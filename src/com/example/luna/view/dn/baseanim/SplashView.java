package com.example.luna.view.dn.baseanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class SplashView extends View {

    private BaseState mBaseState;
    private int mWidth, mHeight, mRadius;
    int mDegree;

    private Paint mPaint;

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mRadius = 100;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBaseState == null) {
            mBaseState = new RotationState();
        }
        mBaseState.onDraw(canvas);
    }

    public abstract class BaseState {

        public BaseState() {

        }

        abstract void onDraw(Canvas canvas);

        abstract BaseState switchState(BaseState state);
    }

    public class RotationState extends BaseState {
        ValueAnimator mValueAnimator;


        public RotationState() {
            super();
            mValueAnimator = ValueAnimator.ofInt(0, (int) (Math.PI * 2));
//            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.start();
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mDegree = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mBaseState = switchState(mBaseState);
                    invalidate();
                }
            });
        }

        @Override
        void onDraw(Canvas canvas) {
            //x = r*cos(a) + centerX
            //y = r*sin(a) + centerY
            float rotationAngle = (float) (2 * Math.PI / 6);
            for (int i = 0; i < 6; i++) {
                float cx = (float) (mRadius * Math.cos(mDegree + i * rotationAngle) + mWidth / 2);
                float cy = (float) (mRadius * Math.sin(mDegree + i * rotationAngle) + mHeight / 2);
                canvas.drawCircle(cx, cy, 10, mPaint);
            }
        }

        @Override
        BaseState switchState(BaseState state) {
            return new ExpandState();
        }
    }

    public class ExpandState extends BaseState {

        public ExpandState() {
            super();
            ValueAnimator va = ValueAnimator.ofFloat(0f, 1.0f);
            va.setDuration(2000);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.setInterpolator(new Bounce2Interpolator());
            va.start();
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mRadius = (int) (100 * value);
                    invalidate();
                }
            });
        }

        @Override
        void onDraw(Canvas canvas) {
            for (int i = 0; i < 6; i++) {
                float cx = (float) (mRadius * Math.cos(mDegree + i * 60) + mWidth / 2);
                float cy = (float) (mRadius * Math.sin(mDegree + i * 60) + mHeight / 2);
                canvas.drawCircle(cx, cy, 10, mPaint);
            }
        }

        @Override
        BaseState switchState(BaseState state) {
            return null;
        }
    }

    public class Bounce2Interpolator extends BounceInterpolator {

        @Override
        public float getInterpolation(float t) {
            if (t < 0.17f) {
                return 1.0f + t;
            } else if (t < 0.67f) {
                return 1.5678f - 2.34f * t;
            }
            return t * 3f - 2f;
        }
    }
}
