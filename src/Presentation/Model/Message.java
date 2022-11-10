package Presentation.Model;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class Message implements Serializable {
    private String type;
    private String text;
    private int senderId;
    private int receiverId;
    private LocalDateTime created;
    private boolean isReceived;

    public Message() {
        this.type = "individual";
        this.text = "";
        this.senderId = 0;
        this.receiverId = 0;
        this.created = LocalDateTime.now();
        this.isReceived = false;
    }

    public Message(String type, String text, int senderId, int receiverId, boolean isReceived) {
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.created = LocalDateTime.now();
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
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
