package Presentation.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractUser{
    private List<Contact> contactList;

    public User() {
        this.contactList = new ArrayList<>();
    }

    public User(int id, String username, String password) {
        super(id, username, password);
    }

    public User(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public User(int id, String username, String password, List<Contact> contactList) {
        super(id, username, password);
        this.contactList = contactList;
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
}
