package com.example.luna.activity.panel;

import android.content.Context;
import com.example.luna.utils.Logutils;

public class LocationTile extends PanelTile {

    public LocationTile(Context context) {
        super(context);
    }

    @Override
    public String getString() {
        return "location";
    }

    @Override
    public void handleClick() {
        Logutils.d("GuestResumeSessionReceiver","location");
    }
}
