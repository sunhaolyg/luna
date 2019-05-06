package com.example.luna.activity.drawable;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.luna.R;
import com.example.luna.base.BaseActivity;

/**
 * Created by sunhao on 17-10-11.
 */

public class DrawActivity extends BaseActivity {

    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        iv = (ImageView) findViewById(R.id.draw_iv);
        iv.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.background)));
    }
}
