package com.example.luna.activity.panel;

import android.os.Bundle;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class PanelActivity extends BaseActivity {

    private PanelView mPanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        mPanelView = (PanelView) findViewById(R.id.panelview);
        mPanelView.addTile(new WifiTile(this));
        mPanelView.addTile(new LocationTile(this));
    }
}
