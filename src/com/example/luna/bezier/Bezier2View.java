package com.example.luna.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sunhao on 17-10-18.
 */

public class Bezier2View extends View {

    private Paint mPaintBezier, mPaintAuxiliary, mPaintAuxiliaryText;

    private float mAuxiliaryX, mAuxiliaryY;

    private float mStartPointX, mStartPointY;

    private float mEndPointX, mEndPointY;

    private Path mPath;

    public Bezier2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setStrokeWidth(6);

        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2 - 200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);

        canvas.drawPoint(mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);

        mPaintAuxiliaryText.setTextSize(60);
        canvas.drawText("control", mAuxiliaryX, mAuxiliaryY, mPaintAuxiliaryText);
        canvas.drawText("start", mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText("end", mEndPointX, mEndPointY, mPaintAuxiliaryText);

        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        canvas.drawLine(mAuxiliaryX, mAuxiliaryY, mEndPointX, mEndPointY, mPaintAuxiliary);

        mPath.quadTo(mAuxiliaryX, mAuxiliaryY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryX = event.getX();
                mAuxiliaryY = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
