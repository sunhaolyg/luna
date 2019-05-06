package com.example.luna.java.template;


import com.example.luna.utils.Logutils;

/**
 * Created by sunhao on 17-10-12.
 */

public class ChineseTranslate extends Translate {
    @Override
    public void translate(String content) {
        Logutils.d(TAG, "chinese = " + content);
    }
}
