package com.example.luna.click;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.example.luna.utils.Logutils;

public class ChildView extends FrameLayout {
    public ChildView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//        }
//        Logutils.d("NotificationViewWrapper", "child onTouchEvent Down"+MotionEvent.actionToString(event.getAction()));
//        return true;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//        }
//        Logutils.d("NotificationViewWrapper", "child onInterceptTouchEvent Down"+MotionEvent.actionToString(ev.getAction()));
//        return true;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//        }
//        Logutils.d("NotificationViewWrapper", "child dispatchTouchEvent Down"+MotionEvent.actionToString(ev.getAction()));
//        return super.dispatchTouchEvent(ev);
//    }
}
