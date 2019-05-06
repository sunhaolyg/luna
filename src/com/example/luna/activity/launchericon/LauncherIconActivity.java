package com.example.luna.activity.launchericon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.DateUtil;

import android.net.wifi.WifiManager;
import com.example.luna.utils.Logutils;
import android.os.UserHandle;
import android.provider.Settings;

public class LauncherIconActivity extends BaseActivity {

    private ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
//        mIcon = (ImageView) findViewById(R.id.iv_icon);
//
//
//        Drawable drawable = getFullResIcon(getResources(),
//                R.drawable.background);
//        Bitmap b = createIconBitmap(drawable, this);
//        mIcon.setImageBitmap(drawTextAtBitmap(b, "abcd"));
////        mIcon.setImageDrawable(drawable);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
////                Settings.ACTION_APPLICATION_DETAIL_SETTING
////                intent.setClassName("com.mediatek.mtklogger","com.mediatek.mtklogger.MainActivity");
////                startActivity(intent);
//                Intent intent = new Intent(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//                intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT);
//                intent.putExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
//                intent.putExtra(IWifiFwkExt.EXTRA_SHOW_RESELECT_DIALOG_FLAG, true);
//                sendBroadcastAsUser(intent, UserHandle.ALL);
//            }
//        });
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
//        registerReceiver(mWifiReceiver,filter);

    }


    private Drawable getFullResIcon(Resources resources, int iconId) {
        Drawable d;
        try {
            d = resources.getDrawableForDensity(iconId, 300);
        } catch (Resources.NotFoundException e) {
            d = null;
        }

        return d;
    }

    public static Bitmap createIconBitmap(Drawable icon, Context context) {
        int width = 800;
        int height = 800;

        if (icon instanceof PaintDrawable) {
            PaintDrawable painter = (PaintDrawable) icon;
            painter.setIntrinsicWidth(width);
            painter.setIntrinsicHeight(height);
        } else if (icon instanceof BitmapDrawable) {
            // Ensure the bitmap has a density.
            BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
            }
        }
        int sourceWidth = icon.getIntrinsicWidth();
        int sourceHeight = icon.getIntrinsicHeight();
        if (sourceWidth > 0 && sourceHeight > 0) {
            // Scale the icon proportionally to the icon dimensions
            final float ratio = (float) sourceWidth / sourceHeight;
            if (sourceWidth > sourceHeight) {
                height = (int) (width / ratio);
            } else if (sourceHeight > sourceWidth) {
                width = (int) (height * ratio);
            }
        }
        int textureWidth = 800;
        int textureHeight = 800;

        final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);

        final int left = (textureWidth - width) / 2;
        final int top = (textureHeight - height) / 2;
        Rect rect = new Rect();
        rect.set(icon.getBounds());
        icon.setBounds(left, top, left + width, top + height);
        icon.draw(canvas);
        icon.setBounds(rect);
        canvas.setBitmap(null);

        return bitmap;
    }

    /**
     * 图片上画字 *
     */
    private Bitmap drawTextAtBitmap(Bitmap bitmap, String text) {
        int x = bitmap.getWidth();
        int y = bitmap.getHeight(); // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newbit);
        Paint paint = new Paint();
        // 在原始位置0，0插入原图
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(Color.RED);
        paint.setTextSize(20); // 在原图指定位置写上字
        canvas.drawText(DateUtil.getDay(), 53, 30, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        //存储
        canvas.restore();
        return newbit;
    }

    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean update= intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED,false);
            Logutils.d("GuestResumeSessionReceiver","update = "+update);
        }
    };
}
