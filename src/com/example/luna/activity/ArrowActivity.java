package com.example.luna.activity;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.utils.PropertiesUtil;
import com.example.luna.view.custom.ArrowView;
import android.os.SystemProperties;

public class ArrowActivity extends BaseActivity {

    private ArrowView mView;
    private ImageView iv_up;
    private ViewGroup mPushUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrow);
        mView = (ArrowView) findViewById(R.id.arrow_ani);
//        runAnimation();
//        startAnim(mView);
        iv_up = (ImageView) findViewById(R.id.iv_up);
//        startAnim(iv_up);
        actionbar();
        listener();
        getSystemProperties();
    }

    private void getSystemProperties(){
        Logutils.d("RegisterCodeTAG","BOARD = "+PropertiesUtil.BOARD);
        Logutils.d("RegisterCodeTAG","BOOTLOADER = "+PropertiesUtil.BOOTLOADER);
        Logutils.d("RegisterCodeTAG","ID = "+PropertiesUtil.ID);
        Logutils.d("RegisterCodeTAG","DISPLAY = "+PropertiesUtil.DISPLAY);
        Logutils.d("RegisterCodeTAG","PRODUCT = "+PropertiesUtil.PRODUCT);
        Logutils.d("RegisterCodeTAG","DEVICE = "+PropertiesUtil.DEVICE);
        Logutils.d("RegisterCodeTAG","MANUFACTURER = "+PropertiesUtil.MANUFACTURER);
        Logutils.d("RegisterCodeTAG","BRAND = "+PropertiesUtil.BRAND);
        Logutils.d("RegisterCodeTAG","MODEL = "+PropertiesUtil.MODEL);
        Logutils.d("RegisterCodeTAG","PRODUCT_S = "+PropertiesUtil.getString(PropertiesUtil.PRODUCT_S));
        Logutils.d("RegisterCodeTAG","TIME_ZONE = "+PropertiesUtil.TIME_ZONE);

        String v = SystemProperties.get("ro.product.name","ro.product.name");
        Logutils.d("RegisterCodeTAG","PRODUCT_S = "+v);

    }

    private void listener() {
        mPushUp = findViewById(R.id.push_up);
        mPushUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Logutils.d("RegisterCodeTAG", "ACTION_DOWN = " + event.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Logutils.d("RegisterCodeTAG", "ACTION_MOVE = " + event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        Logutils.d("RegisterCodeTAG", "ACTION_UP = " + event.getRawY());
                        break;
                }
                return true;
            }
        });
    }

    private void actionbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void startAnim(View view) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationY", 0, -200);
        oa.setRepeatCount(ObjectAnimator.INFINITE);
        oa.setDuration(1000);
        oa.start();
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.4f);
        oa2.setRepeatCount(ObjectAnimator.INFINITE);
        oa2.setDuration(1000);
        oa2.start();
    }

    private void runAnimation() {
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.1f);
        aa.setDuration(1000);
        aa.setRepeatCount(-1);
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -200);
        ta.setDuration(1000);
        ta.setRepeatCount(-1);
        mView.startAnimation(aa);
        mView.startAnimation(ta);
    }
}
