package Presentation.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
public class Message implements Serializable {
    private String type;
    private int id;
    private String text;
    private int senderId;
    private int receiverId;
    private Timestamp created;
    private boolean isReceived;

    public Message() {
        this.type = "individual";
        this.text = "";
        this.senderId = 0;
        this.receiverId = 0;
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.isReceived = false;
    }

    public Message(String type, String text, int senderId, int receiverId, boolean isReceived) {
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.isReceived = isReceived;
    }

    public Message(String type, int id, String text, int senderId, int receiverId, Timestamp created, boolean isReceived) {
        this.type = type;
        this.id = id;
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.created = created;
        this.isReceived = isReceived;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", created=" + created +
                ", isReceived=" + isReceived +
                '}';
    }
}
