package com.example.luna.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.luna.utils.Logutils;

public class DownloadService extends Service {

    private static final String TAG = "GuestResumeSessionReceiver";

    @Override
    public void onCreate() {
        Logutils.d(TAG, "服务启动");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logutils.d(TAG, "shopId=" + intent.getStringExtra("userName"));
        return super.onStartCommand(intent, flags, startId);
    }
}
