package com.example.luna.bonus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.luna.R;


import java.util.List;

/**
 * Created by sunhao on 17-11-1.
 */

public class BonusAdapter extends BaseAdapter {

    private List<String> mBeans;
    private Context mContext;

    public BonusAdapter(List<String> beans, Context context) {
        mBeans = beans;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.note_item, null);
        }
        ViewHolder holder = new ViewHolder(convertView);
        String bean = mBeans.get(position);
        holder.date.setText(bean);
        return convertView;
    }

    private class ViewHolder {

        TextView date;
        TextView scale;

        public ViewHolder(View view) {
            date = (TextView) view.findViewById(R.id.date);
            scale = (TextView) view.findViewById(R.id.scale);
            view.setTag(this);
        }

    }

}
