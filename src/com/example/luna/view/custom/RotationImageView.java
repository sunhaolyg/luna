package com.example.luna.view.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class RotationImageView extends ImageView {

    private ObjectAnimator mObjectAnimator;

    public RotationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(mAnimation, 500);
    }

    @Override
    protected void onDetachedFromWindow() {
        mObjectAnimator.cancel();
        super.onDetachedFromWindow();
    }

    private Runnable mAnimation = new Runnable() {
        @Override
        public void run() {
            startAnimation();
        }
    };


    private void startAnimation() {
        mObjectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0.0F, 360.0F);
        mObjectAnimator.setDuration(10000);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.start();
    }
}
