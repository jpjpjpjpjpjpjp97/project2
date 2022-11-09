package Presentation.Model;

import java.util.List;

public class Contact {
    private User user;
    private List<Message> conversation;

    public Contact() {
        this.user = null;
        this.conversation = null;
    }

    public Contact(User user, List<Message> conversation) {
        this.user = user;
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                "user=" + user +
                ", conversation=" + conversation +
                '}';
    }
}
