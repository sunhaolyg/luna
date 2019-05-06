package com.example.luna.view.dn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.view.dn.attr.AttrActivity;
import com.example.luna.view.dn.baseanim.BaseAnimatorActivity;
import com.example.luna.view.dn.baseanim.SplashAnimatorActivity;
import com.example.luna.view.dn.path.BezierActivity;
import com.example.luna.view.dn.pathmeasure.PathMeasureActivity;
import com.example.luna.view.dn.pie.PieActivity;

public class DNActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn);
    }

    public void dn(View view) {
        switch (view.getId()) {
            case R.id.attr:
                startActivity(new Intent(this, AttrActivity.class));
                break;
            case R.id.base_animator:
                startActivity(new Intent(this, BaseAnimatorActivity.class));
                break;
            case R.id.splash_animator:
                startActivity(new Intent(this, SplashAnimatorActivity.class));
                break;
            case R.id.pieview:
                startActivity(new Intent(this, PieActivity.class));
                break;
            case R.id.path_measure:
                startActivity(new Intent(this, PathMeasureActivity.class));
                break;
            case R.id.bezier_wave:
                startActivity(new Intent(this, BezierActivity.class));
                break;
        }
    }
}
