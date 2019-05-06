package com.example.luna.number;

import android.view.MotionEvent;

public class NumberTouchHelper {

    private int mDownX, mDownY;

    private int mOrientation = 2;
    private OnTouchMoveListener mListener;

    private boolean mCanContinue = true;

    public NumberTouchHelper(OnTouchMoveListener listener) {
        mListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                mOrientation = 2;
                if (mListener != null) {
                    mCanContinue = mListener.onStart(getIndex(x, y));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mCanContinue) {
                    return false;
                }
                if (mOrientation == 2) {

                } else if (mOrientation == 4) {
                    int delta = x - mDownX;
                    if (mListener != null && delta < 0) {
                        mListener.onX(delta);
                    }
                } else if (mOrientation == 8) {
                    int delta = y - mDownY;
                    if (mListener != null && delta < 0) {
                        mListener.onY(delta);
                    }
                } else if (mOrientation == 16) {
                    int delta = x - mDownX;
                    if (mListener != null && delta > 0) {
                        mListener.onX(delta);
                    }
                } else if (mOrientation == 32) {
                    int delta = y - mDownY;
                    if (mListener != null && delta > 0) {
                        mListener.onY(delta);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!mCanContinue) {
                    mCanContinue = true;
                    return false;
                }
                mOrientation = 2;
                if (mListener != null) {
                    mListener.complete();
                }
                break;
        }
        return true;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getIndex(int x, int y) {
        y -= 216;
        int rowCount = NumberConfig.NUMBER_ROW_COUNT;
        int columnCount = NumberConfig.NUMBER_COLUMN_COUNT;
        int w = NumberConfig.NUMBER_ICON_WIDTH;
        int h = NumberConfig.NUMBER_ICON_HEIGHT;

        int row = 0, column = 0;

        for (int i = 1; i <= rowCount; i++) {
            if (y < h * i) {
                for (int j = 1; j <= columnCount; j++) {
                    if (x < w * j) {
                        row = i - 1;
                        column = j - 1;
                        break;
                    }
                }
                break;
            }
        }
        return row * columnCount + column;
    }

    /**
     * @param index
     * @param curentEmpty
     * @return 2:none,4:l,8:t,16:r,32:b
     */
    public int isAdjoin(int index, int curentEmpty) {
        return getAdjoin(index, curentEmpty, NumberConfig.NUMBER_COLUMN_COUNT);
    }

    private int getAdjoin(int index, int curentEmpty, int columnCount) {
        if (index - 1 == curentEmpty) {
            return 4;
        } else if (index + 1 == curentEmpty) {
            return 16;
        } else if (index - columnCount == curentEmpty) {
            return 8;
        } else if (index + columnCount == curentEmpty) {
            return 32;
        }
        return 2;
    }

    public interface OnTouchMoveListener {

        boolean onStart(int index);

        void onX(int x);

        void onY(int y);

        void complete();
    }

}
