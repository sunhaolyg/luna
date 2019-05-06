package com.example.luna.view.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.example.luna.utils.Logutils;

public class RotationCircleImageView extends ImageView {

    private ObjectAnimator mObjectAnimator;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    private static final int DEFALUT_ROUND_RADIUS = 10;

    private int mBorderRadius = DEFALUT_ROUND_RADIUS;
    private Paint mBitmapPaint;
    private int mRadius;

    private Matrix mMatrix;

    private BitmapShader mBitmapShader;

    private int mWidth;
    private RectF mRoundRect;

    public RotationCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(mAnimation, 500);
    }

    @Override
    protected void onDetachedFromWindow() {
        mObjectAnimator.cancel();
        removeCallbacks(mAnimation);
        super.onDetachedFromWindow();
    }


    private Runnable mAnimation = new Runnable() {
        @Override
        public void run() {
            startAnimation();
        }
    };

    private void startAnimation() {
        mObjectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0.0F, 360.0F);
        mObjectAnimator.setDuration(40000);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Logutils.d("GuestResumeSessionReceiver","mea wid = "+getMeasuredWidth()+",mea hei = "+getMeasuredHeight()+",wid = "+width+",hei = "+height);
        mRadius = mWidth / 2;
//        setMeasuredDimension(mWidth, mWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (getDrawable() == null) {
            return;
        }
        setupShader();
        canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
    }

    private void setupShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bmp = drawableToBitmap(drawable);
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
        scale = mWidth * 1.0f / bSize;
        Logutils.d("GuestResumeSessionReceiver","scale = "+scale+"mwidth = "+mWidth);
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bmp;
    }
}
