package com.example.luna.poker.middle;

import com.example.luna.poker.server.Room;
import com.example.luna.poker.server.RoomMember;
import com.example.luna.poker.server.User;

import java.util.List;

public interface MiddleServer {

    int createRoom(Room room);

    int joinRoom(User user, int roomId);

    User insertUser(User user);

    User selectUserFromId(int userId);

    List<RoomMember> getMemebers(int roomId);


}
