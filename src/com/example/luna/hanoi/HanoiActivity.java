package com.example.luna.hanoi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.activity.view.WaterFallActivity;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.utils.SpUtils;

import java.util.Random;

public class HanoiActivity extends BaseActivity {

    private HanoiLayout mParentView;
    public static final int ITEM_HEIGHT = 50;
    public static final int ITEM_DIVIDER_HEIGHT = 2;
    private int mScreenWidth = 0;

    private TextView mCount, mSuccess;
    private int mMoveCount;

    private int mCurrentCount = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanoi);
        mParentView = findViewById(R.id.hanoi_layout);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mCount = findViewById(R.id.move_count);
        mSuccess = findViewById(R.id.mission_success);
        init(mCurrentCount);
        listener();
        Logutils.d("WaterFallLayout", "context =" + mContext);
    }


    private void init(int count) {
        int itemMaxWidth = mScreenWidth / 3;
        int delta = (itemMaxWidth - ITEM_HEIGHT) / (count - 1);
        for (int i = count - 1; i >= 0; i--) {
            RectImageView iv = new RectImageView(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ITEM_HEIGHT + delta * i, ITEM_HEIGHT);
            iv.setLayoutParams(lp);
            iv.setBackgroundColor(Color.GRAY);
            mParentView.addView(iv);
        }
        for (int i = 0; i < 3; i++) {
            View v = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(4, count * (ITEM_HEIGHT + ITEM_DIVIDER_HEIGHT));
            v.setLayoutParams(lp);
            v.setBackgroundColor(Color.DKGRAY);
            v.setTag(i);
            mParentView.addView(v);
        }
        mParentView.setSuccess(false);
        mSuccess.setText("");
        mMoveCount = 0;
        String s = getString(R.string.move_count);
        mCount.setText(s + mMoveCount);
    }

    public void hanoi(View v) {
        switch (v.getId()) {
            case R.id.increase:
                mCurrentCount++;
                break;
            case R.id.decrease:
                mCurrentCount--;
                break;
        }
        mParentView.removeAllViews();
        init(mCurrentCount);
    }

    public void listener() {
        mParentView.setOnMoveListener(new HanoiLayout.OnMoveListener() {
            @Override
            public void onCountChange() {
                mMoveCount++;
                String s = getString(R.string.move_count);
                mCount.setText(s + mMoveCount);
            }

            @Override
            public void onMissionSuccess() {
                int value = SpUtils.getInt(mContext, "" + mCurrentCount);
                if (value == 0) {
                    SpUtils.putInt(mContext, mCurrentCount + "", mMoveCount);
                    value = mMoveCount;
                }
                if (value > mMoveCount) {
                    SpUtils.putInt(mContext, mCurrentCount + "", mMoveCount);
                }
                mSuccess.setText("MISSION SUCCESS!!!" + "\\t" + "最高记录是：" + value);
            }
        });
    }
}
