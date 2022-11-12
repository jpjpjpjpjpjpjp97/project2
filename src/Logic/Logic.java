package Logic;

import Data.Data;
import Presentation.Model.User;

import java.io.IOException;
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

    public boolean authenticate(String testUsername, String testPassword) {
        return client.authenticate(testUsername, testPassword);
    }
    public boolean registerUser(String name , String password) {
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

    public void closeConnection(){
        client.closeConnection();
    }
}
