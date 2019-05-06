package com.example.luna.poker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.poker.cow.CowActivity;
import com.example.luna.poker.gold.GoldActivity;

public class PokerActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);
    }

    public void poker(View v) {
        switch (v.getId()) {
            case R.id.poker_gold:
                startActivity(new Intent(this, GoldActivity.class));
                break;
            case R.id.poker_cow:
                startActivity(new Intent(this, CowActivity.class));
                break;
        }
    }


}
