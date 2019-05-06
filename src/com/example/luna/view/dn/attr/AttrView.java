package com.example.luna.view.dn.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.luna.R;
import com.example.luna.utils.Logutils;

public class AttrView extends View {

    public AttrView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /*TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sss);
        float size = a.getDimension(R.styleable.sss_size, 0);

        float type = a.getDimension(R.styleable.sss_type,0);

        String hint = a.getString(R.styleable.sss_hint);

        Logutils.d("MainAainActivityTAG", "size attr = " + size+",type = "+type+",hint = "+hint);
        a.recycle();*/

    }

}
