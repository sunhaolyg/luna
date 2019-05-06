package com.example.luna.wallpaper;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.HttpCallback;
import com.example.luna.utils.HttpUtils;
import com.example.luna.utils.Logutils;
import com.example.luna.view.recyclerview.DividerGridViewItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ThreadShowActivity extends BaseActivity {

    private static final String TAG = "GuestResumeSessionReceiver";
    public static final int GALLERY_COLUM = 2;
    private RecyclerView mRecyclerView;
    private ThreadShowAdapter mAdapter;
    private Button upload_pic;
    private EditText upload_times;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery);
        mRecyclerView = findViewById(R.id.gallery_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GALLERY_COLUM));
        mRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(this));
        mAdapter = new ThreadShowAdapter(this, getPicBeans(getGallery(null)));
        mRecyclerView.setAdapter(mAdapter);
        upload_pic = findViewById(R.id.upload_pic);
        upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoad(mAdapter.getData());
            }
        });
        upload_times = findViewById(R.id.upload_times);
    }

    private void upLoad(List<PicBean> data) {
        String times = upload_times.getText().toString();
        if (TextUtils.isEmpty(times)) {
            Toast.makeText(this, getString(R.string.input_times), 0).show();
            return;
        }
        List<File> paths = new ArrayList<>();
        JSONArray ja = new JSONArray();
        for (PicBean bean : data) {
            if (bean.isChecked()) {
                JSONObject j = new JSONObject();
                File file = new File(bean.getPath());
                paths.add(file);
                j.put("name", file.getName());
                j.put("times", times);
                ja.add(j);
            }
        }
        File[] files = new File[paths.size()];
        JSONObject json = new JSONObject();
        json.put("model", "R10");
        json.put("paper_list", ja);
        Logutils.d(TAG, "json = " + json);
        HttpUtils.uploadFile(paths.toArray(files), json.toJSONString(), new HttpCallback() {
            @Override
            public void callback(boolean success, Object o) {
                Logutils.d(TAG, "callback = " + success);
            }
        });
    }

    private List<PicBean> getPicBeans(List<String> paths) {
        List<PicBean> beans = new ArrayList<>();
        for (String path : paths) {
            if (isCorrect(path)) {
                PicBean bean = new PicBean(path);
                beans.add(bean);
            }
        }
        return beans;
    }

    private boolean isCorrect(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int h = options.outHeight;
        int w = options.outWidth;
        Logutils.d(TAG, "h = " + h + ",w = " + w);
//        return h == 1440 && w == 720;
        return true;
    }

    private List<String> getGallery(File fileAll) {
        List<String> paths = new ArrayList<>();
        if (fileAll == null) {
            String filePath = Environment.getExternalStorageDirectory().toString() + File.separator;
            fileAll = new File(filePath);
        }
        if (!fileAll.isDirectory()) {
            if (isImageFile(fileAll.getPath())) {
                paths.add(fileAll.getPath());
            }
            return paths;
        }
        File[] files = fileAll.listFiles();
        for (File file : files) {
            if (file != null) {
                paths.addAll(getGallery(file));
            }
        }
        return paths;
    }

    private boolean isImageFile(String fName) {
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        return FileEnd.equals("jpg") || FileEnd.equals("png");
    }

}
