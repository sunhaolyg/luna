package com.example.luna.notification;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class ShowContentActivity extends BaseActivity {

    private TextView mShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        mShow = findViewById(R.id.tv_show);
       /* Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, show ? 1 : 0);
        Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.LOCK_SCREEN_SHOW_NOTIFICATIONS, enabled ? 1 : 0);*/
        refresh();
    }

    public void showContent(View v) {
        switch (v.getId()) {
            case R.id.pri_0:
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, 0);
                break;
            case R.id.pri_1:
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, 1);
                break;
            case R.id.pub_0:
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_SHOW_NOTIFICATIONS, 0);
                break;
            case R.id.pub_1:
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_SHOW_NOTIFICATIONS, 1);
                break;
            case R.id.custom_get:
                Settings.Secure.putInt(getContentResolver(), "lock_screen_custom_show_notifications", 0);
                break;
            case R.id.custom_set:
                Settings.Secure.putInt(getContentResolver(), "lock_screen_custom_show_notifications", 1);
                break;
        }
        refresh();
    }

    private void refresh() {
        int pri = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, 0);
        int pub = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCK_SCREEN_SHOW_NOTIFICATIONS, 0);
        int custom = Settings.Secure.getInt(getContentResolver(), "lock_screen_custom_show_notifications", 0);
        String content = "pri = " + pri + ",pub = " + pub + ",custom = " + custom;
        mShow.setText(content);
    }

}
