package com.example.luna.privision;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class AgreementDetailActivity extends ProvisionBaseActivity {

    public static final int PRIVATE_POLICY = 1;
    public static final int LICENCE_POLICY = 2;
    public static final int IMPROVE_POLICY = 3;
    public static final String POLICY_KEY = "policy_key";
    private TextView mDetailView;
    private TextView mTitleView;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_detail);
        mDetailView = findViewById(R.id.agree_detail_text);
        mTitleView = findViewById(R.id.agree_detail_title);
        mBack = findViewById(R.id.agree_detail_back);
        String value = getResValue();
        if (value == null) {
            finish();
            return;
        } else {
            mDetailView.setText(value);
        }
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getResValue() {
        Intent intent = getIntent();
        int type = intent.getIntExtra(POLICY_KEY, 0);
        switch (type) {
            case PRIVATE_POLICY:
                mTitleView.setText(getString(R.string.secret_private_title));
                return getResources().getString(R.string.private_policy);
            case LICENCE_POLICY:
                mTitleView.setText(getString(R.string.secret_licence_title));
                return getResources().getString(R.string.license_policy);
            case IMPROVE_POLICY:
                mTitleView.setText(getString(R.string.secret_improve_title));
                return getResources().getString(R.string.improve_policy);
            default:
                return null;
        }
    }
}
