package com.example.luna.activity.shader;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.view.custom.BitmapShaderView;

/**
 * Created by sunhao on 17-10-11.
 */

public class BitmapShaderActivity extends BaseActivity {

    private BitmapShaderView mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmapshader);
//        mView= (BitmapShaderView) findViewById(R.id.bitmapshader);
//        mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mView.setType(BitmapShaderView.TYPE_ROUND);
//                mView.setBorderRadius(100);
//            }
//        });
        mView = (BitmapShaderView) findViewById(R.id.bv);
        mView.setImageResource(R.drawable.background);
    }
}
