package com.example.luna.view.dn.pie;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.luna.R;

public class PieView extends View {

    private int mWidth, mHeight;
    private Paint mPaint;
    private RectF mRectF;

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRectF = new RectF(w / 2 - 200, h / 2 - 200, w / 2 + 200, h / 2 + 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.gray));

        drawColumn(canvas);


        drawPie(canvas);

        drawRing(canvas);

    }

    private void drawRing(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawCircle(mWidth / 2, mHeight / 2 + 500, 200, mPaint);

        RectF rectF = new RectF(mWidth / 2 - 200, mHeight / 2 + 500 - 200, mWidth / 2 + 200, mHeight / 2 + 500 + 200);

        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF, 270, 30, false, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(rectF, 300, 60, false, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 50, false, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawArc(rectF, 50, 170, false, mPaint);
    }

    private void drawColumn(Canvas canvas) {
        int originX = mWidth / 2 - 450;
        int originY = mHeight / 2 - 300;
        int columnW = 100;

        mPaint.setColor(Color.parseColor("#00aaaa"));
        for (int i = originX; i < 900; i += columnW + 20) {
            Rect r = new Rect(i + 20, (int) (i * 0.3f) + 200, i + 20 + columnW, originY);
            canvas.drawRect(r, mPaint);
        }
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(mWidth / 2 - 450, mHeight / 2 - 800, originX, originY, mPaint);
        canvas.drawLine(originX, originY, mWidth / 2 + 450, mHeight / 2 - 300, mPaint);

    }

    private void drawPie(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 5, mPaint);

        canvas.save();

        mPaint.setColor(Color.parseColor("#841144"));
        canvas.translate(10, 0);
        canvas.drawArc(mRectF, 0, 10, true, mPaint);

        canvas.restore();
        canvas.save();

        canvas.translate(10, 5);
        mPaint.setColor(Color.GRAY);
        canvas.drawArc(mRectF, 10, 10, true, mPaint);

        canvas.restore();
        canvas.save();

        canvas.translate(10, 10);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(mRectF, 20, 60, true, mPaint);

        canvas.restore();
        canvas.save();

        canvas.translate(0, 10);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(mRectF, 80, 100, true, mPaint);

        canvas.restore();
        canvas.save();

        canvas.translate(-10, -10);
        mPaint.setColor(Color.RED);
        canvas.drawArc(mRectF, 180, 120, true, mPaint);

        canvas.restore();
        canvas.save();

        canvas.translate(10, -5);
        mPaint.setColor(Color.parseColor("#ff00ff"));
        canvas.drawArc(mRectF, 300, 60, true, mPaint);

        canvas.restore();
    }

}
