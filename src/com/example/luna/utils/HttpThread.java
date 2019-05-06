package com.example.luna.utils;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class HttpThread extends Thread {

    public static final MediaType BODY_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType BODY_TXT = MediaType.parse("txt");

    private void postInternal(final String url, final RequestBody body, final HttpCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void uploadFile(String url, File[] files, String json, HttpCallback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (files != null && files.length != 0) {
            for (File file : files) {
                RequestBody fileBody = RequestBody.create(BODY_TXT, file);
                builder.addFormDataPart(file.getName(), file.getName(), fileBody);
            }
        }
        builder.addFormDataPart("json", json);

        postInternal(url, builder.build(), callback);
    }

    public void post(final String url, final String json, final HttpCallback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = FormBody.create(BODY_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void get(String url, HttpCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }
}
