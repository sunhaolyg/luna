package com.example.luna.view.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.luna.utils.Logutils;

/**
 * 贝塞尔曲线画波浪
 */
public class WaveView4 extends View {

    private Path mPath;
    private Paint mPaint;
    private int mLength;
    private int mWidth;
    private int mHeight;
    private int mWaveHeight;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mHeightAnimator;
    private int mAnimatorHeight;
    private int mAnimatorValue;

    public WaveView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mLength = 300;
        mWaveHeight = 50;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mAnimatorHeight = mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPath();
        canvas.clipRect(100, 100, mWidth - 100, mHeight - 100);
        Region region = new Region(mWidth / 2 - 1, 0, mWidth / 2, mHeight);
        Region region1 = new Region();
        region1.setPath(mPath, region);
        Rect rect = region1.getBounds();
        int left = rect.left;
        int top = rect.top;
        int right = rect.right;
        int bottom = rect.bottom;
        Logutils.d("GuestResumeSessionReceiver", "left = " + left + ",top = " + top + ",right = " + right + ",bottom = " + bottom);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(38);
        canvas.drawText((100-mAnimatorHeight * 100 / (mHeight - 200)) + "%", mWidth / 2, top, mPaint);
    }

    private void setPath() {
        mPath.reset();
        mPath.moveTo(mAnimatorValue - mLength, mAnimatorHeight);
        for (int i = -mLength; i < mWidth + mLength; i += mLength) {
            mPath.rQuadTo(mLength / 4, mWaveHeight, mLength / 2, 0);
            mPath.rQuadTo(mLength / 4, -mWaveHeight, mLength / 2, 0);
        }

        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

//        for (int i = -mLength; i < mWidth + mLength; i += mLength) {
//            mPath.rQuadTo(mLength * 3 / 4, mWaveHeight, mLength / 2, 0);
//            mPath.rQuadTo(mLength / 4, -mWaveHeight, mLength / 2, 0);
//        }
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
        mHeightAnimator.cancel();
    }

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            runAnimation();
            runHeightAnimation();
        }
    };

    private void runHeightAnimation() {
        mHeightAnimator = ValueAnimator.ofInt(mHeight - 200, 200, mHeight - 200);
        mHeightAnimator.setDuration(200000);
        mHeightAnimator.setInterpolator(new LinearInterpolator());
        mHeightAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mHeightAnimator.start();
        mHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorHeight = (int) animation.getAnimatedValue();
            }
        });
    }

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
