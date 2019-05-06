package com.example.luna.markfilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class MarkFilterActivity extends BaseActivity {

    private MarkFilterView mView;
    private boolean mShowing;
    private int mLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markfilter);
        mView = findViewById(R.id.mark_filter_charging);
    }

    public void filter(View v) {
        switch (v.getId()) {
            case R.id.mark_filter:
                if (mShowing) {
                    mView.dismiss();
                } else {
                    mLevel += 10;
                    mView.setLevel(mLevel % 100);
                }
                mShowing = !mShowing;
                break;
        }
    }

}
