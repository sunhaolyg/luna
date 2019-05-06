package com.example.luna.activity.panel;

import android.content.Context;
import android.view.View;
import com.example.luna.R;
import com.example.luna.utils.Logutils;

public class WifiTile extends PanelTile {

    @Override
    public String getString() {
        return "wifi";
    }

    @Override
    public int getImage() {
        return R.drawable.ic_back;
    }

    public WifiTile(Context context) {
        super(context);
    }

    @Override
    public void handleClick() {
        Logutils.d("GuestResumeSessionReceiver","wifi");
    }

    @Override
    public View createTileView() {
        return new TileSignalView(mContext,this);
    }
}
