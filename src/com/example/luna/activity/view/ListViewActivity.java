package com.example.luna.activity.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luna.R;
import com.example.luna.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-10-10.
 */

public class ListViewActivity extends BaseActivity {

    private ListView lv;
    private List<String> lists = new ArrayList<>();
    private LVAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        lv = (ListView) findViewById(R.id.lv);
        init();
    }

    private void init() {
        for (int i = 0; i < 20; i++) {
            lists.add(i + "");
        }
        mAdapter = new LVAdapter();
        lv.setAdapter(mAdapter);
    }

    private class LVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ListViewActivity.this, R.layout.lv_item, null);
            }
            ViewHolder holder = new ViewHolder(convertView);
            holder.tv.setText(lists.get(position));
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tv;

        public ViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.i_tv);
            view.setTag(this);
        }
    }
}
