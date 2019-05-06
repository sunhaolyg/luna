package com.example.luna.click;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.example.luna.utils.Logutils;

public class ParentView extends FrameLayout {


    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ThreePointHelper.getInstance().onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ThreePointHelper.getInstance().onInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev);
    }

}
