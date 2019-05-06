package com.example.luna.utils;

import android.util.Log;

/**
 * Created by sunhao on 17-10-9.
 */

public class Logutils {

    public static final boolean DEBUG = true;

    public static void d(String TAG, String content) {
        if (DEBUG) {
            Log.d(TAG, content);
        }
    }

    public static void i(String TAG, String content) {
        Log.i(TAG, content);
    }

    public static void w(String TAG, String content) {
        Log.i(TAG, content);
    }

    public static void e(String TAG, String content) {
        Log.e(TAG, content);
    }

    public static void d(String TAG, String content, Throwable t) {
        Log.d(TAG, content, t);
    }
}
