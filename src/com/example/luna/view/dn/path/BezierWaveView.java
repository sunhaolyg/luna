package com.example.luna.view.dn.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BezierWaveView extends View {

    private static final int WAVE_LENGTH = 300;
    private static final int WAVE_HEIGHT = 100;

    private Path mPath;
    private Paint mPaint;

    private int mWidth, mHeight;

    public BezierWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        int i = 0;
        mPath.moveTo(i, 0);
        for (; i < mWidth; i += WAVE_LENGTH) {
            mPath.quadTo(i + WAVE_LENGTH / 4, WAVE_HEIGHT, i + WAVE_LENGTH / 2, 0);
            mPath.quadTo(i + WAVE_LENGTH * 3 / 4, -WAVE_HEIGHT, i + WAVE_LENGTH, 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, mHeight/2);
        canvas.drawPath(mPath, mPaint);
    }
}
