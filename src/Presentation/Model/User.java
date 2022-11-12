package Presentation.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractUser{
    private List<Contact> contactList;
    private boolean online;

    public User() {
        this.contactList = new ArrayList<>();
        this.online = false;
    }

    public User(int id, String username) {
        super(id, username);
        this.online = false;
    }

    public User(int id, String username, boolean online) {
        super(id, username);
        this.online = online;
    }

    public User(List<Contact> contactList) {
        this.contactList = contactList;
        this.online = false;
    }

    public User(int id, String username, List<Contact> contactList) {
        super(id, username);
        this.contactList = contactList;
        this.online = false;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    // TO IMPLEMENT
    public User getContact(int userId){
        return null;
    }

    public void addContact(Contact contact){
        this.contactList.add(contact);
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() +
                "username=" + this.getUsername() +
                ", online=" + online +
                '}';
    }
}
