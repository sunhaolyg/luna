package com.example.luna.base;

import com.example.luna.utils.Logutils;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private static List<BaseActivity> sActivity = new ArrayList<>();

    public static void add(BaseActivity ba) {
        sActivity.add(ba);
        for (BaseActivity b : sActivity) {
            Logutils.d("ActivityManagerActivityManager", "" + ba);
        }
    }

    public static void remove(BaseActivity ba) {
        if (sActivity != null) {
            sActivity.remove(ba);
        }
    }

    public static void exit() {
        for (BaseActivity ba : sActivity) {
            if (ba != null) {
                ba.finish();
            }
        }
        sActivity.clear();
        sActivity = null;
        System.gc();
    }
}
