package com.example.luna.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

public class ToggleSwitch extends Switch /*implements SwitchHelper.SwitchHelperCallback*/ {

    private SwitchHelper mSwitchHelper;

    public ToggleSwitch(Context context) {
        super(context);
        init();
    }

    public ToggleSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        mSwitchHelper = new SwitchHelper(this);
    }

    private ToggleSwitch.OnBeforeCheckedChangeListener mOnBeforeListener;

    public static interface OnBeforeCheckedChangeListener {
        public boolean onBeforeCheckedChanged(ToggleSwitch toggleSwitch, boolean checked);
    }

    public void setOnBeforeCheckedChangeListener(OnBeforeCheckedChangeListener listener) {
        mOnBeforeListener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mOnBeforeListener != null
                && mOnBeforeListener.onBeforeCheckedChanged(this, checked)) {
            return;
        }
        super.setChecked(checked);
//        if (mSwitchHelper != null) {
//            mSwitchHelper.setChecked(checked);
//        }
    }

    public void setCheckedInternal(boolean checked) {
        super.setChecked(checked);
//        mSwitchHelper.setChecked(checked);
    }

//    @Override
//    public void onCheckedChange(boolean checked) {
//        if (mOnCheckedChangeListener != null) {
//            mOnCheckedChangeListener.onCheckedChanged(this, checked);
//        }
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        mSwitchHelper.onDraw(canvas);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return mSwitchHelper.onTouchEvent(ev);
//    }
//
//    @Override
//    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
////        super.setOnCheckedChangeListener(listener);
//        this.mOnCheckedChangeListener = listener;
//    }
//
//    private OnCheckedChangeListener mOnCheckedChangeListener;
//
//    @Override
//    public boolean isChecked() {
////        return super.isChecked();
//        boolean checked = false;
//        if (mSwitchHelper != null) {
//            checked = mSwitchHelper.isChecked();
//        }
//        return checked;
//    }
}
