package com.example.luna.number;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.example.luna.utils.Logutils;

public class NumberEntity {

    private int mPosition;
    private TextView mView;

    public NumberEntity(int position, TextView view) {
        this.mPosition = position;
        this.mView = view;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
        Logutils.i("WaterFallLayout", "mPosition=" + mPosition + ",getNumber = " + getNumber());
    }

    public TextView getView() {
        return mView;
    }

    public void setView(TextView view) {
        this.mView = view;
    }

    public int getNumber() {
        String txt = mView.getText().toString();
        if (TextUtils.isEmpty(txt)) {
            return 0;
        }
        return Integer.parseInt(txt);
    }

}
