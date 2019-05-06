package com.example.luna.kill;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import android.app.ActivityManagerNative;
import android.app.ActivityManager;
import com.example.luna.utils.Logutils;

import java.io.IOException;

public class KillActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill);
        Button button = findViewById(R.id.kill_p);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logutils.d("GuestResumeSessionReceiver", "onclick");
//                Process.killProcess(Process.myPid());
//                killBackgroundProcesses("com.example.luna");
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                am.forceStopPackage("com.android.music");
            }
        });
    }

    public void killBackgroundProcesses(String packageName) {
        try {
            ActivityManagerNative.getDefault().killBackgroundProcesses(packageName,
                    UserHandle.myUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
