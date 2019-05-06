package com.example.luna.bonus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sunhao on 17-11-1.
 */

public class BonusSqliteHelper extends SQLiteOpenHelper {

    /*public static final String CREATE_NEWS = "create table news ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text, "
            + "publishdate integer,"
            + "commentcount integer)";*/

    private static final String CREATE_NOTE = "create table bonus (" +
            "id integer primary key autoincrement," +
            "come txt);";


    public BonusSqliteHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion==4){
            db.execSQL(CREATE_NOTE);
        }
    }
}
