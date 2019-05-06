package com.example.luna.wallpaper;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends BaseActivity {

    private static final String TAG = "GuestResumeSessionReceiver";
    private ImageView iv;
    private int mIndex;
    private List<String> images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_show_image, null);
        setContentView(R.layout.activity_show_image);
        ViewGroup view = findViewById(R.id.show_parent);
        images = getImagePathFromSD();
//        for (String path : images) {
//            Logutils.d(TAG, "path = " + path);
//            ImageView iv = new ImageView(this);
//            Picasso.with(this).load(new File(path)).into(iv);
////            Bitmap bitmap = BitmapFactory.decodeFile(path);
////            iv.setImageBitmap(bitmap);
//            view.addView(iv);
//        }
        String path = getPath();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
//        options.inJustDecodeBounds = true;
//
//        options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = false;
//
//
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        imageView.setImageBitmap(bitmap);
        iv = new ImageView(this);
        iv.setImageBitmap(bitmap);
        view.addView(iv);
        final int value = Settings.System.getInt(mContext.getContentResolver(), "notifiy_wall_paper", 0);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageBitmap(getBitmap(getPath()));
                final int value = Settings.System.getInt(mContext.getContentResolver(), Settings.System.NOTIFY_WALL_PAPER, 0);
                Settings.System.putInt(getContentResolver(), Settings.System.NOTIFY_WALL_PAPER, value == 1 ? 0 : 1);
            }
        });

    }

    private void startDownloadService() {
        String currentDay = translateDay(System.currentTimeMillis());
        if (!currentDay.equals(mLastDay)) {
            startDownloadServiceReal();
            mLastDay = currentDay;
        }
    }

    private String mLastDay;

    public String translateDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        return d;
    }

    private void startDownloadServiceReal() {
        String packageName = "com.ruyi.feedback";
        String serviceClassName = packageName + ".service.DownloadService";
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName(packageName, serviceClassName));
        mContext.startService(serviceIntent);
    }

    private String getPath() {
        mIndex++;
        return images.get(mIndex % images.size());
    }

    private int mCount;

    private Bitmap getBitmap(String path) {
        mCount++;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int height = options.outHeight;
        int width = options.outWidth;
        if (height != 1440 || width != 720) {
            if (mCount > images.size()) {
                return bitmap;
            }
            return getBitmap(getPath());
        }
        mCount = 0;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                + "WallPaper";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }
}
