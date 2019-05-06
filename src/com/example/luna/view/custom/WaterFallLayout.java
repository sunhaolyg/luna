package com.example.luna.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.example.luna.utils.Logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-10-10.
 */

public class WaterFallLayout extends FrameLayout {

    private static final String TAG = "WaterFallLayout";

    public WaterFallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;
        int count = getChildCount();
        int lineWidth = 0, lineHeight = 0;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
            if (lineWidth + cWidth > sizeWidth) {
                lineWidth = cWidth;
                height += lineHeight;
                width = Math.max(width, lineWidth);
                lineHeight = cHeight;
            } else {
                lineWidth += cWidth;
                lineHeight = Math.max(lineHeight, cHeight);
            }
        }
        Logutils.i("WaterFallLayout", "sizeWidth=" + width + ",sizeHeight=" + height);

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        List<WaterFallBean> beans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            beans.add(new WaterFallBean());
        }

        int l = 0, t = 0, r = 0, b = 0;
        int count = getChildCount();
        int lineWidth = 0, lineHeight = 0;
        int width = getWidth();
        Logutils.d(TAG, "width=" + width + ",height=" + getHeight() + ",count=" + count);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();
//            Logutils.d(TAG, "cWidth=" + cWidth + ",cHeight=" + cHeight + ",lineWidth=" + lineWidth + ",lineHeight=" + lineHeight);
//            if (lineWidth + cWidth > width) {
//                t += lineHeight;
//                lineWidth = 0;
//                lineHeight = cHeight;
//                l = 0;
//                r = 0;
//                b += lineHeight;
//            }
//            l = r;
//            lineWidth += cWidth;
//            r += cWidth;
//            b = t + cHeight;
//            lineHeight = Math.max(lineHeight, cHeight);

            int index = indexByHeight(beans);
            l = index * 216;
            t = beans.get(index).getHeight();
            r = l + cWidth;
            b = t + cHeight;
            Logutils.d(TAG, "index=" + index + ",cWidth=" + cWidth + ",cHeight=" + cHeight);
            Logutils.d(TAG, "l=" + l + ",t=" + t + ",r=" + r + ",b=" + b);
            beans.get(l / 216).setHeight(beans.get(l / 216).getHeight() + cHeight);

            child.layout(l, t, r, b);
        }
    }

    public int indexByHeight(List<WaterFallBean> beans) {
        int temp = beans.get(0).getHeight();
        for (int i = 0; i < beans.size() - 1; i++) {
            if (temp > beans.get(i + 1).getHeight()) {
                temp = beans.get(i + 1).getHeight();
            }
        }
        for (int i = 0; i < beans.size(); i++) {
            if (temp == beans.get(i).getHeight()) {
                return i;
            }
        }
        return 0;
    }
}
