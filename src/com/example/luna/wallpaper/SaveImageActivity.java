package com.example.luna.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.HttpUtils;
import com.example.luna.utils.Logutils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.*;
import java.util.List;

public class SaveImageActivity extends BaseActivity {

    public static final String GET_IMG_LIST = HttpUtils.HTTP + "thinkgem/wallpaper/selectImg";

    private static final String BAIDU_URL = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png?where=super";

    private String imageDataPath = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera" + File.separator;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);
        mImageView = findViewById(R.id.save_show);
        Picasso.with(this).load(BAIDU_URL).into(mImageView);
//        download();
        getImgList();

    }

    private void getImgList() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Logutils.d("MainActivityTAG", "json = " + json);
                WallPaperList list = JSON.parseObject(json, WallPaperList.class);
                if (list.getSuccess() != 0 || list.getPaper_list() == null) {
                    return;
                }
                for (WallPaperBean bean : list.getPaper_list()) {
//                    download(HttpUtils.HTTP + bean.getImage_pic(), bean.getName());
                    okDownload(HttpUtils.HTTP + bean.getImage_pic(), bean.getName());
                    Logutils.d("MainActivityTAG", "bean = " + bean);

                }
            }
        };
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/eaglelog/");
            JSONObject json = new JSONObject();
            json.put("model", "R10");
            HttpUtils.post(GET_IMG_LIST, json.toJSONString(), callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void okDownload(String url, final String name) {
        HttpUtils.downloadPic(url + name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //将响应数据转化为输入流数据
                InputStream inputStream = response.body().byteStream();
                //将输入流数据转化为Bitmap位图数据
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveImageToGallery(mContext, bitmap, name);
            }
        });

    }

    private void download(String url, final String name) {
        //获得图片的地址
//        String url = BAIDU_URL;
        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                saveImageToGallery(SaveImageActivity.this, bitmap, name);
            }

            @Override
            public void onBitmapFailed() {
            }
        };
        //Picasso下载
        Picasso.with(this).load(url + name).into(target);

    }

    public static void saveImageToGallery(Context context, Bitmap bmp, String name) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "WallPaper");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        Logutils.d("GuestResumeSessionReceiver", "file = " + file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

}
