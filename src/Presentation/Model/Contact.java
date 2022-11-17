package Presentation.Model;

import java.util.List;

public class Contact {
    private int userId;
    private String username;
    private List<Message> conversation;

    public Contact() {
        this.userId = 0;
        this.username = "";
        this.conversation = null;
    }

    public Contact(int userId, String username, List<Message> conversation) {
        this.userId = userId;
        this.username = username;
        this.conversation = conversation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Message> getConversation() {
        return conversation;
    }

    public void setConversation(List<Message> conversation) {
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
