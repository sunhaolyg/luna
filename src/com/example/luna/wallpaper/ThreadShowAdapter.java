package com.example.luna.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.luna.R;
import com.example.luna.utils.Logutils;

import java.util.List;

public class ThreadShowAdapter extends RecyclerView.Adapter<ThreadShowAdapter.ViewHolder> {
    private static final String TAG = "GuestResumeSessionReceiver";
    private final int mScreenWidth;
    private Context mContext;
    private List<PicBean> mData;
    private int mLastPosition;
    private LruCache<String, Bitmap> mMemoryCache;

    public ThreadShowAdapter(Context context, List<PicBean> data) {
        mContext = context;
        mData = data;
        long maxMemory = Runtime.getRuntime().maxMemory();
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        mScreenWidth = dm2.widthPixels;
    }

    public List<PicBean> getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PicBean bean = mData.get(position);
        getBitmap(holder.iv, bean.getPath());
        if (bean.isChecked()) {
            holder.check.setImageResource(R.drawable.dialog_restart);
        } else {
            holder.check.setImageResource(R.drawable.dialog_shut_down);
        }
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logutils.d(TAG, "path = " + mData.get(position));
                Intent intent = new Intent(mContext, GalleryDetailsActivity.class);
                intent.putExtra("path", mData.get(position).getPath());
                mContext.startActivity(intent);
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

    private void getBitmap(HeightImageView iv, final String path) {
        Bitmap b = mMemoryCache.get(path);
        if (b != null) {
            iv.setBitmap(b);
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapReal(path);
                iv.setBitmap(bitmap);
                mMemoryCache.put(path, bitmap);
            }
        }.start();
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
        int base = mScreenWidth / ThreadShowActivity.GALLERY_COLUM;
        int wSize = w / base;
        int hSize = h / base;

        return Math.min(wSize, hSize);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        HeightImageView iv;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.gallery_item_iv);
            check = itemView.findViewById(R.id.gallery_item_check);
        }

    }
}
