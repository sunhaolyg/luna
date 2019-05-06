package com.example.luna.poker.cow;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.poker.Poker;
import com.example.luna.poker.PokerFactory;
import com.example.luna.poker.middle.MiddleClient;
import com.example.luna.poker.room.RoomActivity;
import com.example.luna.poker.server.Room;
import com.example.luna.poker.server.User;
import com.example.luna.utils.Logutils;

import java.util.ArrayList;
import java.util.List;

public class CowActivity extends BaseActivity {


    private List<Poker> mPokers = new ArrayList<>();
    private Button cow_fresh, register_user;
    private int mCount;
    private EditText roomId, userId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            apply();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow);
        cow_fresh = findViewById(R.id.cow_fresh);
        roomId = findViewById(R.id.room_id);
        userId = findViewById(R.id.user_id);
        register_user = findViewById(R.id.register_user);
    }

    public void cow(View v) {
        MiddleClient client = new MiddleClient(this);
        User user = new User();
        int room_id = Integer.parseInt(roomId.getText().toString());
        int user_id = Integer.parseInt(userId.getText().toString());
        switch (v.getId()) {
            case R.id.cow_fresh:
                apply();
                break;
            case R.id.cow_show:
                bullcow();
                break;
            case R.id.register_user:
                register_user.setText(getString(R.string.register_user));
                user.setName(user_id + "");
                user = client.insertUser(user);
                register_user.append("  " + user.toString());
                break;
            case R.id.create_room:
                Room room = new Room();
                room.setMemberCount(room_id);
                room.setRoom_master(user_id);
                int id = client.createRoom(room);
                Logutils.d("WaterFallLayout", "room_id = " + id);
                if (id != -1) {
                    Intent intent = new Intent(this, RoomActivity.class);
                    intent.putExtra("room_id", id);
                    startActivity(intent);
                }
                break;
            case R.id.join_room:
                user = client.selectUserFromId(user_id);
                client.joinRoom(user, room_id);
                break;
        }
    }

    private void bullcow() {
        BullCow bullCow = new BullCow(PokerFactory.getInstance().init().createPokers(5));
        bullCow.calculate();

        BullCow bullCow1 = new BullCow(PokerFactory.getInstance().createPokers(5));
        bullCow1.calculate();

        BullCow bullCow2 = new BullCow(PokerFactory.getInstance().createPokers(5));
        bullCow2.calculate();

        BullCow bullCow3 = new BullCow(PokerFactory.getInstance().createPokers(5));
        bullCow3.calculate();
        Logutils.d("WaterFallLayout", bullCow + "\r\n" + bullCow1 + "\r\n" + bullCow2 + "\r\n" + bullCow3);
    }

    private void apply() {
        mCount = 0;
        for (; ; ) {

            BullCow bullCow = new BullCow(PokerFactory.getInstance().init().createCow());
            bullCow.calculate();
            mCount++;
//        cow_fresh.setText(mCount + "," + bullCow.toString());
            Logutils.d("WaterFallLayout", mCount + "," + bullCow.toString());
            if (bullCow.getType() >= 200000) {
                return;
            }
        }
    }

}
