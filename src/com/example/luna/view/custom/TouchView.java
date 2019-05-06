package com.example.luna.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.luna.utils.Logutils;


/**
 * Created by sunhao on 17-10-12.
 */

public class TouchView extends View {

    private static final String TAG = "TouchView";

    private Paint mPaint;
    private float mValueX, mValueY;
    private float mLastMoveX, mLastMoveY;
    private float mStopValue;

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    float aaa;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        float value = mStopValue + mValueX;
        Logutils.d(TAG, "onDraw = " + value);
        aaa = value;
        canvas.drawCircle(value, 800, 50, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveX = aaa;
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mValueX = x - mLastMoveX;
                Logutils.d(TAG, "mValueX = " + mValueX + ",mLastMoveX = " + mLastMoveX + ",mStopValue = " + mStopValue + ",plus = " + (mValueX + mStopValue));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//                mStopValue = x;
                Logutils.d(TAG, "ACTION_UP mStopValue = " + mStopValue);
                break;
        }
        return true;
    }
}
