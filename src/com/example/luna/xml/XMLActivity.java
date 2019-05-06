package com.example.luna.xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

public class XMLActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        String nu="pk10 b02";
        String real = nu.substring(6,8);
        Logutils.e("MainActivityTAG","read = "+real);
    }

    public void xml(View view) {
        switch (view.getId()) {
            case R.id.invoice:
                startActivity(new Intent(this, InvoiceActivity.class));
                break;
        }
    }

}
