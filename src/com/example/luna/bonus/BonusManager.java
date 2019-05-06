package com.example.luna.bonus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.luna.utils.Logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhao on 17-11-1.
 */

public class BonusManager {

    private static final String NOTE_NAME = "bonus";
    private static final int NOTE_VERSION = 4;
    private BonusSqliteHelper mHelper;
    private boolean isLast;
    private List<String> mBeans = new ArrayList<>();
    private int lastId;

    public BonusManager(Context context) {
        mHelper = new BonusSqliteHelper(context, NOTE_NAME, NOTE_VERSION);
    }

    public void insert(String come) {
        isLast = false;
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.execSQL("insert into bonus (come) values (?) ",
                new Object[]{come});
        sd.close();
    }

    public List<String> cursor() {
        if (isLast) {
            Logutils.d("SwitchView", "bonus is not update ,do not need cursor again");
            return mBeans;
        }
        mBeans.clear();
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from bonus", null);
        int columnCount=cursor.getColumnCount();
        while (cursor.moveToNext()) {
            lastId=cursor.getInt(0);
            Logutils.d("SwitchView","id="+lastId);
            String come=cursor.getString(1);
            mBeans.add(come);
        }
        cursor.close();
        sd.close();
        isLast = true;
        return mBeans;
    }

    public void delete() {
        isLast = false;
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.execSQL("drop table bonus");
//        sd.delete("bonus", "come = ?", new String[]{"6"});
        sd.close();
    }

    public void deleteLast(){
//        List<String> beans=cursor();
//        String last=beans.get(beans.size()-1);
//        String last=beans.size()+"";
        Logutils.d("SwitchView","id = "+lastId);
        SQLiteDatabase sd=mHelper.getWritableDatabase();
        sd.execSQL("delete from bonus where id = ?",new Object[]{lastId+""});
        sd.close();
        isLast=false;
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
