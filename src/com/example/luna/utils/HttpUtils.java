package com.example.luna.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import okhttp3.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.File;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class HttpUtils {

    private static final String TAG = "MainActivityTAG";
    public static final String HTTP = "http://192.168.3.31:8080/";
    public static final String UPLOAD_NAME = HTTP + "thinkgem/feedback/uploadFileName";
    public static final String UPLOAD_FILE = HTTP + "thinkgem/feedback/uploadLog";
    public static final String UPLOAD_PIC = HTTP + "thinkgem/wallpaper/uploadPic";
    public static final MediaType BODY_JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static void uploadFile(File[] files, String json, HttpCallback callback) {
        new HttpThread().uploadFile(UPLOAD_PIC, files, json, callback);
    }

    public static void post(final String url, final String json, final Callback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = FormBody.create(BODY_JSON, json);
//        FormBody formBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void get(final String url, final Callback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();
//        FormBody formBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void downloadPic(String url, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
//                .url("http://pic.qiantucdn.com/58pic/17/85/35/559de1de9b223_1024.jpg")
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static String uploadTest(String[] files) {
        final OkHttpClient client = new OkHttpClient();
        String filePath = Environment.getExternalStorageDirectory() + "/eaglelog/";

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String fileName : files) {
            File file = new File(filePath + fileName);
            RequestBody fileBody = RequestBody.create(MediaType.parse("txt"), file);
            builder.addFormDataPart(file.getName(), file.getName(), fileBody);
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(UPLOAD_FILE)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            Logutils.d(TAG, " upload jsonString =" + jsonString);

            if (!response.isSuccessful()) {
                Logutils.d(TAG, "upload error code " + response);
            } else {
                JSONObject jsonObject = JSONObject.parseObject(jsonString);
                int errorCode = jsonObject.getIntValue("errorCode");
                if (errorCode == 0) {
                    Logutils.d(TAG, " upload data =" + jsonObject.getString("data"));
                    return jsonObject.getString("data");
                } else {
                    Logutils.d(TAG, "upload error code " + errorCode + ",errorInfo=" + jsonObject.getString("errorInfo"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean hasWifiConnection(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
