package com.example.luna.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class HttpCallback<T> implements Callback {

    private static final String TAG = "FeedbackHttpCallback";
    private static final int SUCCESS = 0;
    private static final int FAILURE = 1;
    private Class<T> t;

    public HttpCallback(){

    }

    public HttpCallback(Class<T> t) {
        this.t = t;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    String json = (String) msg.obj;
                    Logutils.d(TAG, "json = " + json);
                    try {
                        callback(true, JSON.parseObject(json, t));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback(false, null);
                    }
                    break;
                case FAILURE:
                    callback(false, null);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onFailure(Call call, IOException e) {
        mHandler.obtainMessage(FAILURE, null).sendToTarget();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        mHandler.obtainMessage(SUCCESS, response.body().string()).sendToTarget();
    }

    public void callback(boolean success, T t) {
    }

    public boolean isNetworkConnected(Context context) {
        return HttpUtils.isNetworkConnected(context);
    }
}