package com.example.luna.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.utils.Logutils;

public class CardTextView extends TextView {
    public CardTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        apiKey="yunjiasu"
//        card:frameDuration="1000"
//        card:focusable="true"
//        card:orientation="horizontal"
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
//        float size = a.getDimension(R.styleable.MyView_textSize, 1);
        int size = a.getDimensionPixelSize(R.styleable.MyView_textSize, 1);
        String apiKey = a.getString(R.styleable.MyView_apiKey);
        int frameDuration = a.getInt(R.styleable.MyView_frameDuration, 1);
        boolean focusable = a.getBoolean(R.styleable.MyView_focusable, false);
        int horizontal = a.getInt(R.styleable.MyView_orientation, -1);
        Logutils.d("GuestResumeSessionReceiver", "size = " + size + ",apiKey = " + apiKey
                + ",frameDuration = " + frameDuration + ",focusable = " + focusable + ",horizontal = " + horizontal);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setText("ddd\nddd");
    }
}
