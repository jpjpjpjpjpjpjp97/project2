package Presentation.View;

import Presentation.Controller.MainController;

import java.util.concurrent.TimeUnit;

public class MainView {
    private MainController mainController;
    public static void main(String[] args) throws InterruptedException {
        String testUsername = "root";
        String testPassword = "Leosanpo16";

        MainController mainController = new MainController();
        TimeUnit.SECONDS.sleep(1);
        //mainController.authenticate(testUsername, testPassword);
        mainController.registerUser("Julio" , "1234");
    }
}