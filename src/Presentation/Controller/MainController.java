package Presentation.Controller;

import Logic.Logic;
import Presentation.Model.Message;
import Presentation.Model.User;

import java.util.List;

public class MainController {
    private Logic logic;

    public MainController() {
        this.logic = new Logic();
    }

    public MainController(Logic logic) {
        this.logic = logic;
    }

    public boolean authenticate(String testUsername, String testPassword) {
        return logic.authenticate(testUsername, testPassword);
    }

    public List<User> listUsers(){
        return logic.listUsers();
    }

    public List<User> filterUsers(String username){
        return logic.filterUsers(username);
    }

    public boolean registerUser (String name , String password) {
        return logic.registerUser(name , password);
    }

    public boolean updateUser (int id,String name , String password) {
        return logic.updateUser(id,name , password);
    }

    public boolean deleteUser (int id) {
        return logic.deleteUser(id);
    }



    //MESSAGES

    public List<Message> listMessages(){
        return logic.listMessages();
    }

    public List<Message> filterMessages(String text){
        return logic.filterMessages(text);
    }

    public boolean addMessage (String text , int id_con) {
        return logic.addMessage(text , id_con);
    }

    public boolean updateMessage (int id,String text , int id_con) {
        return logic.updateMessage(id,text , id_con);
    }

    public boolean deleteMessage (int id) {
        return logic.deleteMessage(id);
    }
    public void closeConnection () {
        logic.closeConnection();
    }
}
