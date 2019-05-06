package com.example.luna.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;


/**
 * Created by sunhao on 17-10-12.
 */

public class JavaActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
    }

    public void java(View view) {
        switch (view.getId()) {
            case R.id.template:
                startActivity(new Intent(this,TemplateActivity.class));
                break;
            case R.id.strategy:
                startActivity(new Intent(this,StrategyActivity.class));
                break;
            case R.id.array:
                startActivity(new Intent(this,ArrayActivity.class));
                break;

            default:
                break;
        }
    }
}
