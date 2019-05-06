package com.example.luna.click;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

public class ClickActivity extends BaseActivity {

    private ParentView mParentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        mParentView = (ParentView) findViewById(R.id.click_parent);
        ThreePointHelper.getInstance().register(new ThreePointHelper.OnThreePointListener() {
            @Override
            public void takeScreenshot() {

                screenshot();
            }
        });
        mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot();
            }
        });
    }

    private void screenshot() {
        ScreenshotUtil util = new ScreenshotUtil(this);
        util.takeScreenshot(WindowManager.TAKE_SCREENSHOT_FULLSCREEN);
    }
}
