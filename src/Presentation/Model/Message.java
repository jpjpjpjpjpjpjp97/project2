package Presentation.Model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class Message {
    private String text;
    private LocalDateTime created;
    private boolean isReceived;

    public Message() {
        this.text = "";
        this.created = LocalDateTime.now();
        this.isReceived = false;

    }

    public Message(String text, boolean isReceived) {
        this.text = text;
        this.created = LocalDateTime.now();
        this.isReceived = isReceived;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
