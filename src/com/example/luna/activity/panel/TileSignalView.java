package com.example.luna.activity.panel;

import android.content.Context;
import android.widget.ImageView;

public class TileSignalView extends TileBaseView {

    public TileSignalView(Context context, PanelTile tile) {
        super(context, tile);
        addImageView(tile);
    }

    private void addImageView(PanelTile tile){
        if (tile.getImage() != 0) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(tile.getImage());
            addView(iv);
        }
    }
}
