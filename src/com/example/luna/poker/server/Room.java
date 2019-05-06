package com.example.luna.poker.server;

import java.util.List;

public class Room {

    private int id;
    private int room_master;
    private String create_time;
    private int complete;
    private List<RoomMember> roomMembers;
    private int memberCount;

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom_master() {
        return room_master;
    }

    public void setRoom_master(int room_master) {
        this.room_master = room_master;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public List<RoomMember> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<RoomMember> roomMembers) {
        this.roomMembers = roomMembers;
    }
}
