package Presentation.View;

import Presentation.Controller.MainController;

import java.util.concurrent.TimeUnit;

public class MainView {
    private MainController mainController;
    private MainView mainView;
    public static void main(String[] args) throws InterruptedException {
        String testUsername = "leo";
        String testPassword = "1234";

        MainController mainController = new MainController();
        mainController.startLoginWindow();
    }
}
