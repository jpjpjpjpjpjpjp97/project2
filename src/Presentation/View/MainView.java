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
//        mainController.authenticate(testUsername, testPassword);
         //mainController.registerUser("jdiaz" , "1234");
        //mainController.registerUser("leosanpo16" , "1234");
     //  mainController.updateUser(1,"Jose" , "4321");
          //mainController.deleteUser(2);

//        mainController.addMessage("Hola" , 1);
//        mainController.addMessage("Hola" , 2);
//        mainController.addMessage("2134" , 1);
//        mainController.addMessage("1234" , 2);
        mainController.listMessages();
       //  mainController.deleteMessage(1);
       // mainController.listUsers();
       // mainController.filterUsers("jdi");
        mainController.closeConnection();
    }
}
