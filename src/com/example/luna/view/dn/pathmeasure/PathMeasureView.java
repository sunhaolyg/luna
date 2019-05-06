package com.example.luna.view.dn.pathmeasure;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.luna.R;
import com.example.luna.base.BaseView;


public class PathMeasureView extends BaseView {

    private Path mPathCircle;
    private Path mPathRec;
    private Paint mPaint;

    private int mWidth, mHeight;
    private Bitmap mBitmap;

    private PathMeasure mMeasure;
    private Matrix mMatrix;

    private float mPercent;

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        mPathCircle = new Path();
        mPathRec = new Path();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_back);

        mMatrix = new Matrix();

        ValueAnimator anim = ObjectAnimator.ofFloat(0, 1);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setDuration(10000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mPathCircle.addCircle(mWidth / 2, mHeight / 2, 200, Path.Direction.CW);
        int x = mWidth/2;
        int y = mHeight/2;
        mPathRec.addRect(x-200,y-200,x+200,y+200,Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPathCircle, mPaint);

        drawAnim(canvas,mPathCircle);

        canvas.drawPath(mPathRec, mPaint);

        drawAnim(canvas,mPathRec);

    }

    private void drawAnim(Canvas canvas,Path path) {
        float[] pos = new float[2];
        float[] tan = new float[2];
        mMeasure = new PathMeasure(path, false);
        float lenght = mMeasure.getLength();
        mMeasure.getPosTan(lenght * mPercent, pos, tan);

        mMatrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        mMatrix.postRotate(degrees - 180, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);

        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }
}
