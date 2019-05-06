package com.example.luna.note;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.luna.utils.Logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-11-1.
 */

public class NoteManager {

    private static final String NOTE_NAME = "notes";
    private static final int NOTE_VERSION = 2;
    private NoteSqliteHelper mHelper;
    private boolean isLast;
    private List<NoteBean> mBeans = new ArrayList<>();

    public NoteManager(Context context) {
        mHelper = new NoteSqliteHelper(context, NOTE_NAME, NOTE_VERSION);
    }

    public void insert(NoteBean bean) {
        isLast = false;
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.execSQL("insert into notes (date, total, income, scale) values (?,?,?,?) ",
                new Object[]{bean.getDate(),
                        bean.getTotal(),
                        bean.getIncome(),
                        bean.getScale()});
        sd.close();
    }

    public List<NoteBean> cursor() {
        if (isLast) {
            Logutils.d("SwitchView", "db is not update ,do not need cursor again");
            return mBeans;
        }
        mBeans.clear();
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from notes", null);
        while (cursor.moveToNext()) {
            NoteBean bean = new NoteBean();
            bean.setDate(cursor.getString(1));
            bean.setTotal(cursor.getString(2));
            bean.setIncome(cursor.getString(3));
            bean.setScale(cursor.getString(4));
            mBeans.add(bean);
        }
        cursor.close();
        sd.close();
        isLast = true;
        return mBeans;
    }

    public void delete() {
        isLast = false;
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.delete("notes", "date = ?", new String[]{"1509506963465"});
        sd.close();
    }

    public void update1() {
        isLast = false;
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.execSQL("update notes set income = 689 where date = ? ", new Object[]{"1509505595420"});
        float i = 689.0f / 39.89f;
        sd.execSQL("update notes set scale = " + i + " where date = ? ", new Object[]{"1509505595420"});
        sd.close();
    }

}
