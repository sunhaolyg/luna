package com.example.luna.activity.curve;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;


import java.util.Random;

/**
 * Created by sunhao on 17-10-13.
 */

public class CurveActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
        calculate4();
    }

    private void calculate() {
        int a = 66667;
        int sum = 0;
        for (int i = 0; i < 24; i++) {
            int b = (int) (a * 0.06f);
            a -= 4665;
            a += b;
            sum += b;

            Logutils.d("SwitchView", "i=" + i + ",a=" + a + ",b=" + b + ",sum=" + sum);
        }
        Logutils.d("SwitchView", "all=" + (4665 * 24 - 66667) + ",s=" + (4665 - 2777) * 24);
    }

    private void calculate2() {
        int a = 60000;
        int sum = 0;
        for (int i = 0; i < 36; i++) {
            int b = (int) (a * 0.06f);
            a -= 3220;
            a += b;
            sum += b;

            Logutils.d("SwitchView", "i=" + i + ",a=" + a + ",b=" + b + ",sum=" + sum);
        }
    }

    private void calculate3() {
        int a = 400000;
        int sum = 0;
        Random random = new Random();
        for (int i = 1; i < 365 * 5; i++) {
            int ra = 0;
            if (a < 1200000) {

                ra = random.nextInt(40) + 140;
            } else {
                ra = random.nextInt(40) + 160;
            }
            int b = (a * ra / 100000);
            a += b;
            sum += b;
            if (i % 30 == 0) {


//                a -= 3300;
                //19*400
                sum += a / 20000 * 400;
                a += a / 20000 * 400;
//                Logutils.d("SwitchView", "i=" + i + ",month=" + i / 30 + ",a=" + a + ",b=" + b + ",sum=" + sum);

            }

//            Logutils.d("SwitchView", "i=" + i + ",ra=" + ra + ",a=" + a + ",b=" + b + ",sum=" + sum);

        }
        int b = 153 - 25 + 33 + 3 + 18 + 46 + 21 - 68;
        int c = -100 - 44 - 41 + 85;
        int d = 84 - 75 + 70 - 91 - 22 + 114 + 73;
        Logutils.d("SwitchView", "b=" + d);
    }

    private void calculate4() {

    }

}
