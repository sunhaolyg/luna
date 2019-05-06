package com.example.luna.activity.paint;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.MotionEvent;
import com.example.luna.R;
import com.example.luna.base.ActivityManager;
import com.example.luna.base.BaseActivity;

/**
 * Created by sunhao on 17-10-11.
 */

public class PaintBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_base);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ActivityManager.exit();
        }
        return true;
    }
}
