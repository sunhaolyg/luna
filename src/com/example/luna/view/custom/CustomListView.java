package com.example.luna.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import com.example.luna.utils.Logutils;


/**
 * Created by sunhao on 17-10-10.
 */

public class CustomListView extends ListView {

    private static final String TAG = "CustomListView";

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        int count = getChildCount();
//        int lineHeight = 0;
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            int cHeight = child.getMeasuredHeight();
//            Logutils.d(TAG, "cHeight=" + cHeight);
//            lineHeight += cHeight;
//        }
//        Logutils.d(TAG, "lineHeight=" + lineHeight + ",count=" + getAdapter().getCount());
//    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
//        int count = getAdapter().getCount();
        int count = getChildCount();
        int lineHeight = -50;
        int decrease=0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
//            View child = getAdapter().getView(i,null,this);
            lineHeight += 50;
            child.layout(0, lineHeight, 900, lineHeight+100);
        }
        invalidate();
        Logutils.d(TAG, "lineHeight=" + lineHeight + ",count=" + count);
    }
}
