package com.example.luna.activity.panel;

import android.content.Context;
import android.view.View;

public abstract class PanelTile {

    protected Context mContext;

    public PanelTile(Context context) {
        this.mContext = context;
    }

    public abstract String getString();

    public int getImage(){
        return 0;
    }

    public abstract void handleClick();

    public View createTileView(){
        return new TileBaseView(mContext,this);
    }
}
