package com.example.luna.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.base.BaseActivity;
import com.example.luna.java.strategy.ChineseStrategy;
import com.example.luna.java.strategy.Strategy;


/**
 * Created by sunhao on 17-10-12.
 */

public class StrategyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translate(new ChineseStrategy(),"");
    }


    private void translate(Strategy strategy, String content) {
        strategy.translate(content);
    }
}
