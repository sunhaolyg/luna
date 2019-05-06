package com.example.luna.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

public class BaseView extends View {

    protected int mScreenWidth, mScreenHeight;

    public BaseView(Context context) {
        super(context);
        initScreen(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScreen(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScreen(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initScreen(context);
    }

    private void initScreen(Context context) {
        DisplayMetrics display = new DisplayMetrics();
        mScreenWidth = display.widthPixels;
        mScreenHeight = display.heightPixels;

    }
}
