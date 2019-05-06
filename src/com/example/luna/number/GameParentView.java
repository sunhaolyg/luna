package com.example.luna.number;

import android.content.Context;
import android.content.Entity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class GameParentView extends FrameLayout {

    private List<NumberEntity> mEntities;
    private NumberTouchHelper mTouchHelper;

    private NumberEntity mDropView;
    private NumberEntity mEmtpyView;
    private int mCurrentEmpty = 0;

    private int mWidth;

    public GameParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mEntities = new ArrayList<>();
        mTouchHelper = new NumberTouchHelper(mListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    NumberTouchHelper.OnTouchMoveListener mListener = new NumberTouchHelper.OnTouchMoveListener() {
        @Override
        public boolean onStart(int index) {
//            Logutils.i("WaterFallLayout", "index=" + index + ",x = " + x + ",y = " + y);
            if (index == mCurrentEmpty || index == -1) {
                return false;
            }
            int orientation = mTouchHelper.isAdjoin(index, mCurrentEmpty);
            if (orientation == 2) {
                return false;
            }
            mTouchHelper.setOrientation(orientation);
            mDropView = mEntities.get(index);
            mEmtpyView = mEntities.get(mCurrentEmpty);
            return true;
        }

        @Override
        public void onX(int x) {
            int max = NumberConfig.NUMBER_ICON_WIDTH;
            if (x > max) {
                x = max;
            } else if (x < -max) {
                x = -max;
            }
            mDropView.getView().setTranslationX(x);
        }

        @Override
        public void onY(int y) {
            int max = NumberConfig.NUMBER_ICON_HEIGHT;
            if (y > max) {
                y = max;
            } else if (y < -max) {
                y = -max;
            }
            mDropView.getView().setTranslationY(y);
        }

        @Override
        public void complete() {
            float x = mDropView.getView().getTranslationX();
            float y = mDropView.getView().getTranslationY();
            int criticalX = NumberConfig.NUMBER_ICON_WIDTH / 3;
            int criticalY = NumberConfig.NUMBER_ICON_HEIGHT / 3;
            if (Math.abs(x) < criticalX && Math.abs(y) < criticalY) {
                mDropView.getView().setTranslationX(0f);
                mDropView.getView().setTranslationY(0f);
                return;
            }
            mDropView.getView().setTranslationX(0f);
            mDropView.getView().setTranslationY(0f);

            removeAllViews();
            int empty = 0, current = 0;
            for (int i = mEntities.size() - 1; i >= 0; i--) {
                NumberEntity entity = mEntities.get(i);
                if (entity == mDropView) {
                    current = i;
                    mEntities.remove(i);
                } else if (entity == mEmtpyView) {
                    empty = i;
                    mEntities.remove(i);
                }
            }
            if (empty > current) {
                mEntities.add(current, mEmtpyView);
                mEntities.add(empty, mDropView);
            } else {
                mEntities.add(empty, mDropView);
                mEntities.add(current, mEmtpyView);
            }
            mCurrentEmpty = current;
            for (int i = 0; i < mEntities.size(); i++) {
                NumberEntity entity = mEntities.get(i);
                entity.setPosition(i);
                View v = entity.getView();
                if (v.getParent() != null) {
                    ((ViewGroup) v.getParent()).removeView(v);
                }
                addView(v);
            }
            mCountNumber++;
            if (mOnCountListener != null) {
                mOnCountListener.onCount(mCountNumber);
            }
            if (NumberController.isSuccess(mEntities) && mOnCountListener != null) {
                mOnCountListener.onSuccess();
                mCountNumber = 0;
            }
            invalidate();
        }
    };

    public void addChild(View v, NumberEntity entity) {
        addView(v);
        mEntities.add(entity);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchHelper.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int count = getChildCount();

        int w = NumberConfig.NUMBER_ICON_WIDTH;
        int h = NumberConfig.NUMBER_ICON_HEIGHT;
        int l = -w, t = 0;

//        Logutils.i("WaterFallLayout", "count=" + count);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (l + w >= mWidth) {
                l = 0;
                t += h;
            } else {
                l += w;
            }
            child.layout(l, t, l + w, t + h);
        }
    }

    private OnCountListener mOnCountListener;
    private int mCountNumber;

    public void setOnCountListener(OnCountListener listener) {
        mOnCountListener = listener;
    }

    public interface OnCountListener {
        void onCount(int count);

        void onSuccess();
    }
}
