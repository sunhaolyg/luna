package com.example.luna.poker.middle;

import android.content.Context;
import com.example.luna.poker.server.Room;
import com.example.luna.poker.server.RoomController;
import com.example.luna.poker.server.RoomMember;
import com.example.luna.poker.server.User;

import java.util.List;

public class MiddleClient {

    private MiddleServer middleServer;

    public MiddleClient(Context context) {
        middleServer = new RoomController(context);
    }

    public int createRoom(Room room) {
        return middleServer.createRoom(room);
    }

    public int joinRoom(User user, int roomId) {
        return middleServer.joinRoom(user, roomId);
    }

    public User insertUser(User user) {
        return middleServer.insertUser(user);
    }

    public User selectUserFromId(int userId) {
        return middleServer.selectUserFromId(userId);
    }

    public List<RoomMember> getMemebers(int roomId) {
        return middleServer.getMemebers(roomId);
    }

}
