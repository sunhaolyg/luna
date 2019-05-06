package com.example.luna.view.touch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.luna.utils.Logutils;

public class LunaViewGroup extends FrameLayout {

    private static final String TAG = "LunaViewGroup";

    public LunaViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logutils.d(TAG, "dispatchTouchEvent",new Throwable());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logutils.d(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logutils.d(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
