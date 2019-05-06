package com.example.luna.privision;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

public class FingerPrintActivity extends BaseActivity implements View.OnClickListener {

    private Button mDis, mNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        mDis = findViewById(R.id.finger_dis);
        mNext = findViewById(R.id.finger_next);
        mDis.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.finger_dis) {
            finish();
        }
        if (v.getId() == R.id.finger_next) {
            startSecureActivity();
        }
    }

    private void startSecureActivity() {
        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintEnrollIntroduction"));
//        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.SetupFingerprintEnrollIntroduction"));
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.SetupFingerprintEnrollIntroduction"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("provision", true);
        startActivityForResult(intent,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logutils.d("RegisterCodeTAG","result = "+resultCode+",request = "+requestCode);
    }
}
