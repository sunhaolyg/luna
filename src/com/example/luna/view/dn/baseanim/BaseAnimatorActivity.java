package com.example.luna.view.dn.baseanim;

import android.animation.*;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class BaseAnimatorActivity extends BaseActivity {

    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_anim);
        iv = findViewById(R.id.img_iv);
    }

    public void startAnimator(View view) {
        switch (view.getId()) {
            case R.id.btn_object:
                startObjectAnimatorAnim(iv);
                break;
            case R.id.btn_animatorset:
                startAnimatorSet(iv);
                break;
            case R.id.btn_value:
                startValueAnimatorAnim(view);
                break;
            case R.id.btn_interpolator:
                startInterpolatorApply(iv);
                break;
            case R.id.btn_valueholder:
                startPropertyValueHolderAnim(iv);
                break;
            case R.id.btn_evaluator:
                startEvaluator(iv);
                break;
        }


    }

    private void startObjectAnimatorAnim(ImageView iv) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(iv, "alpha", 1.0f, 0f);
        oa.setDuration(1000);
        oa.setInterpolator(new LinearInterpolator());
        oa.start();
    }

    private void startAnimatorSet(ImageView iv) {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(iv, "alpla", 0.3f, 1.0f);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 2.0f);
        ObjectAnimator oa3 = ObjectAnimator.ofFloat(iv, "translationX", 0f, 100f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(oa1, oa2, oa3);
        set.start();
    }

    private void startValueAnimatorAnim(View view) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "hh", 0f, 100f, 50f);
        oa.setDuration(300);
        oa.start();
        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                view.setScaleX(0.5f + v / 200);
                view.setScaleY(0.5f + v / 200);
            }
        });
    }

    private void startInterpolatorApply(ImageView iv) {
        ValueAnimator va = ValueAnimator.ofFloat(0f, 1.0f);
        va.setDuration(2000);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                iv.setX(value * 1080);
                iv.setY(value * 2160);
            }
        });
    }

    private void startPropertyValueHolderAnim(ImageView iv) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.5f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);

        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(iv, holder1, holder2, holder3);
        oa.setDuration(1000);
        oa.start();
    }

    private void startEvaluator(ImageView iv) {
        ValueAnimator va = new ValueAnimator();
        va.setDuration(2000);
        va.setObjectValues(new PointF(0, 0));
        final PointF pointF = new PointF();
        va.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                pointF.x = 100f * (fraction * 5);
                // y=vt=1/2*g*t*t(重力计算)
                pointF.y = 0.5f * 98f * (fraction * 5) * (fraction * 5);
                return pointF;
            }
        });
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
            }
        });
        va.start();
    }
}
