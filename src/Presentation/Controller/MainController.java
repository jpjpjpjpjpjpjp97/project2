package Presentation.Controller;

import Logic.Logic;
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

    public void closeConnection () {
        logic.closeConnection();
    }
}
