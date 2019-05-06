package com.example.luna.r10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.HttpUtils;
import com.example.luna.utils.Logutils;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.File;
import java.io.IOException;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "BrightnessPrefCtrl";

    private static final String ADDRESS = "http://192.168.3.13:8080/thinkgem";
    private static final String CODE = ADDRESS + "/register/phoneCode";
    private static final String BAIDU = "http://www.baidu.com";

    OkHttpClient client = new OkHttpClient();
    private Button upload_file, upload_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        post();
//        try {
//            run("http://www.baidu.com");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        asyncGet(BAIDU);
//        get(BAIDU);
        upload_file = findViewById(R.id.upload_file);
        upload_name = findViewById(R.id.upload_name);
        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        File file = new File(Environment.getExternalStorageDirectory() + "/eaglelog/");
//                        if (file.exists()) {
//                            String[] list = file.list();
//                            for (String s : list) {
//                                HttpUtils.uploadTest(s);
//                            }
//                        }
                        HttpUtils.uploadTest(file.list());
                    }
                }.start();
            }
        });
        upload_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    public void upload() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Callback callback = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Logutils.d("MainActivityTAG", "json = " + json);
                    }
                };
                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/eaglelog/");
                    JSONObject json = new JSONObject();
                    json.put("feed_description", "luna 1");
                    json.put("feed_number", "123123123123");

                    String names = "";
                    if (file.exists()) {
                        String[] list = file.list();
                        for (String s : list) {
                            names += "," + s;
                        }
                    }
                    json.put("file_name", names);
                    HttpUtils.post(HttpUtils.UPLOAD_NAME, json.toJSONString(), callback);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void get(String url) {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("username", "zhangsan");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logutils.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logutils.d(TAG, "body" + response.body().string());
                Logutils.d(TAG, "message" + response.message());
            }
        });
    }

    private void asyncGet(String url) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String result = run(url);
                    Logutils.d(TAG, "result = " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }


        };
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void post() {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("username", "zhangsan");
        Request request = new Request.Builder()
                .url(CODE)
                .post(formBody.build())
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logutils.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logutils.d(TAG, "body" + response.body().string());
                Logutils.d(TAG, "message" + response.message());
            }
        });
    }
}
