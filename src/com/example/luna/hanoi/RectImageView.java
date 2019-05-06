package com.example.luna.hanoi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RectImageView extends ImageView {

    /**
     * 0:left
     * 1:center
     * 2:right
     */
    private int oritention;

    public RectImageView(Context context) {
        super(context);
    }

    public RectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public int getOritention() {
        return oritention;
    }

    /**
     * @param oritention 0:left
     *                   1:center
     *                   2:right
     */
    public void setOritention(int oritention) {
        this.oritention = oritention;
    }

    @Override
    public String toString() {
        return "RectImageView{" +
                "oritention=" + oritention +
                '}';
    }
}
