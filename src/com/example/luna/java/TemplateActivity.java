package com.example.luna.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.base.BaseActivity;
import com.example.luna.java.template.ChineseTranslate;
import com.example.luna.java.template.Translate;


/**
 * Created by sunhao on 17-10-12.
 */

public class TemplateActivity extends BaseActivity {

    private String content = "myapplication";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translate(new ChineseTranslate(),"");
    }

    private void translate(Translate translate, String content) {
        translate.translate(content);
    }
}
