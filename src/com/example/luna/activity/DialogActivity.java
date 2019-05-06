package com.example.luna.activity;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.example.luna.utils.RenderScriptBlur;
import com.example.luna.utils.ScreenShotUtil;

import java.util.Random;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "KeyguardSecurityView";
    private Context mConetext;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLp;
    private Button show,show_dialog;
    private ImageView signal0, signal1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mConetext = this;
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        show = (Button) findViewById(R.id.show);
        show_dialog=findViewById(R.id.show_dialog);
        show.setOnClickListener(this);
        show_dialog.setOnClickListener(this);
        signal0 = (ImageView) findViewById(R.id.signal0);
        signal1 = (ImageView) findViewById(R.id.signal1);
        int [] i = {1,2};

    }

    private void setTint(float function) {
        Random random = new Random();
        float f = random.nextInt(10);
        float cc = f / 10;
        int tint = (int) ArgbEvaluator.getInstance().evaluate(cc, Color.WHITE, Color.BLACK);
        signal1.setImageTintList(ColorStateList.valueOf(tint));
        Logutils.d(TAG, "tint = " + tint + ",f = " + cc);
    }

    private void showDialog2() {
        AlertDialog dialog = new AlertDialog.Builder(mConetext).setTitle("title").setMessage("message").show();
    }

    private void createDialog() {
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        view.getChildCount();
        View v = view.getChildAt(1);
        Bitmap bitmap = ScreenShotUtil.takeScreenShot(this);
        if (bitmap != null) {

            Bitmap blur = RenderScriptBlur.blur(this, bitmap);
            ImageView iv = (ImageView) view.findViewById(R.id.dialog_iv);
            iv.setImageBitmap(blur);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(v);
            }
        });
        add(view);
    }

    private void createDialog2() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        Bitmap bitmap = ScreenShotUtil.takeScreenShot(this);
        if (bitmap != null) {

            Bitmap blur = RenderScriptBlur.blur(this, bitmap);
            ImageView iv = (ImageView) view.findViewById(R.id.dialog_iv);
            iv.setImageBitmap(blur);
        }
        dialog.setView(view);
        dialog.show();
    }

    private void add(View view) {

        // Now that the status bar window encompasses the sliding panel and its
        // translucent backdrop, the entire thing is made TRANSLUCENT and is
        // hardware-accelerated.
        mLp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT);
        mLp.gravity = Gravity.CENTER;
        mLp.setTitle("test");
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);

//        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mWindowManager.addView(view, mLp);
    }

    private void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(uiOptions);

        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        window.setContentView(view);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    float tt = 0f;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                showDialog();
//                createDialog();
//                setTint(tt);
//                tt++;
                break;
            case R.id.show_dialog:
                showDeleteDialog();
                break;
        }
    }

    private void showDeleteDialog() {
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_delete_clock, null);
        deleteDialog.setView(dialogView);
        deleteDialog.show();
        final Button cancel = (Button) deleteDialog.findViewById(R.id.cancel);
        final Button ensure = (Button) deleteDialog.findViewById(R.id.ensure);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };
        cancel.setOnClickListener(clickListener);
        ensure.setOnClickListener(clickListener);
    }
}
