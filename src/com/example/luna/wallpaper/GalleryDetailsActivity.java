package com.example.luna.wallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class GalleryDetailsActivity extends BaseActivity {

    private ImageView gallery_details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_details);
        gallery_details = findViewById(R.id.gallery_details);
        String path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path)) {
            finish();
            return;
        }
        Bitmap bitmap = getBitmapReal(path);
        if (bitmap == null) {
            finish();
            return;
        }
        gallery_details.setImageBitmap(bitmap);
        gallery_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Bitmap getBitmapReal(String path) {
        Bitmap bitmap = null;
        if (bitmap != null) {
            return bitmap;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = getScale(options.outWidth, options.outHeight);
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private int getScale(int w, int h) {
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        int base = dm2.widthPixels;
        int wSize = w / base;
        int hSize = h / base;

        return Math.min(wSize, hSize);
    }

}
