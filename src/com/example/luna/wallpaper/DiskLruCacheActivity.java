package com.example.luna.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.disklrucache.DiskLruCache;
import com.example.luna.utils.HttpUtils;
import com.example.luna.utils.Logutils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiskLruCacheActivity extends BaseActivity {

    private static final String TAG = "GuestResumeSessionReceiver";
//    private String pic_path = "/storage/emulated/0/Pictures/Screenshot_2018-11-23-15-44-56-36.png";
    private String pic_path = "/storage/emulated/0/Pictures/Screenshot_2018-07-16-20-57-38-05.png";
    private String pic_url = HttpUtils.HTTP + "thinkgem/wallpaper/downloadPic/31.png";

    private DiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    private Button input, output;
    private ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lrucache);
        input = findViewById(R.id.disk_input);
        output = findViewById(R.id.disk_output);
        show = findViewById(R.id.disk_iv);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeFile(pic_path);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        putBitmap(DISK_CACHE_SUBDIR, bitmap);
                    }
                }.start();
            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmap(DISK_CACHE_SUBDIR);
                show.setImageBitmap(bitmap);
            }
        });
//        Bitmap bitmap = BitmapFactory.decodeFile(pic_path);
//        show.setImageBitmap(bitmap);
        // Initialize memory cache
        // Initialize disk cache on background thread
        File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
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

    private Bitmap getBitmap(String key) {
        Logutils.d(TAG, "getBitmap 1");
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream in = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            Logutils.d(TAG, "getBitmap 2");
            e.printStackTrace();
        }
        return null;
    }

    private void putBitmap(String key, Bitmap bitmap) {
        Logutils.d(TAG, "putBitmap 1");
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
//                if (downloadUrlToStream(pic_url, outputStream)) {
                if (readFromSD(pic_path, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();

        } catch (IOException e) {
            Logutils.d(TAG, "putBitmap 2");
            e.printStackTrace();
        }
    }

    private void readSaveFile(String filename) {
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(filename);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定义读取文件的方法:
    public boolean readFromSD(String filename, OutputStream outputStream) {
        try {
            StringBuilder sb = new StringBuilder("");
//        filename = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + filename;
            //打开文件输入流
            FileInputStream input = null;
            input = new FileInputStream(filename);

            BufferedInputStream in = new BufferedInputStream(input, 8 * 1024);
            BufferedOutputStream out = new BufferedOutputStream(outputStream, 8 * 1024);
//            byte[] temp = new byte[1024];

            int len = 0;
            //读取文件内容:
            while ((len = in.read()) != -1) {
//                sb.append(new String(temp, 0, len));
                out.write(len);
            }
            //关闭输入流
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {

        }
        return true;
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();

    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final String imageKey = String.valueOf(params[0]);

            // Check disk cache in background thread
            Bitmap bitmap = getBitmapFromDiskCache(imageKey);

            if (bitmap == null) { // Not found in disk cache
                // Process as normal
//                final Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), params[0], 100, 100));
            }

            // Add final bitmap to caches
            addBitmapToCache(imageKey, bitmap);

            return bitmap;
        }
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
//        if (getBitmapFromMemCache(key) == null) {
//            mMemoryCache.put(key, bitmap);
//        }

        // Also add to disk cache
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null && getBitmap(key) == null) {
//                mDiskLruCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                }
            }

            if (mDiskLruCache != null) {
//                return getDiskLru(key);
            }
        }

        return null;
    }

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
    public File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable() ? getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

}
