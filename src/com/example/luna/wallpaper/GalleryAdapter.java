package com.example.luna.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.utils.Logutils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private static final String TAG = "GuestResumeSessionReceiver";
    private Context mContext;
    private List<PicBean> mData;
    private LruCache<String, Bitmap> mMemoryCache;
    private int mLastPosition;

    public GalleryAdapter(Context context, List<PicBean> data) {
        mContext = context;
        mData = data;
        long maxMemory = Runtime.getRuntime().maxMemory();
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public List<PicBean> getData() {
        return mData;
    }

    private void setPosition(final int position) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                loadCache(position);
            }
        }.start();
    }

    private void loadCache(int position) {
        if (position > mLastPosition) {
            if (position > mData.size() - 5) {
                return;
            }
            String p1 = mData.get(position + 3).getPath();
            String p2 = mData.get(position + 4).getPath();
            getBitmap(p1);
            getBitmap(p2);
        } else {
            if (position < 2) {
                return;
            }
            String p1 = mData.get(position - 1).getPath();
            String p2 = mData.get(position - 2).getPath();
            getBitmap(p1);
            getBitmap(p2);
        }
        mLastPosition = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setPosition(position);
        PicBean bean = mData.get(position);
        holder.iv.setImageBitmap(getBitmap(bean.getPath()));
        if (bean.isChecked()) {
            holder.check.setImageResource(R.drawable.dialog_restart);
        } else {
            holder.check.setImageResource(R.drawable.dialog_shut_down);
        }
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logutils.d(TAG, "path = " + mData.get(position));
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isChecked()) {
                    bean.setChecked(false);
                    holder.check.setImageResource(R.drawable.dialog_shut_down);
                } else {
                    bean.setChecked(true);
                    holder.check.setImageResource(R.drawable.dialog_restart);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private Bitmap getBitmap(String path) {
        Bitmap bitmap = mMemoryCache.get(path);
        if (bitmap != null) {
            return bitmap;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = getScale(options.outWidth, options.outHeight);
        bitmap = BitmapFactory.decodeFile(path, options);
        mMemoryCache.put(path, bitmap);
        return bitmap;
    }

    private int getScale(int w, int h) {
        int base = 240;
        int wSize = w / base;
        int hSize = h / base;

        return Math.min(wSize, hSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.gallery_item_iv);
            check = itemView.findViewById(R.id.gallery_item_check);
        }
    }
}
