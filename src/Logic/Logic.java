package Logic;

import Data.Data;
import Presentation.Model.Message;
import Presentation.Model.User;

import java.util.List;

public class Logic {
    Client client;
    Data data;

    public Logic() {
        this.data = new Data();
        this.client = new Client(data);
    }

    public Logic(Client client) {
        this.client = client;
    }

    public int authenticate(String username, String password) {
        return client.authenticate(username, password);
    }
    public int registerUser(String name , String password) {
        return client.registerUser(name, password);
    }
    public boolean updateUser(int id , String name , String password) {
        return client.updateUser(id,name, password);
    }
    public boolean deleteUser(int id) {
        return client.deleteUser(id);
    }

    public List<User> listUsers(){
        return client.listUsers();
    }

    public List<User> filterUsers(String username){
        return client.filterUsers(username);
    }


    //MESSAGES
    public boolean addMessage(String type, String text, int senderId, int receiverId) {
        return client.addMessage(type, text, senderId, receiverId);
    }
    public boolean updateMessage(int id , String text ,int id_con) {
        return client.updateMessage(id,text, id_con);
    }
    public boolean deleteMessage(int id) {
        return client.deleteMessage(id);
    }

    public List<Message> listMessages(){
        return client.listMessages();
    }

    public List<Message> filterMessages(String text){
        return client.filterMessages(text);
    }

    public List<Message> getNewMessages(int receiverId) {
        return client.getNewMessages(receiverId);
    }

    public void closeConnection(){
        client.closeConnection();
    }
}
