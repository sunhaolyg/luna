package com.example.luna.disklrucache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import com.example.luna.utils.Logutils;

import java.io.*;

public class DiskLruCacheUtils {

    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    public DiskLruCacheUtils(Context context) {
        File cacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
    }

    public Bitmap getBitmap(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream in = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putBitmap(String key, String pic_path) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (readFromSD(pic_path, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //定义读取文件的方法:
    public boolean readFromSD(String filename, OutputStream outputStream) {
        try {
            StringBuilder sb = new StringBuilder("");
            //打开文件输入流
            FileInputStream input = null;
            input = new FileInputStream(filename);

            BufferedInputStream in = new BufferedInputStream(input, 8 * 1024);
            BufferedOutputStream out = new BufferedOutputStream(outputStream, 8 * 1024);
            int len = 0;
            //读取文件内容:
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            //关闭输入流
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
        return true;
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {

        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                try {
                    File cacheDir = params[0];
                    if (!cacheDir.exists()) {
                        cacheDir.mkdirs();
                    }
                    mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads
            }
            return null;
        }
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }


}
