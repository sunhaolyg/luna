package com.example.luna.poker.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.luna.poker.server.Room;
import com.example.luna.poker.server.RoomMember;
import com.example.luna.poker.server.User;
import com.example.luna.sqlite.BaseSqManager;
import com.example.luna.sqlite.BaseSqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class RoomSqManager extends BaseSqManager {

    public static final String TABLE_ROOM_NAME = "poker_room";
    public static final String TABLE_USER_NAME = "poker_user";
    public static final String TABLE_MEMBER_NAME = "poker_member";
    private static final int DATA_VERSION = 1;

    public RoomSqManager(Context context) {
        mHelper = new BaseSqliteHelper(context, BaseSqliteHelper.POKER_DATABASE, DATA_VERSION, BaseSqliteHelper.CREATE_ROOM) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                super.onCreate(db);
                db.execSQL(BaseSqliteHelper.CREATE_USER);
                db.execSQL(BaseSqliteHelper.CREATE_MEMBER);
            }
        };
    }

   /* public RoomSqManager(Context context) {
        mHelper = new BaseSqliteHelper(context, TABLE_ROOM_NAME, NOTE_VERSION,BaseSqliteHelper.CREATE_ROOM);
    }*/

    public void insertRoom(Room bean) {
        insert("insert into " + TABLE_ROOM_NAME + "(room_master, create_time, complete, member_count) values (?,?,?,?) ",
                new Object[]{bean.getRoom_master(),
                        bean.getCreate_time(),
                        bean.getComplete(),
                        bean.getMemberCount()});
    }

    public void insertMember(RoomMember bean) {
        /*"name txt," +
                "room_id txt," +
                "poker txt," +
                "spare txt," +
                "user_id int);";*/
        insert("insert into " + TABLE_MEMBER_NAME + "(name, room_id, poker, spare, user_id) values (?,?,?,?,?) ",
                new Object[]{bean.getName(),
                        bean.getRoom_id(),
                        bean.getPoker(),
                        bean.getSpare(),
                        bean.getUser_id()});
    }

    public void insertUser(User bean) {
        /*"name txt," +
            "create_time txt," +
            "spare txt," +
            "money int);";*/
        insert("insert into " + TABLE_USER_NAME + "(name, create_time, spare, money) values (?,?,?,?) ",
                new Object[]{bean.getName(),
                        bean.getCreate_time(),
                        bean.getSpare(),
                        bean.getMoney()});
    }

    public List<User> cursorUser() {
        List<User> beans = new ArrayList<>();
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from " + TABLE_USER_NAME, null);
        while (cursor.moveToNext()) {
            User bean = new User();
            /*"name txt," +
            "create_time txt," +
            "spare txt," +
            "money int);";*/
            bean.setId(cursor.getInt(0));
            bean.setName(cursor.getString(1));
            bean.setCreate_time(cursor.getString(2));
            bean.setSpare(cursor.getString(3));
            bean.setMoney(cursor.getInt(4));
            beans.add(bean);
        }
        cursor.close();
        sd.close();
        return beans;
    }

    public List<Room> cursor() {
        List<Room> beans = new ArrayList<>();
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from " + TABLE_ROOM_NAME, null);
        while (cursor.moveToNext()) {
            Room bean = new Room();
            bean.setId(cursor.getInt(0));
            bean.setRoom_master(cursor.getInt(1));
            bean.setCreate_time(cursor.getString(2));
            bean.setComplete(cursor.getInt(3));
            bean.setMemberCount(cursor.getInt(4));
            beans.add(bean);
        }
        cursor.close();
        sd.close();
        return beans;
    }

    public List<RoomMember> cursorRoomMember() {
        List<RoomMember> beans = new ArrayList<>();
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from " + TABLE_MEMBER_NAME, null);
        while (cursor.moveToNext()) {
            RoomMember bean = new RoomMember();
            bean.setId(cursor.getInt(0));
            bean.setName(cursor.getString(1));
            bean.setRoom_id(cursor.getInt(2));
            bean.setPoker(cursor.getString(3));
            bean.setSpare(cursor.getString(4));
            bean.setUser_id(cursor.getInt(5));
            beans.add(bean);
        }
        cursor.close();
        sd.close();
        return beans;
    }

    public void delete() {
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.delete("notes", "date = ?", new String[]{"1509506963465"});
        sd.close();
    }

    public void updateRoomMember(String poker, int id) {
        SQLiteDatabase sd = mHelper.getWritableDatabase();
        sd.execSQL("update " + TABLE_MEMBER_NAME + " set poker = '" + poker + "' where id = ? ", new Object[]{id});
        sd.close();
    }

}
