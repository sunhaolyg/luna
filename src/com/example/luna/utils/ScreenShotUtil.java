package com.example.luna.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.WindowManager;

/**
 * add by sunhao 20171115
 */
public class ScreenShotUtil {

    public static Bitmap takeScreenShot(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        float[] dims = {displayMetrics.widthPixels, displayMetrics.heightPixels};
        return SurfaceControl.screenshot((int) dims[0], (int) dims[1]);
    }

}
