package Presentation.Model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private String name;
    private User administrator;
    private List<User> roomMembers;

    public Room() {
        this.roomId = 0;
        this.name = "";
        this.administrator = null;
        this.roomMembers = new ArrayList<>();
    }

    public Room(int roomId, String name, User administrator) {
        this.roomId = roomId;
        this.name = name;
        this.administrator = administrator;
    }

    public Room(int roomId, String name, User administrator, List<User> roomMembers) {
        this.roomId = roomId;
        this.name = name;
        this.administrator = administrator;
        this.roomMembers = roomMembers;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public List<User> getRoomMembers() {
        return roomMembers;
    }

    public void setRoomMembers(List<User> roomMembers) {
        this.roomMembers = roomMembers;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", administrator=" + administrator +
                ", roomMembers=" + roomMembers +
                '}';
    }
}
