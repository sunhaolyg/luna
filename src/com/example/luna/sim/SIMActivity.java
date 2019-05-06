package com.example.luna.sim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

import java.util.List;

public class SIMActivity extends BaseActivity {

    TextView subid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
        subid = findViewById(R.id.getsubid);
        getSubid();
    }

    private void getSubid() {
        int id = SubscriptionManager.getDefaultDataSubscriptionId();
        int mPhoneCount = TelephonyManager.getDefault().getPhoneCount();

        List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
        String infos = "";
        for (SubscriptionInfo info : list) {
            infos += info.toString();
        }
//        if (id == 1 && list.size() != 0) {
//            int id2 = list.get(0).getSubscriptionId();
//            SubscriptionManager.from(this).setDefaultDataSubId(-1);
//        }
        TelephonyManager manager = TelephonyManager.from(this);
        boolean enable = manager.getDataEnabled(1);
        subid.setText(id + "," + mPhoneCount + ",size = " + list.size() + ",enable = " + enable);
    }


}
