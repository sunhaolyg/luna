package com.example.luna.activity.view;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;

import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.view.custom.WaterFallLayout;

import java.util.Random;

/**
 * Created by sunhao on 17-10-10.
 */

public class WaterFallActivity extends BaseActivity {

    private WaterFallLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = (WaterFallLayout) LayoutInflater.from(this).inflate(R.layout.activity_waterfall, null);
        setContentView(mLayout);
//        setContentView(R.layout.activity_waterfall);
//        mLayout = (WaterFallLayout) findViewById(R.id.water);
        listener();
    }

    public void listener() {
        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = new ImageView(WaterFallActivity.this);
                iv.setMaxWidth(50);
                Random rd = new Random();
                int height = rd.nextInt(200) + 200;
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(216, height);
                iv.setLayoutParams(lp);
                iv.setBackgroundColor(Color.RED);
//                iv.setImageResource(R.drawable.ic_launcher);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                mLayout.addView(iv);
            }
        });
    }
}
