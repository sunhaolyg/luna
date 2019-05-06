package com.example.luna.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.base.BaseActivity;

/**
 * Created by sunhao on 17-9-22.
 */

public class ObjectAnimatorActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void rotateAnimRun(View view){

        ObjectAnimator.ofFloat(view, "rotationX", 0.0F, 360.0F)
                .setDuration(500)
                .start();

    }

}
