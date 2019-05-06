package com.example.luna.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ArrowView extends View {

    private Paint mPaint;
    private Path mPath;

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        setPath();
        canvas.drawPath(mPath,mPaint);
    }

    private void setPath(){
        mPath.moveTo(100,250);
        mPath.lineTo(200,130);
        mPath.lineTo(300,250);

        mPath.moveTo(100,400);
        mPath.lineTo(200,280);
        mPath.lineTo(300,400);

        mPath.moveTo(100,550);
        mPath.lineTo(200,430);
        mPath.lineTo(300,550);
    }
}
