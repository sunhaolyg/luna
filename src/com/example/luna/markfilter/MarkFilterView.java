package com.example.luna.markfilter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import com.example.luna.utils.Logutils;
import com.example.luna.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarkFilterView extends View {

    private static final int MARK_FILTER_RADIUS = 15;
    private static final int CENTER_X = 100, CENTER_Y = 100;
    private static final int RADIUS = 100;

    private Paint mStrokePaint;
    private Paint mArcPaint;
    private Paint mBottomPaint;
    private Paint mDotPaint;
    private Paint mChargingPaint;

    private RectF mRectF;
    private int mStopAngle;

    private int mWidth, mHeight;
    private RectF mBottomRectF;

    private List<CircleUp> mCircleUps = new ArrayList<>();
    private ValueAnimator mDrgeeAnimator;

    private boolean mIsShowing;
    private Path mChargingPath;
    private int mDrawCount;

    public MarkFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        mHeight = (RADIUS * 2 + MARK_FILTER_RADIUS * 4) * 2;
        mWidth = RADIUS * 2 + MARK_FILTER_RADIUS * 4;
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        Resources res = getContext().getResources();

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(res.getColor(R.color.charging_stroke_color));
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setMaskFilter(new BlurMaskFilter(MARK_FILTER_RADIUS, BlurMaskFilter.Blur.SOLID));
        mStrokePaint.setStrokeWidth(20);

        mArcPaint = new Paint();
        mArcPaint.setColor(res.getColor(R.color.charging_arc_color));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(20);
        mArcPaint.setMaskFilter(new BlurMaskFilter(MARK_FILTER_RADIUS, BlurMaskFilter.Blur.SOLID));
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mBottomPaint = new Paint();
        mBottomPaint.setColor(res.getColor(R.color.charging_bottom_color));
        mBottomPaint.setStyle(Paint.Style.STROKE);
        mBottomPaint.setStrokeWidth(50);
//        mBottomPaint.setAlpha(122);
        mBottomPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

        mDotPaint = new Paint();
        mDotPaint.setColor(res.getColor(R.color.charging_dot_color));
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setMaskFilter(new BlurMaskFilter(MARK_FILTER_RADIUS / 2, BlurMaskFilter.Blur.NORMAL));

        mChargingPaint = new Paint();
        mChargingPaint.setColor(res.getColor(R.color.charging_color));
        mChargingPaint.setMaskFilter(new BlurMaskFilter(MARK_FILTER_RADIUS / 2, BlurMaskFilter.Blur.SOLID));

        mRectF = new RectF(MARK_FILTER_RADIUS * 2, MARK_FILTER_RADIUS * 2,
                RADIUS * 2 + MARK_FILTER_RADIUS * 2, RADIUS * 2 + MARK_FILTER_RADIUS * 2);
        mBottomRectF = new RectF(MARK_FILTER_RADIUS * 2, mHeight - 150, RADIUS * 2 + MARK_FILTER_RADIUS * 2, mHeight);

        int w = mWidth / 4;
        int h = w * 3 / 2;
        int a = mWidth / 2 - w / 2;
        int b = mWidth / 2 - h / 2;
        mChargingPath = new Path();
        mChargingPath.moveTo(a + w * 4 / 9, b);
        mChargingPath.lineTo(a, b + h * 6 / 11);
        mChargingPath.lineTo(a + w / 3, b + h * 6 / 11);
        //4
        mChargingPath.lineTo(a + 5, b + h + 10);
        mChargingPath.lineTo(a + w - 5, b + h / 3);
        mChargingPath.lineTo(a + w * 5 / 9, b + h / 3);
        mChargingPath.lineTo(a + w, b);
        mChargingPath.close();

        for (int i = 0; i < 3; i++) {
            CircleUp circleUp = new CircleUp();
            circleUp.start();
            mCircleUps.add(circleUp);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsShowing) {
            drawStroke(canvas);
            drawArc(canvas);
            drawDot(canvas);
            drawChargingPath(canvas);
            drawBottom(canvas);
        }
    }

    private void drawChargingPath(Canvas canvas) {
        canvas.drawPath(mChargingPath, mChargingPaint);
    }

    private void drawBottom(Canvas canvas) {
        canvas.save();
        canvas.translate(0, 150);
        canvas.drawArc(mBottomRectF, 180, 180, false, mBottomPaint);
        canvas.clipRect(0, mBottomRectF.top, mWidth, mHeight);
    }

    private void drawDot(Canvas canvas) {
        Logutils.d("NotificationViewWrapper", "start = " + mCircleUps.size());
        for (CircleUp circleUp : mCircleUps) {
            if (circleUp.start) {
                setDotAlpha(circleUp.y);
                canvas.drawCircle(circleUp.x, circleUp.y, circleUp.radius, mDotPaint);
            }
        }
    }

    private void setDotAlpha(int y) {
        if (y - mRectF.bottom < 30) {
            int delta = (int) (y - mRectF.bottom);
            mDotPaint.setAlpha(255 * delta / 30);
        } else {
            mDotPaint.setAlpha(255);
        }
    }

    private void drawArc(Canvas canvas) {
        canvas.drawArc(mRectF, 90, mStopAngle, false, mArcPaint);
    }

    private void drawStroke(Canvas canvas) {
        canvas.drawCircle(CENTER_X + MARK_FILTER_RADIUS * 2, CENTER_Y + MARK_FILTER_RADIUS * 2, RADIUS, mStrokePaint);
    }

    public void setLevel(int level) {
        mIsShowing = true;
        setVisibility(VISIBLE);
        init();
        int drgee = 360 * level / 100;
        runAnimation(drgee);
    }

    public void dismiss() {
        mIsShowing = false;
        for (CircleUp circleUp : mCircleUps) {
            if (circleUp != null) {
                circleUp.cancel();
            }
        }
        if (mDrgeeAnimator != null) {
            mDrgeeAnimator.cancel();
        }
        mStrokePaint.reset();
        mStrokePaint = null;
        mArcPaint.reset();
        mArcPaint = null;
        mDotPaint.reset();
        mDotPaint = null;
        mBottomPaint.reset();
        mBottomPaint = null;
        mChargingPaint.reset();
        mChargingPaint = null;
        mChargingPath.reset();
        mRectF = null;
        mBottomRectF = null;
        mCircleUps.clear();
        mDrawCount = 0;
        setVisibility(GONE);
    }

    private void runAnimation(int drgee) {
        mDrgeeAnimator = ValueAnimator.ofInt(0, drgee);
        mDrgeeAnimator.setDuration(10000 * drgee / 360);
        mDrgeeAnimator.setInterpolator(new LinearInterpolator());
        mDrgeeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mDrgeeAnimator.start();
        mDrgeeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mStopAngle = value;
                mDrawCount++;
                if (mDrawCount % 4 == 0) {
                    postInvalidate();
                }
            }
        });
    }

    class CircleUp {
        int x;
        int y;
        int radius;
        ValueAnimator vaX;
        ValueAnimator vaY;
        boolean start;

        public CircleUp() {
            setRadius();
            setX();
        }

        private void setRadius() {
            Random random = new Random();
            int next = random.nextInt(4);
            radius = (next + 5) * 2;
        }

        private void setX() {
            Random random = new Random();
            int next = random.nextInt(mWidth / 2);
            x = mWidth / 4 + next;
        }

        private int getDuration() {
            Random random = new Random();
            int next = random.nextInt(4);
            return (next + 6) * 500;
        }

        private void dotAnimation() {
            vaY = ValueAnimator.ofInt(mHeight, (int) mRectF.bottom);
            vaY.setDuration(getDuration());
            vaY.setInterpolator(new LinearInterpolator());
            vaY.start();
            vaY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    y = (int) animation.getAnimatedValue();
                    if (y == mRectF.bottom) {
                        again();
                    }
                }
            });

            vaX = ValueAnimator.ofInt(x, x > mWidth / 2 ? mWidth / 2 : mWidth / 4, x > mWidth / 2 ? mWidth * 3 / 4 : mWidth / 2, x);
            vaX.setDuration(getDuration());
            vaX.setRepeatCount(ValueAnimator.INFINITE);
            vaX.start();
            vaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    x = (int) animation.getAnimatedValue();
                }
            });
        }

        private void again() {
            setRadius();
            setX();
            vaX.cancel();
            vaY.cancel();
            dotAnimation();
        }

        private void cancel() {
            if (vaY != null) {
                vaY.cancel();
            }
            if (vaX != null) {
                vaX.cancel();
            }
            if (handler != null) {
                handler.removeMessages(100);
            }
        }

        private void start() {
            Random random = new Random();
            int next = random.nextInt(5000);
            Message msg = handler.obtainMessage();
            msg.what = 100;
            handler.sendMessageDelayed(msg, next);
        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 100) {
                    start = true;
                    dotAnimation();
                }
            }
        };
    }
}
