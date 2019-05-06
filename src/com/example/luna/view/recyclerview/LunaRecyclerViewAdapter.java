package com.example.luna.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.luna.R;
import com.example.luna.utils.Logutils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunaRecyclerViewAdapter extends RecyclerView.Adapter<LunaRecyclerViewAdapter.BaseViewHolder> {

    private static final String URL_HTTP = "http://192.168.3.31:8080/";
    private static final String URL_IV = URL_HTTP + "thinkgem/user/downloadPic/background.png";
    private static final int ITEM_FOOTER = 1;

    private List<String> mList;
    private OnLunaItemClickListener mOnLunaItemClickListener;
    private OnLunaItemLongClickListener mOnLunaItemLongClickListener;
    private Context mContext;
    private OnSwipeFoot mOnSwipeFoot;
    private boolean mLoadMoreEnable;

    public LunaRecyclerViewAdapter(List<String> lists, Context context, OnSwipeFoot recyclerView) {
        mList = lists;
        mContext = context;
        mOnSwipeFoot = recyclerView;
        mLoadMoreEnable = true;
    }

//    public void addItem(String s, int position) {
//        mList.add(position, s);
//        notifyItemInserted(position);
//    }
//
//    public void removeItem(int position) {
//        if (mList.size() - 1 < position) {
//            return;
//        }
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }

    public void setLoadMoreEnable(boolean enable) {
        mLoadMoreEnable = enable;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && mLoadMoreEnable) {
            return ITEM_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        BaseViewHolder holder = null;
        if (viewType == ITEM_FOOTER && mLoadMoreEnable) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, null);
            holder = new FootViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_item, null);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            holder.textView.setText("foot");
            if (mOnSwipeFoot != null) {
                mOnSwipeFoot.onLoading(holder);
            }
        } else {
            holder.textView.setText(mList.get(position));
//        holder.imageView.setImageResource(R.drawable.background);
            Picasso.with(mContext).load(URL_IV).into(holder.imageView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnLunaItemClickListener.onClick(holder, position);
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return mOnLunaItemLongClickListener.onLongClick(holder, position);
//            }
//        });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnLunaItemClickListener(OnLunaItemClickListener listener) {
        mOnLunaItemClickListener = listener;
    }

    public void setOnLunaItemLongClickListener(OnLunaItemLongClickListener listener) {
        mOnLunaItemLongClickListener = listener;
    }

    class ViewHolder extends BaseViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.i_tv);
            imageView = itemView.findViewById(R.id.i_iv);
        }
    }

    class FootViewHolder extends BaseViewHolder{

        public FootViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.date);
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnSwipeFoot {
        void onLoading(BaseViewHolder holder);
    }

}
