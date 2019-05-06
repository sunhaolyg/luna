package com.example.luna.view.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.luna.utils.Logutils;

/**
 * 贝塞尔曲线画两条波浪线
 */
public class WaveView5 extends View {

    private Path mPath1;
    private Path mPath2;
    private Paint mPaint;
    private int mLength;
    private int mWidth;
    private int mHeight;
    private int mWaveHeight;
    private ValueAnimator mValueAnimator;
    private int mAnimatorValue;

    public WaveView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPath1 = new Path();
        mPath2 = new Path();
        mLength = 300;
        mWaveHeight = 150;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPath();
        canvas.drawPath(mPath1, mPaint);
        canvas.drawPath(mPath2, mPaint);
    }

    private void setPath() {
        mPath1.reset();
        mPath1.moveTo(mAnimatorValue - mLength, mHeight/2);
        for (int i = -mLength; i < mWidth + mLength; i += mLength) {
            mPath1.rQuadTo(mLength / 4, mWaveHeight, mLength / 2, 0);
            mPath1.rQuadTo(mLength / 4, -mWaveHeight, mLength / 2, 0);
        }

        mPath2.reset();
        mPath2.moveTo(-mAnimatorValue , mHeight/2);
        for (int i = -mLength; i < mWidth + mLength; i += mLength) {
            mPath2.rQuadTo(mLength / 4, mWaveHeight, mLength / 2, 0);
            mPath2.rQuadTo(mLength / 4, -mWaveHeight, mLength / 2, 0);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(animator, 500);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(animator);
        mValueAnimator.cancel();
    }

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            runAnimation();
        }
    };


    private void runAnimation() {
        mValueAnimator = ValueAnimator.ofInt(0, mLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }
}
