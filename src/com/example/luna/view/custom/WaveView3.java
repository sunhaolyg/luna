package com.example.luna.view.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.example.luna.utils.Logutils;

public class WaveView3 extends View {

    private static final String TAG = "PagedView";
    private Paint mPaint;
    private Path mPath;
    int w = getWidth();
    int h = getHeight();
    int waveAmplitude = 200;
    int highLevel;

    public WaveView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#fa7319"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        w = 1080;
        h = getMeasuredHeight();
        waveAmplitude = 200;
        highLevel = (int) (1920 * (0.5) + waveAmplitude);
        runAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(5, 5, getWidth() - 5, getHeight() - 5);
//        mPath.addArc(rectF, 90.0f - 145.0f / 2.0f, 145.0f);
//        setPath();
        mPaint.setColor(Color.parseColor("#fa7319"));
        canvas.drawPath(mPath, mPaint);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint);
        canvas.drawLine(getWidth() / 4, 0, getWidth() / 4, getHeight(), mPaint);
        canvas.drawLine(getWidth() / 4 * 3, 0, getWidth() / 4 * 3, getHeight(), mPaint);
        mPaint.setTextSize(40);
        canvas.drawText("110%", getWidth() / 2-40, mMoveHeight, mPaint);
    }

    float mMoveHeight;

    private void setPath() {

//        w += 5;
//        if (w >= (1080 - 5) * 2) {
//            w = 0;
//        }
//        Logutils.d(TAG, "w = " + w);
//        mPath.reset();
////        mPath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, Path.Direction.CW);
//        for (int i = 5; i < w - 5; i++) {
//            mPath.lineTo(i, (float) (500 + 100 * Math.cos((float) (i + w) / (float) (w / 4) * Math.PI)));
//        }
        int width = 1080;
        w += 3;
        if (w >= (width - 5) * 2) {
            w = 0;
        }
        mPath.reset();
        RectF rectF = new RectF(width / 4, 1920 / 2 - width / 4, width / 4 * 3, 1920 / 2 + width / 4);
        mPath.addArc(rectF, 0f, 180.0f);
        for (int i = width / 4; i < width / 4 * 3; i++) {
            if (i == width / 2) {
                mMoveHeight = (float) (960 + 20 * Math.cos((float) (i + w) / (float) (width / 8) * Math.PI));
            }
            mPath.lineTo(i, (float) (960 + 20 * Math.cos((float) (i + w) / (float) (width / 8) * Math.PI)));
        }
        mPath.close();
    }

    private void runAnimation() {
        ValueAnimator va = ValueAnimator.ofInt(0, 100000);
        va.setDuration(100000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setPath();
                invalidate();
            }
        });
    }
}
