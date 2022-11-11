package Presentation.Controller;

import Logic.Logic;

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
    public boolean registerUser (String name , String password) {
        return logic.registerUser(name , password);
    }

    public boolean updateUser (int id,String name , String password) {
        return logic.updateUser(id,name , password);
    }

    public boolean deleteUser (int id) {
        return logic.deleteUser(id);
    }
}
