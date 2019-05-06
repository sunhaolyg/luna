package com.example.luna.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.DateUtil;
import com.example.luna.utils.Logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-11-1.
 */

public class NoteActivity extends BaseActivity {

    private EditText total, income;
    private Button btn, query;
    private ListView lv;
    private NoteManager manager;
    private List<NoteBean> beans = new ArrayList<>();
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        total = (EditText) findViewById(R.id.total);
        income = (EditText) findViewById(R.id.income);
        btn = (Button) findViewById(R.id.ensure);
        lv = (ListView) findViewById(R.id.data);
        query = (Button) findViewById(R.id.query);
        manager = new NoteManager(this);
        mAdapter = new NoteAdapter(beans, this);
        lv.setAdapter(mAdapter);
        listener();
    }

    private void listener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = total.getEditableText().toString();
                String i = income.getEditableText().toString();
                if (t == null || t.equals("") || i == null || i.equals("")) {
                    return;
                }
                String time = System.currentTimeMillis() + "";
                if (!canInsert(time)) {
                    Logutils.d("SwitchView", "can not insertRoom,this day has data already");
                    return;
                }
                float a = Float.parseFloat(t);
                float b = Float.parseFloat(i);
                float scale = b / a;
                NoteBean bean = new NoteBean(time, t, i, scale + "");
                Logutils.d("SwitchView", bean.toString());
                manager.insert(bean);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beans.clear();
                beans.addAll(select());
                float sum = 0f;
                for (NoteBean bean : beans) {
                    sum += Float.parseFloat(bean.getScale());
                }
                float average = sum / beans.size();
                beans.add(new NoteBean("0", "1", "1", average + ""));
                Logutils.d("SwitchView", "sum=" + sum + ",average=" + average);
                mAdapter.notifyDataSetChanged();
                for (NoteBean bean : beans) {
                    Logutils.d("SwitchView", bean.toString());
                    Logutils.d("SwitchView", DateUtil.tanslateDay(bean.getDate()));
                }
            }
        });
    }

    private List<NoteBean> select() {
        //NoteBean{date='1509506864480', total='39.89', income='289', scale='7.24492'}
        List<NoteBean> beans = manager.cursor();
        return beans;
    }

    private boolean canInsert(String time) {
        List<NoteBean> beans = select();
        for (NoteBean bean : beans) {
            if (DateUtil.tanslateDay(time).equals(DateUtil.tanslateDay(bean.getDate()))) {
                return false;
            }
        }
        return true;
    }

}
