package com.example.luna.activity.panel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.example.luna.activity.panel.PanelTile;

public class PanelView extends ViewGroup {

    private int mWidth, mHeight;
    private int mColumn;
    private int mRow;
    private int mChildWidth, mChildHeight;

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mColumn = 3;

    }

    public void addTile(final PanelTile tile) {
        View child = tile.createTileView();
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tile.handleClick();
            }
        });
        addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mWidth = width;
        mChildHeight = mChildWidth = width / mColumn;
        int childCount = getChildCount();
        mRow = childCount / mColumn + 1;
        mHeight = mRow * mChildHeight;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.measure(mChildWidth, mChildHeight);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int row, col;
        int left = 0, top = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            col = i % mColumn;
            row = i / mColumn;
            if (col == 0) {
                left = 0;
            }
            top = row * mChildHeight;
            child.layout(left, top, left + mChildWidth, top + mChildHeight);
            left += mChildWidth;
        }
    }
}
