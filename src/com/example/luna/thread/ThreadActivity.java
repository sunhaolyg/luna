package com.example.luna.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class ThreadActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
    }

    public void threadClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.thread_name:
                intent = new Intent(this, ThreadNameActivity.class);
                break;
            case R.id.thread_pool:
                intent = new Intent(this, ThreadPoolActivity.class);
                break;
        }
        startActivity(intent);
    }

}
