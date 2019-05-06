package com.example.luna.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import com.example.luna.utils.Logutils;


import java.lang.ref.WeakReference;


/**
 * Created by sunhao on 17-10-11.
 */

public class XFerModeView extends ImageView {

    private static final String TAG = "XFerModeView";

    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    private static final int DEFALUT_ROUND_RADIUS = 10;
    private int mBorderRadius = DEFALUT_ROUND_RADIUS;

    private Paint mPaint;
    private Xfermode mXfermode;
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    public XFerModeView(Context context) {
        this(context, null);
    }

    public XFerModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
//
//        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
//
//        mBorderRadius = a.getDimensionPixelSize(
//                R.styleable.RoundImageView_borderRadius, (int) TypedValue
//                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                DEFALUT_ROUND_RADIUS, getResources()
//                                        .getDisplayMetrics()));// 默认为10dp
//        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Bitmap bmp = mWeakBitmap == null ? null : mWeakBitmap.get();
        if (bmp == null || bmp.isRecycled()) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int w = drawable.getIntrinsicWidth();
                int h = drawable.getIntrinsicHeight();
                Logutils.d(TAG, "w=" + w + ",h=" + h);
                bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas drawCanvas = new Canvas(bmp);

                float scale = 1.0f;
                if (type == TYPE_ROUND) {
                    scale = Math.max(getWidth() * 1.0f / w, getHeight() * 1.0f / h);
                } else {
                    scale = getWidth() * 1.0f / Math.min(w, h);
                }
                drawable.setBounds(0, 0, (int) scale * w, (int) scale * h);
                drawable.draw(drawCanvas);
                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getBitmap();
                }

                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);

                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);
                drawCanvas.drawBitmap(bmp, 0, 0, null);
                mWeakBitmap = new WeakReference<Bitmap>(bmp);
            }
        }
        if (bmp != null) {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bmp, 0, 0, mPaint);
        }
    }

    private Bitmap getBitmap() {
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mBorderRadius, mBorderRadius, paint);
        } else {
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        }
        return bmp;
    }

}
