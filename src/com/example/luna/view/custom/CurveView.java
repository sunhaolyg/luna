package com.example.luna.view.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.luna.activity.curve.PointBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-10-13.
 */

public class CurveView extends View {

    private Paint mPaint;
    private int x1, x2, y1, y2, x3, y3;
    private int[] point1 = {10, 1400};
    private int[] point2 = {1000, 1400};
    private Path mPath = new Path();
    private static final int DURATION = 5000;
    private List<PointBean> points = new ArrayList<>();
    private float progress;

    public CurveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(5);

//        canvas.drawLine(100, 1000, 400, 200, mPaint);
//        canvas.drawLine(400, 200, 900, 1000, mPaint);
        points.add(new PointBean(x1, y1, x2, y2));
        for (PointBean bean : points) {
            mPaint.setColor(Color.BLACK);
//            canvas.drawLine(bean.getX1(), bean.getY1(), bean.getX2(), bean.getY2(), mPaint);
//            mPaint.setColor(Color.RED);
//            float x3 = (bean.getX2() - bean.getX1()) * progress + bean.getX1();
//            float y3 = (bean.getY2() - bean.getY1()) * progress + bean.getY1();
//            Logutils.d("SwitchView","x3="+x3+",y3="+y3);
//            mPath.lineTo(x3, y3);
        }
        for (int i = 0; i < points.size(); i++) {
            PointBean bean = points.get(i);
            if (i != points.size() - 1) {
                mPaint.setColor(Color.BLACK);
                canvas.drawLine(bean.getX1(), bean.getY1(), bean.getX2(), bean.getY2(), mPaint);
            } else {
                mPaint.setColor(Color.RED);
                canvas.drawLine(bean.getX1(), bean.getY1(), bean.getX2(), bean.getY2(), mPaint);
            }
        }
//        canvas.drawLine(x1,y1,x2,y2,mPaint);
//        canvas.drawPath(mPath, mPaint);
//        mPaint.setTextSize(50);
//        mPaint.setStrokeWidth(10);
//
//        canvas.drawText(progress + "", 100, 100, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mPath = new Path();
                points.clear();
                runAnimation(event.getX(), event.getY());
                break;
            default:
                break;
        }
        return true;
    }

    private void runAnimation(float x, float y) {
        ValueAnimator va1 = ValueAnimator.ofInt(point1[0], (int) x);//x1
        ValueAnimator va2 = ValueAnimator.ofInt(point1[1], (int) y);//y1
        ValueAnimator va3 = ValueAnimator.ofInt((int) x, point2[0]);//x2
        ValueAnimator va4 = ValueAnimator.ofInt((int) y, point2[1]);//y2
        va1.setDuration(DURATION);
        va2.setDuration(DURATION);
        va3.setDuration(DURATION);
        va4.setDuration(DURATION);
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x1 = (int) animation.getAnimatedValue();
//                invalidate();
//                progress = (float) (x1 - 100) / (400.0f - 100.f);
//                Logutils.d("SwitchView", "progress=" + progress + ",x1 = " + x1);
            }
        });
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y1 = (int) animation.getAnimatedValue();
//                invalidate();
            }
        });
        va3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x2 = (int) animation.getAnimatedValue();
//                invalidate();

            }
        });
        va4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y2 = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        va1.start();
        va2.start();
        va3.start();
        va4.start();

//        ValueAnimator v5 = ValueAnimator.ofInt(x1, x2);
//        ValueAnimator v6 = ValueAnimator.ofInt(y1, y2);
//        v5.setDuration(1000);
//        v6.setDuration(1000);
//        v5.start();
//        v6.start();
//        v5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                x3 = (int) animation.getAnimatedValue();
//            }
//        });
//        v6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                y3 = (int) animation.getAnimatedValue();
//                invalidate();
//            }
//        });

    }
}
