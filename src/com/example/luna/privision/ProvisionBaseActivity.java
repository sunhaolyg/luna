package com.example.luna.privision;

import android.os.Bundle;
import android.view.KeyEvent;
import com.example.luna.base.BaseActivity;
import android.app.StatusBarManager;

public class ProvisionBaseActivity extends BaseActivity {
    private StatusBarManager mStatusBarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().hide();
        super.onCreate(savedInstanceState);
        mStatusBarManager = (StatusBarManager)getSystemService(getApplicationContext().STATUS_BAR_SERVICE);
        mStatusBarManager.disable(StatusBarManager.DISABLE_EXPAND);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        return true;
//    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusBarManager.disable(StatusBarManager.DISABLE_NONE);
    }
}
