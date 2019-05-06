package com.example.luna.poker.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.poker.middle.MiddleClient;
import com.example.luna.poker.server.RoomMember;
import com.example.luna.poker.server.User;

import java.util.List;

public class JoinMember implements View.OnClickListener {

    private Button button;
    private RoomMember member;
    private User user;
    private boolean join;
    private boolean canReady, ready;
    private Context mContext;
    private MiddleClient mClient;
    private int roomId;

    public JoinMember(Button button, int user_id, MiddleClient client, int roomId) {
        this.button = button;
        mContext = button.getContext();
        button.setOnClickListener(this);
        user = client.selectUserFromId(user_id);
        mClient = client;
        this.roomId = roomId;
    }

    @Override
    public void onClick(View v) {
        String text = button.getText().toString();
        if (!join) {
            if (text.equals(mContext.getString(R.string.unjoin))) {
                button.setText(mContext.getString(R.string.join));
                join = true;
                mClient.joinRoom(user, roomId);
            }
        } else if (canReady) {
            if (!ready) {
                ready = true;
                button.setText(mContext.getString(R.string.ready));
                mClient.joinRoom(user, roomId);
            }
        }
        getMembers();
    }

    public void callOnClick() {
        this.button.callOnClick();
    }

    public boolean equalSButton(Button button) {
        return button.equals(this.button);
    }

    public void getMembers() {
        List<RoomMember> members = mClient.getMemebers(roomId);
        for (RoomMember member : members) {
            if (member.getUser_id() == user.getId() && member.getPoker() != null) {
                this.button.setText(member.getPoker());
            }
        }
    }
}
