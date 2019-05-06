package com.example.luna.sports;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class CircleSportsView extends View {

    private Paint mBackPaint;
    private Paint mDotPaint;
    private int y1;
    private int x1;

    private float x2, y2;

    public CircleSportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBackPaint = new Paint();
        mBackPaint.setColor(Color.parseColor("#007dff"));

        mDotPaint = new Paint();
        mDotPaint.setColor(Color.RED);

        runAnimation();
        run2();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bx = getWidth() / 2;
        int by = getHeight() / 2;
        canvas.drawCircle(bx, by, 300, mBackPaint);

        canvas.drawCircle(bx + y1, by, 20, mDotPaint);
        canvas.drawCircle(bx, by + x1, 20, mDotPaint);

        canvas.drawCircle(bx + x2, by + x2, 20, mDotPaint);
        canvas.drawCircle(bx - x2, by - x2, 20, mDotPaint);
    }

    private void runAnimation() {
        ValueAnimator va = ValueAnimator.ofInt(-300, 300, -300);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setInterpolator(new LinearInterpolator());
        va.setDuration(5000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                y1 = value;
                x1 = -value;
                postInvalidate();
            }
        });

    }

    private void run2() {
        float x = (float) (300 / 1.414);
        ValueAnimator va = ValueAnimator.ofFloat(-x, x, -x);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setDuration(5000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                x2 = value;
            }
        });
    }
}
