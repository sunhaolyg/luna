package com.example.luna.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class WallPaperActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper);
    }

    public void wallPaper(View view) {
        switch (view.getId()) {
            case R.id.save_image:
                startActivity(new Intent(this, SaveImageActivity.class));
                break;
            case R.id.show_image:
                startActivity(new Intent(this, ShowImageActivity.class));
                break;
            case R.id.gallery:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.disk_lrucache:
                startActivity(new Intent(this, DiskLruCacheActivity.class));
                break;
            case R.id.disk_lru_show:
                startActivity(new Intent(this, ThreadShowActivity.class));
                break;
        }
    }
}
