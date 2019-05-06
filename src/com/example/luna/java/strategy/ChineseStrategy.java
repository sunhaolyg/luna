package com.example.luna.java.strategy;


import com.example.luna.utils.Logutils;

/**
 * Created by sunhao on 17-10-12.
 */

public class ChineseStrategy implements Strategy {

    private static final String TAG = "Strategy";

    @Override
    public void translate(String content) {
        Logutils.d(TAG, "ChineseStrategy");
    }
}
