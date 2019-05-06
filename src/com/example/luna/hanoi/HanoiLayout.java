package com.example.luna.hanoi;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.luna.utils.Logutils;

import java.util.ArrayList;
import java.util.List;

public class HanoiLayout extends FrameLayout {

    public static final int ITEM_HEIGHT = HanoiActivity.ITEM_HEIGHT;
    public static final int ITEM_DIVIDER_HEIGHT = HanoiActivity.ITEM_DIVIDER_HEIGHT;
    private int mHeight, mWidth;

    private int mOritention;
    private boolean mLeftUp, mCenterUp, mRightUp;
    private RectImageView mCurrentUpView;

    private boolean mMissionSuccess;

    public HanoiLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);

//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
//
//        int width = mWidth, height = mHeight;
//        int count = getChildCount();
//        int lineWidth = 0, lineHeight = 0;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int l = 0, t = 0, r = 0, b = 0;
        int count = getChildCount();
        int baseX = mWidth / 6;

        int dividerHeight = 0;

        List<RectImageView> lefts = new ArrayList<>();
        List<RectImageView> centers = new ArrayList<>();
        List<RectImageView> rights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            if (child instanceof RectImageView) {
                RectImageView iv = (RectImageView) child;
                int oritention = iv.getOritention();
                if (oritention == 0) {
                    lefts.add(iv);
                } else if (oritention == 1) {
                    centers.add(iv);
                } else {
                    rights.add(iv);
                }
            } else {
                dividerHeight = cHeight;
                int oritention = 0;
                Object o = child.getTag();
                if (o instanceof Integer) {
                    oritention = (int) o;
                }
                baseX = getBaseX(oritention);
                b = mHeight - ITEM_HEIGHT;
                l = baseX - cWidth / 2;
                r = baseX + cWidth / 2;
                t = b - cHeight;
                child.layout(l, t, r, b);
            }
        }

        //left
        for (int i = 0; i < lefts.size(); i++) {
            RectImageView iv = lefts.get(i);
            int cWidth = iv.getMeasuredWidth();
            int cHeight = iv.getMeasuredHeight();

            baseX = getBaseX(iv.getOritention());
            if (i == 0) {
                b = mHeight - cHeight;
            } else {
                b = b - cHeight - 2;
            }
            if (i == lefts.size() - 1 && mLeftUp) {
                mCurrentUpView = iv;
                b = mHeight - dividerHeight - cHeight;
            }

            l = baseX - cWidth / 2;
            r = baseX + cWidth / 2;
            t = b - cHeight;
            iv.layout(l, t, r, b);
        }

        //center
        for (int i = 0; i < centers.size(); i++) {
            RectImageView iv = centers.get(i);
            int cWidth = iv.getMeasuredWidth();
            int cHeight = iv.getMeasuredHeight();

            baseX = getBaseX(iv.getOritention());
            if (i == 0) {
                b = mHeight - cHeight;
            } else {
                b = b - cHeight - 2;
            }
            if (i == centers.size() - 1 && mCenterUp) {
                mCurrentUpView = iv;
                b = mHeight - dividerHeight - cHeight;
            }

            l = baseX - cWidth / 2;
            r = baseX + cWidth / 2;
            t = b - cHeight;
            iv.layout(l, t, r, b);
        }

        //right
        for (int i = 0; i < rights.size(); i++) {
            RectImageView iv = rights.get(i);
            int cWidth = iv.getMeasuredWidth();
            int cHeight = iv.getMeasuredHeight();

            baseX = getBaseX(iv.getOritention());
            if (i == 0) {
                b = mHeight - cHeight;
            } else {
                b = b - cHeight - 2;
            }
            if (i == rights.size() - 1 && mRightUp) {
                mCurrentUpView = iv;
                b = mHeight - dividerHeight - cHeight;
            }

            l = baseX - cWidth / 2;
            r = baseX + cWidth / 2;
            t = b - cHeight;
            iv.layout(l, t, r, b);
        }
    }

    private int getBaseX(int oritention) {
        int result = mWidth / 6;
        switch (oritention) {
            case 0:
                result = mWidth / 6;
                break;
            case 1:
                result = mWidth / 2;
                break;
            case 2:
                result = mWidth / 6 * 5;
                break;
        }
        return result;
    }

    private int getOritention(int x) {
        if (x > mWidth / 3 * 2) {
            return 2;
        } else if (x > mWidth / 3) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean canMoveToLeft() {
        if (mCurrentUpView == null) {
            return false;
        }
        int current = mCurrentUpView.getOritention();
        if (current <= 0) {
            return false;
        }
        return canMove(current - 1);
    }

    public boolean canMOveToRight() {
        if (mCurrentUpView == null) {
            return false;
        }
        int current = mCurrentUpView.getOritention();
        if (current >= 2) {
            return false;
        }
        return canMove(current + 1);
    }

    public boolean canMoveToTarget(int targetOritention) {
        if (mCurrentUpView == null) {
            return false;
        }
        return canMove(targetOritention);
    }

    /**
     * @param oritention 0:left
     *                   1:right
     * @return
     */
    private boolean canMove(int targetOritention) {
        int count = getChildCount();
        int currentWidth = mCurrentUpView.getWidth();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof RectImageView) {
                RectImageView iv = (RectImageView) child;
                if (iv.getOritention() == targetOritention) {
                    if (iv.getWidth() < currentWidth) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void moveToTarget(int tarOritention) {
        if (mCurrentUpView != null) {
            mLeftUp = mCenterUp = mRightUp = false;
            mCurrentUpView.setOritention(tarOritention);
            if (mListener != null) {
                mListener.onCountChange();
            }
            resetAllView();
        }
    }

    public void moveToLeft() {
        if (mCurrentUpView != null && (mLeftUp || mCenterUp || mRightUp)) {
            mCurrentUpView.setOritention(mCurrentUpView.getOritention() - 1);
            mLeftUp = mCenterUp = mRightUp = false;
            resetAllView();
        }
    }

    public void moveToRight() {
        if (mCurrentUpView != null && (mLeftUp || mCenterUp || mRightUp)) {
            mCurrentUpView.setOritention(mCurrentUpView.getOritention() + 1);
            mLeftUp = mCenterUp = mRightUp = false;
            resetAllView();
        }
    }

    private void resetAllView() {
        int count = getChildCount();
        List<RectImageView> lefts = new ArrayList<>();
        List<RectImageView> centers = new ArrayList<>();
        List<RectImageView> rights = new ArrayList<>();
        List<View> dividers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof RectImageView) {
                RectImageView iv = (RectImageView) child;
                if (iv != mCurrentUpView) {
                    int oritention = iv.getOritention();
                    if (oritention == 0) {
                        lefts.add(iv);
                    } else if (oritention == 1) {
                        centers.add(iv);
                    } else {
                        rights.add(iv);
                    }
                }
            } else {
                dividers.add(child);
            }
        }
        int currentUpviewOritention = mCurrentUpView.getOritention();
        if (currentUpviewOritention == 0) {
            lefts.add(mCurrentUpView);
        } else if (currentUpviewOritention == 1) {
            centers.add(mCurrentUpView);
        } else {
            rights.add(mCurrentUpView);
        }
        removeAllViews();
        mMissionSuccess = false;
        mMissionSuccess = lefts.size() == 0 && centers.size() == 0 && rights.size() != 0;
        for (int i = 0; i < lefts.size(); i++) {
            addView(lefts.get(i));
            Logutils.d("WaterFallLayout", "lefts =" + lefts.get(i).getWidth());

        }
        for (int i = 0; i < centers.size(); i++) {
            addView(centers.get(i));
            Logutils.d("WaterFallLayout", "centers =" + centers.get(i).getWidth());
        }
        int rightWidth = Integer.MAX_VALUE;
        for (int i = 0; i < rights.size(); i++) {
            addView(rights.get(i));
            int width = rights.get(i).getWidth();
            if (width > rightWidth) {
                mMissionSuccess = false;
            } else {
                rightWidth = width;
            }
            Logutils.d("WaterFallLayout", "rights =" + rights.get(i).getWidth());
        }
        if (mMissionSuccess && mListener != null) {
            mListener.onMissionSuccess();
        }
        for (int i = 0; i < dividers.size(); i++) {
            addView(dividers.get(i));
        }

    }

    private void addViewList(List<RectImageView> views) {
        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mMissionSuccess) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                mOritention = getOritention(x);

                if (mLeftUp || mCenterUp || mRightUp) {
                    if (canMoveToTarget(mOritention)) {
                        moveToTarget(mOritention);
                    }
                } else {
                    if (mOritention == 0) {
                        mLeftUp = !mLeftUp;
                    } else if (mOritention == 1) {
                        mCenterUp = !mCenterUp;
                    } else {
                        mRightUp = !mRightUp;
                    }
                }
                requestLayout();
                break;
        }
        return true;
    }

    public interface OnMoveListener {
        void onCountChange();

        void onMissionSuccess();
    }

    public void setSuccess(boolean success) {
        mMissionSuccess = success;
    }

    private OnMoveListener mListener;

    public void setOnMoveListener(OnMoveListener listener) {
        mListener = listener;
    }
}
