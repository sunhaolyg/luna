package com.example.luna.activity.panel;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TileBaseView extends LinearLayout {

    public TileBaseView(Context context, PanelTile tile) {
        super(context);
        addTextView(tile.getString());
        setBackgroundColor(Color.parseColor("#ff00ff"));
    }

    private void addTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        addView(tv);
    }
}
