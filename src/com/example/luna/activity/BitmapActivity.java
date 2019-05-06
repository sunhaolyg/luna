package com.example.luna.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

public class BitmapActivity extends BaseActivity {

    private ImageView iv1, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        initView();
        setBitmap1();
    }

    private void setBitmap() {
        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inSampleSize = 3;
        o.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_19, o);
        iv1.setImageBitmap(b);
        Logutils.d("NotificationViewWrapper", "1 = " + b.getByteCount());
    }

    private void setBitmap2() {
        BitmapFactory.Options o = new BitmapFactory.Options();
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_19);
        Bitmap b = decodeSampledBitmapFromResource(getResources(), R.drawable.wallpaper_19, 1440/2, 2560/2);
        iv2.setImageBitmap(b);
        Logutils.d("NotificationViewWrapper", "2 = " + b.getByteCount());
    }

    private void setBitmap1() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_19, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Logutils.d("NotificationViewWrapper", "hei = " + imageHeight + ",wid = " + imageWidth + ",type = " + imageType);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource
            (Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private void initView() {
        iv1 = (ImageView) findViewById(R.id.b_iv1);
        iv2 = (ImageView) findViewById(R.id.b_iv2);
        setBitmap();
        setBitmap2();
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv1.setVisibility(View.GONE);
                iv2.setVisibility(View.VISIBLE);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv2.setVisibility(View.GONE);
                iv1.setVisibility(View.VISIBLE);
            }
        });
    }

}
