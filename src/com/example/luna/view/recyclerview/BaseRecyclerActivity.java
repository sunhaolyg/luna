package com.example.luna.view.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerActivity extends BaseActivity {

    protected static final String TAG = "RecyclerViewActivityTAG";
    protected RecyclerView mRecyclerView;
    protected LunaRecyclerViewAdapter mAdapter;
    protected List<String> mData = new ArrayList<>();
    protected int mIndex;
    protected SwipeRefreshLayout mRefreshLayout;

    protected ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initRefresh();
        initRecylerView();
    }

    private void initRefresh() {
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeColors(Color.parseColor("#007dff"));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();
            }
        });
    }

    protected void startRefresh() {
        mHandler.sendEmptyMessageDelayed(100, 2000);
    }

    private void startLoadMore(LunaRecyclerViewAdapter.BaseViewHolder holder) {
        Message msg = new Message();
        msg.what = 200;
        msg.obj = holder;
        mHandler.sendMessageDelayed(msg, 1000);
    }

    private void initRecylerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlag, swipeFlag);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                viewHolder.getAdapterPosition();
//                target.getAdapterPosition();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                viewHolder.getAdapterPosition();
            }
        });
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        //设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        mRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(this));

        initData();
        mAdapter = new LunaRecyclerViewAdapter(mData, mContext, new LunaRecyclerViewAdapter.OnSwipeFoot() {
            @Override
            public void onLoading(LunaRecyclerViewAdapter.BaseViewHolder holder) {
                startLoadMore(holder);
            }
        });
        //设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);


    }

    protected void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add("data = " + i);
            mIndex = i;
        }
    }

//    private void listener() {
//        mAdapter.setOnLunaItemClickListener(new OnLunaItemClickListener() {
//            @Override
//            public void onClick(LunaRecyclerViewAdapter.ViewHolder holder, int position) {
////                Logutils.d(TAG, "click = " + mData.get(position));
//                mIndex++;
//                mAdapter.addItem("data = " + mIndex, mIndex);
//            }
//        });
//        mAdapter.setOnLunaItemLongClickListener(new OnLunaItemLongClickListener() {
//            @Override
//            public boolean onLongClick(LunaRecyclerViewAdapter.ViewHolder holder, int position) {
////                Logutils.d(TAG, "long Click = " + mData.get(position));
//                mIndex--;
//                mAdapter.removeItem(position);
//                return true;
//            }
//        });
//    }

    protected void setRefreshEnable(boolean enable) {
        mRefreshLayout.setEnabled(enable);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mRefreshLayout.setRefreshing(false);
            } else {
                for (int i = 0; i < 20; i++) {
                    if (mIndex > 100) {
                        LunaRecyclerViewAdapter.BaseViewHolder holder = (LunaRecyclerViewAdapter.BaseViewHolder) msg.obj;
                        holder.textView.setText("already load all");
                        return;
                    }
                    mIndex++;
                    mData.add("data = " + mIndex);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

}

