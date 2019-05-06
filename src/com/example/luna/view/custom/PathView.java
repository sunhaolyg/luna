package com.example.luna.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by sunhao on 17-10-11.
 */

public class PathView extends View {

    private Path mPath;
    private Paint mPaint;
    private float offset = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    private float mLastX, mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mPath.reset();
                mPath.moveTo(x, y);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                float preX = mLastX;
                float preY = mLastY;
                float dx = Math.abs(x1 - preX);
                float dy = Math.abs(y1 - preY);
//                if (dx >= offset || dy >= offset) {
                // 贝塞尔曲线的控制点为起点和终点的中点
                float cX = (x1 + preX) / 2;
                float cY = (y1 + preY) / 2;
                mPath.quadTo(preX, preY, cX, cY);
//                    mPath.lineTo(x1, y1);
                mLastX = x1;
                mLastY = y1;
//                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    public void clear() {
        mPath.reset();
        postInvalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }
}
