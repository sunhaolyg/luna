package com.example.luna.bonus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-11-6.
 */

public class BonusActivity extends BaseActivity {

    private Button btn;
    private ListView lv;
    private EditText et;
    private BonusAdapter mAdapter;
    private BonusManager mManager;
    private List<String> mBeans = new ArrayList<>();

    private Button btn_j;
    private EditText et_j;
    private Button btn_d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
        btn = (Button) findViewById(R.id.bonus_btn);
        lv = (ListView) findViewById(R.id.bonus_lv);
        et = (EditText) findViewById(R.id.bonus_et);
        btn_j = (Button) findViewById(R.id.bonus_btn_j);
        et_j = (EditText) findViewById(R.id.bonus_et_j);
        btn_d= (Button) findViewById(R.id.bonus_btn_d);
        listener();
        mManager = new BonusManager(this);
        mAdapter = new BonusAdapter(mBeans, this);
        lv.setAdapter(mAdapter);
    }

    private void listener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String come = et.getEditableText().toString();
                if (come == null || come.equals("")) {
                    Logutils.d("SwitchView", "et is null");
                    mBeans.clear();
                    List<String> temp = mManager.cursor();
                    int sum = 0;
                    for (String s : temp) {
                        int i = Integer.parseInt(s);
                        sum += i;
                    }
                    mBeans.add(sum + "");
                    mAdapter.notifyDataSetChanged();
                    return;
                }
                int bonus = Integer.parseInt(come);
                mManager.insert(come);
                notifyListView();
            }
        });

        btn_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String come = et_j.getEditableText().toString();
                if (come == null || come.equals("")) {
                    Logutils.d("SwitchView", "et is null");
                    notifyListView();
                    return;
                }
                int bonus = Integer.parseInt(come);
                mManager.insert("-" + bonus);
                notifyListView();
            }
        });
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.deleteLast();
                notifyListView();
            }
        });
    }

    private void notifyListView(){
        mBeans.clear();
        mBeans.addAll(mManager.cursor());
        mAdapter.notifyDataSetChanged();
    }
}
