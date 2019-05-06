package com.example.luna.wallpaper;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BitmapLruCache extends LruCache<String, Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        // 重写获取某个节点的内存大小，不写默认返回1
        return value.getByteCount() / 1024;
    }

    // 某节点被移除后调用该函数
    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

}
