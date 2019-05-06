package com.example.luna.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.luna.poker.server.Room;

public abstract class BaseSqManager {

    protected BaseSqliteHelper mHelper;
    private String table_name;

    public BaseSqManager() {
    }

    public BaseSqManager(Context context, String database, int version, String createString) {
        mHelper = new BaseSqliteHelper(context, database, version, createString);
    }

    public void insert(String insertString, Object[] objects) {
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        /*sd.execSQL("insertRoom into " + table_name + "(room_master, create_time, complete) values (?,?,?) ",
                new Object[]{bean.getRoom_master(),
                        bean.getCreate_time(),
                        bean.getComplete()});*/
        sd.execSQL(insertString, objects);
        sd.close();
    }

    /*public void insertRoom() {
        String insertString = getInsertString();
        Object[] objects = getInsertObjects();
        if (insertString == null || objects == null) {
            return;
        }
        insertRoom(insertString, objects);
    }

    public String getInsertString() {
        return null;
    }


    public Object[] getInsertObjects() {
        return null;
    }
*/
}
