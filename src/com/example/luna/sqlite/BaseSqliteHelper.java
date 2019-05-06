package com.example.luna.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.luna.poker.sqlite.RoomSqManager;

public class BaseSqliteHelper extends SQLiteOpenHelper {

    public static final String POKER_DATABASE = "poker.db";
    public static final String CREATE_NOTE = "create table notes (" +
            "id integer primary key autoincrement," +
            "room_master txt," +
            "create_time txt," +
            "complete int);";

    public static final String CREATE_ROOM = "create table " + RoomSqManager.TABLE_ROOM_NAME +
            " (id integer primary key autoincrement," +
            "room_master int," +
            "create_time txt," +
            "spare txt," +
            "member_count int," +
            "complete int);";

    public static final String CREATE_USER = "create table " + RoomSqManager.TABLE_USER_NAME +
            " (id integer primary key autoincrement," +
            "name txt," +
            "create_time txt," +
            "spare txt," +
            "money int);";

    public static final String CREATE_MEMBER = "create table " + RoomSqManager.TABLE_MEMBER_NAME +
            " (id integer primary key autoincrement," +
            "name txt," +
            "room_id int," +
            "poker txt," +
            "spare txt," +
            "user_id int);";

    private String createString;


    public BaseSqliteHelper(Context context, String name, int version, String createString) {
        super(context, name, null, version);
        this.createString = createString;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}