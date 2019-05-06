package com.example.luna.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.luna.R;
import com.example.luna.activity.*;
import com.example.luna.activity.curve.CurveActivity;
import com.example.luna.activity.drawable.DrawActivity;
import com.example.luna.activity.launchericon.LauncherIconActivity;
import com.example.luna.activity.paint.PaintActivity;
import com.example.luna.activity.shader.BitmapShaderActivity;
import com.example.luna.activity.switchv.SwitchActivity;
import com.example.luna.activity.xfermode.XFerMode;
import com.example.luna.base.BaseActivity;
import com.example.luna.bezier.Bezier2Activity;
import com.example.luna.bezier.Bezier3Activity;
import com.example.luna.scroller.ScrollerActivity;
import com.example.luna.view.dn.DNActivity;
import com.example.luna.view.dn.path.BezierActivity;
import com.example.luna.view.dn.pathmeasure.PathMeasureActivity;
import com.example.luna.view.recyclerview.RecyclerViewActivity;
import com.example.luna.view.touch.FrameLayoutActivity;

/**
 * Created by sunhao on 17-10-10.
 */

public class ViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    public void view(View view) {
        switch (view.getId()) {
            case R.id.dn:
                startActivity(new Intent(this, DNActivity.class));
                break;
            case R.id.custom:
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.flow:
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case R.id.waterfall:
                startActivity(new Intent(this, WaterFallActivity.class));
                break;
            case R.id.listview:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
            case R.id.xfermode:
                startActivity(new Intent(this, XFerMode.class));
                break;
            case R.id.bitmapshader:
                startActivity(new Intent(this, BitmapShaderActivity.class));
                break;
            case R.id.draw:
                startActivity(new Intent(this, DrawActivity.class));
                break;
            case R.id.path:
                startActivity(new Intent(this, PaintActivity.class));
                break;
            case R.id.switchv:
                startActivity(new Intent(this, SwitchActivity.class));
                break;
            case R.id.scroller:
                startActivity(new Intent(this, ScrollerActivity.class));
                break;
            case R.id.curve:
                startActivity(new Intent(this, CurveActivity.class));
                break;
            case R.id.bezier2:
                startActivity(new Intent(this, Bezier2Activity.class));
                break;
            case R.id.bezier3:
                startActivity(new Intent(this, Bezier3Activity.class));
                break;
            case R.id.wave:
                startActivity(new Intent(this, WaveActivity.class));
                break;
            case R.id.rotation:
                startActivity(new Intent(this, RotationActivity.class));
                break;
            case R.id.numberpick:
                startActivity(new Intent(this, NumberPickerActivity.class));
                break;
            case R.id.watch:
                startActivity(new Intent(this, StopWatchActivity.class));
                break;
            case R.id.spinner:
                startActivity(new Intent(this, SpinnerActivity.class));
                break;
            case R.id.arrow:
                startActivity(new Intent(this, ArrowActivity.class));
                break;
            case R.id.launcher:
                startActivity(new Intent(this, LauncherIconActivity.class));
                break;
            case R.id.recyclerview:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.frame_layout:
                startActivity(new Intent(this, FrameLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
