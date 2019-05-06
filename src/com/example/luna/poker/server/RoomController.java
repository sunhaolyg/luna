package com.example.luna.poker.server;

import android.content.Context;
import com.example.luna.poker.Poker;
import com.example.luna.poker.PokerFactory;
import com.example.luna.poker.cow.BullCow;
import com.example.luna.poker.middle.MiddleServer;
import com.example.luna.poker.sqlite.RoomSqManager;

import java.util.List;

public class RoomController implements MiddleServer {

    private RoomSqManager roomSqManager;

    public RoomController(Context context) {
        this.roomSqManager = new RoomSqManager(context);
    }

    @Override
    public int createRoom(Room room) {
        int id = room.getRoom_master();
        User user = selectUserFromId(id);
        if (user == null) {
            return -1;
        }
        room.setCreate_time(System.currentTimeMillis() + "");
        roomSqManager.insertRoom(room);
        List<Room> rooms = roomSqManager.cursor();
        int roomId = 0;
        for (Room room1 : rooms) {
            if (room.getCreate_time().equals(room1.getCreate_time())) {
                roomId = room1.getId();
                break;
            }
        }
        RoomMember member = new RoomMember();
        member.setName(user.getName());
        member.setUser_id(user.getId());
        member.setRoom_id(roomId);
        insertMember(member);
        return roomId;
    }

    @Override
    public int joinRoom(User user, int roomId) {
        if (!canJoinRoom(user, roomId)) {
            return 0;
        }
        RoomMember member = new RoomMember();
        member.setRoom_id(roomId);
        member.setUser_id(user.getId());
        member.setName(user.getName());
        insertMember(member);

        List<Room> rooms = roomSqManager.cursor();
        Room room = null;
        for (Room room1 : rooms) {
            if (room1.getId() == roomId) {
                room = room1;
                break;
            }
        }
        List<RoomMember> members = getMemebers();
        for (int i = members.size() - 1; i >= 0; i--) {
            if (members.get(i).getRoom_id() != room.getId()) {
                members.remove(i);
            }
        }
        if (room.getMemberCount() == members.size()) {
            List<Poker> pokers = PokerFactory.getInstance().init().createPokers(5);
            for (int i = 0; i < members.size(); i++) {
                BullCow bullCow = new BullCow(pokers);
                bullCow.calculate();
                roomSqManager.updateRoomMember(bullCow.toString(), members.get(i).getId());
                pokers = PokerFactory.getInstance().createPokers(5);
            }

        }
        return 0;
    }

    private boolean memberEnough(int roomId) {
        List<Room> rooms = roomSqManager.cursor();
        Room room = null;
        for (Room room1 : rooms) {
            if (room1.getId() == roomId) {
                room = room1;
                break;
            }
        }
        List<RoomMember> members = getMemebers();
        return room.getMemberCount() == members.size();
    }

    private boolean canJoinRoom(User user, int roomId) {
        List<Room> rooms = roomSqManager.cursor();
        List<RoomMember> members = getMemebers();
        int currentCount = 0;
        for (int i = members.size() - 1; i >= 0; i--) {
            if (members.get(i).getRoom_id() == roomId) {
                currentCount++;
            }
        }
        for (Room room : rooms) {
            if (room.getId() == roomId && room.getMemberCount() > currentCount) {
                return true;
            }
        }
        return false;
    }


    public void insertMember(RoomMember member) {
        roomSqManager.insertMember(member);
        List<RoomMember> members = roomSqManager.cursorRoomMember();
    }

    private User insertUserIfNecessary(int id) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        User user = new User();
        user.setName(id + "");
        return insertUser(user);
    }

    public User insertUser(User user) {
        user.setCreate_time(System.currentTimeMillis() + "");
        roomSqManager.insertUser(user);
        List<User> users = getUsers();
        for (User user1 : users) {
            if (user1.getCreate_time().equals(user.getCreate_time())) {
                return user1;
            }
        }
        return null;
    }

    public List<RoomMember> getMemebers() {
        return roomSqManager.cursorRoomMember();
    }

    public List<RoomMember> getMemebers(int roomId) {
        List<RoomMember> members = getMemebers();
        for (int i = members.size() - 1; i >= 0; i--) {
            if (members.get(i).getRoom_id() != roomId) {
                members.remove(i);
            }
        }
        return members;
    }

    public List<User> getUsers() {
        return roomSqManager.cursorUser();
    }

    public User selectUserFromId(int userId) {
        List<User> users = getUsers();
        for (User user : users) {
            if (userId == user.getId()) {
                return user;
            }
        }
        return null;
    }

}
