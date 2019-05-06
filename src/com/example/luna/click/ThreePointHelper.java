package com.example.luna.click;

import android.util.Log;
import android.view.MotionEvent;

public class ThreePointHelper {

    private static ThreePointHelper sInstance;
    private OnThreePointListener mListener;
    private float mDownY;
    private float mDownY2;
    private float mDownY3;
    private float mDistanceY2, mDistanceY3;
    private boolean mPointOneDown, mPointOneUp;
    private boolean mPointTwoDown, mPointTwoUp;
    private boolean mPointThreeDown, mPointThreeUp;

    private boolean mOneValid;
    private boolean mTwoValid;
    private boolean mThreeValid;

    private boolean mInValid;


    private ThreePointHelper() {
    }

    public static ThreePointHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ThreePointHelper();
        }
        return sInstance;
    }

    public void register(OnThreePointListener listener) {
        mListener = listener;
    }

    public void unregister() {
        mListener = null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int actionType = event.getActionMasked();
        switch (actionType) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!mPointTwoDown) {
                    mPointTwoDown = true;
                    mDownY2 = event.getRawY();
                } else if (mPointTwoDown && !mPointThreeDown) {
                    mPointThreeDown = true;
                    mDownY3 = event.getRawY();
                } else {
                    mInValid = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (mPointTwoDown && !mPointTwoUp) {
                    mPointTwoUp = true;
                    mTwoValid = true;
                    mDistanceY2 = event.getRawY() - mDownY2;
                } else if (mPointThreeDown && !mPointThreeUp) {
                    mPointThreeUp = true;
                    mThreeValid = true;
                    mDistanceY3 = event.getRawY() - mDownY3;
                } else {
                    mInValid = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTwoValid && mThreeValid && mDistanceY2 > 200 && mDistanceY3 > 200 &&
                        event.getRawY() - mDownY > 200 && !mInValid) {
                    Log.d("NotificationViewWrapper", "parent onTouchEvent Do screenshot");
                    if (mListener != null) {
                        mListener.takeScreenshot();
                    }
                }
            case MotionEvent.ACTION_CANCEL:
                mDownY = 0f;
                mPointOneDown = false;
                mPointOneUp = false;
                mPointTwoDown = false;
                mPointTwoUp = false;
                mPointThreeDown = false;
                mPointThreeUp = false;
                mOneValid = false;
                mTwoValid = false;
                mThreeValid = false;
                mInValid = false;
                mDistanceY3 = 0f;
                mDistanceY2 = 0f;
                break;

        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int actionType = ev.getActionMasked();
        switch (actionType) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!mPointTwoDown) {
                    mPointTwoDown = true;
                    mDownY2 = ev.getRawY();
                }
                break;
        }
        if (ev.getActionIndex() == 2) {
            mPointThreeDown = true;
            mDownY3 = ev.getRawY();
            return true;
        }
        return false;
    }

    public interface OnThreePointListener {

        void takeScreenshot();

    }
}
