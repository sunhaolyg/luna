package com.example.luna.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class ThreadNameActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_name);
        Log.d(TAG, "name = " + Thread.currentThread().getName());
        startThread();
        runnable();
    }

    private void runnable() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Runnable = " + Thread.currentThread().getName());

            }
        };
        run.run();
    }

    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "startThread = " + Thread.currentThread().getName());

            }
        }.start();
    }
}
