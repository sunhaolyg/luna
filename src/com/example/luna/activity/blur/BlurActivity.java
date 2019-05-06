package com.example.luna.activity.blur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

/**
 * Created by sunhao on 17-10-10.
 */

public class BlurActivity extends BaseActivity {

    private ImageView blur_iv;
    private boolean change = false;
    private Bitmap nativ = null, blur = null;
    private ImageView native_iv;
    private View alpha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        blur_iv = (ImageView) findViewById(R.id.blur_iv);
        native_iv = (ImageView) findViewById(R.id.native_iv);
        nativ = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        blur = RenderScriptBlur.blur(this, nativ, 26);
        native_iv.setImageBitmap(nativ);
        alpha = findViewById(R.id.alpha);
    }

    int ii;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (y < 700) {
                    float a = (y - 200) / 800;
                    if (a < 0) {
                        a=0;
                    }
                    alpha.setAlpha(a);
//                    ii = (int) ((y - 200) / 100) * (int) ((y - 200) / 100) * 2;
                    Logutils.d("SwitchView", "a=" + a + "y=" + y + ",ii=" + ii);
//                    if (ii <= 25) {
//                        blur = RenderScriptBlur.blur(this, nativ, ii);
//                        native_iv.setImageBitmap(blur);
//                    }else{
//                        blur = RenderScriptBlur.blur(this, RenderScriptBlur.blur(this, nativ, 25), ii-25);
//                        native_iv.setImageBitmap(blur);
//                    }
//                    if (600 / 100 > ii) {
//                        ii = 600 / 100;
//                        blur = RenderScriptBlur.blur(this, nativ, ii);
//                        native_iv.setImageBitmap(blur);
//                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }
}
