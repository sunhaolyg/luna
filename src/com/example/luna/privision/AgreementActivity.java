package com.example.luna.privision;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.utils.SpannableUtils;

public class AgreementActivity extends ProvisionBaseActivity {

    private Button agree_next;
    private TextView agree_thumb_text, agree_improve_text;
    private ImageView iv_agree_improve;
    private View agree_improve;
    private boolean mIsAgreeImprove = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        Intent intent = getIntent();
        boolean complete = intent.getBooleanExtra("complete", false);
        Logutils.d("RegisterCodeTAG", "complete = " + complete);
        agree_next = findViewById(R.id.agree_next);
        agree_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AgreementActivity.this, FingerPrintActivity.class));
            }
        });

        agree_thumb_text = findViewById(R.id.agree_thumb_text);
        agree_improve_text = findViewById(R.id.agree_improve_text);

        agree_thumb_text.setText(getResources().getString(R.string.secret_thumb_licence_start));

        SpannableUtils.setSpannable(agree_thumb_text, getString(R.string.secret_key_licence),
                SpannableUtils.getClickableSpan(AgreementDetailActivity.LICENCE_POLICY, this));
        agree_thumb_text.append(getString(R.string.secret_thumb_licence_end));

        agree_improve_text.append(getString(R.string.secret_thumb_improve_start));
        SpannableUtils.setSpannable(agree_improve_text, getString(R.string.secret_key_improve),
                SpannableUtils.getClickableSpan(AgreementDetailActivity.IMPROVE_POLICY, this));
        agree_improve_text.append(getString(R.string.secret_thumb_improve_end));

        SpannableUtils.setSpannable(agree_improve_text, getString(R.string.secret_key_private),
                SpannableUtils.getClickableSpan(AgreementDetailActivity.PRIVATE_POLICY, this));

        iv_agree_improve = findViewById(R.id.iv_agree_improve);
        agree_improve = findViewById(R.id.agree_improve);
        agree_improve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsAgreeImprove = !mIsAgreeImprove;
                if (mIsAgreeImprove) {
                    iv_agree_improve.setImageResource(R.drawable.agreed);
                } else {
                    iv_agree_improve.setImageResource(R.drawable.no_agreed);
                }
            }
        });

    }

    private void startSecureActivity() {
        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintEnrollIntroduction"));
//        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.SetupFingerprintEnrollIntroduction"));
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintSuggestionActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("provision", true);
        startActivity(intent);

    }
}
