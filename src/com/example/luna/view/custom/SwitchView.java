package com.example.luna.view.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;


/**
 * Created by sunhao on 17-11-24.
 */

public class SwitchView extends ToggleSwitch {

    private static final String TAG = "SwitchView";

    public static final int SWITCH_ON = 1;
    public static final int SWITCH_OFF = 0;
    private int mSwitchState;
    private Paint mPaint;

    private boolean mRunning;

    private int mCircleLocationX;
    private int mAnimationValue = 0;
    private boolean mAnimationMode = false;
    private float downX = 0, downY = 0;

    private int mOffsetValue;
    private VelocityTracker mVelocityTracker;

    private OnCheckedChangedListener mOnCheckedChangedListener;

    private int mCircleColor = Color.WHITE;
    private int mCheckedColor = Color.BLUE;
    private int mUnCheckedColor = Color.GRAY;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(mCheckedColor);

        final int w = getWidth();
        final int h = getHeight();
        final int init = mSwitchState == SWITCH_OFF ? 0 + h / 2 : w - h / 2;
        int drawValue = mOffsetValue + init;
        if (mAnimationMode) {
            drawValue = mAnimationValue;
        }
        if (drawValue > w - h / 2) {
            drawValue = w - h / 2;
        }
        if (drawValue < h / 2) {
            drawValue = h / 2;
        }

        canvas.drawCircle(h / 2, h / 2, h / 2, mPaint);
        if (drawValue > h / 2) {
            canvas.drawRect(new RectF(h / 2, 0, drawValue, h), mPaint);
        }
        mPaint.setColor(mUnCheckedColor);
        canvas.drawRect(new RectF(drawValue, 0, w - h / 2, h), mPaint);
        canvas.drawCircle(w - h / 2, h / 2, h / 2, mPaint);

        mPaint.setColor(mCircleColor);
        canvas.drawCircle(drawValue, getHeight() / 2, getHeight() / 2, mPaint);
        mCircleLocationX = drawValue;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRunning) {
            return false;
        }
        mAnimationMode = false;
        mVelocityTracker.addMovement(event);
        final float x = event.getX();
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mOffsetValue = (int) (x - downX);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                final int width = getWidth();
                final int height = getHeight();
                if (Math.abs(x - downX) < 10 && Math.abs(y - downY) < 10) {
                    if (mSwitchState == SWITCH_OFF) {
                        startAnimation(mCircleLocationX, width - height / 2, 0);
                    } else {
                        startAnimation(mCircleLocationX, height / 2, 0);
                    }
                } else {
                    mVelocityTracker.computeCurrentVelocity(100);
                    float velocity = mVelocityTracker.getXVelocity();
                    if (velocity > 40) {
                        startAnimation(mCircleLocationX, width - height / 2, velocity);
                        break;
                    } else if (velocity < -40) {
                        startAnimation(mCircleLocationX, height / 2, Math.abs(velocity));
                        break;
                    }
                    if (mCircleLocationX > width / 2) {
                        startAnimation(mCircleLocationX, width - height / 2, 0);
                    } else {
                        startAnimation(mCircleLocationX, height / 2, 0);
                    }
                }
                break;
        }
        return true;
    }

    private void startAnimation(int start, int end, float velocity) {
        mAnimationMode = true;
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        long duration = Math.abs(end - start) * 4;
        if (velocity != 0) {
            duration = (long) (duration * 40 / velocity);
        }
        va.setDuration(duration);

        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRunning = false;
                setSwitchState();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimationValue = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        va.start();
    }

    private void setSwitchState() {
        if (mCircleLocationX < getWidth() / 2) {
            mSwitchState = SWITCH_OFF;
        } else {
            mSwitchState = SWITCH_ON;
        }
        Log.d(TAG, "mSwitchState = " + mSwitchState);
        if (mOnCheckedChangedListener != null) {
            mOnCheckedChangedListener.onCheckedChange(mSwitchState == SWITCH_ON);
        }
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener onCheckedChangedListener) {
        this.mOnCheckedChangedListener = onCheckedChangedListener;
    }

    public interface OnCheckedChangedListener {
        void onCheckedChange(final boolean checked);
    }

    public void setChecked(final boolean checked) {
        if (mRunning) {
            return;
        }
        final int width = getWidth();
        final int height = getHeight();
        if (checked) {
            startAnimation(mCircleLocationX, height / 2, 0);
        } else {
            startAnimation(mCircleLocationX, width - height / 2, 0);
        }
    }

    public void setCheckedColor(int color) {
        this.mCheckedColor = color;
        postInvalidate();
    }

    public void setUnCheckedColor(int color) {
        this.mUnCheckedColor = color;
        postInvalidate();
    }

    public void setCircleColor(int color) {
        this.mCircleColor = color;
        postInvalidate();
    }

}
