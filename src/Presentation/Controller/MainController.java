package Presentation.Controller;

import Logic.Logic;
import Presentation.Model.Contact;
import Presentation.Model.Message;
import Presentation.Model.User;
import Presentation.View.LoginWindow;
import Presentation.View.MainWindow;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Logic logic;
    private MainWindow mainWindow;
    private LoginWindow loginWindow;
    private int userId;
    private String username;

    public MainController() {
        this.logic = new Logic();
    }

    public MainController(Logic logic) {
        this.logic = logic;
    }

    public boolean authenticate(String username, String password) {
        this.userId = logic.authenticate(username, password);
        if (this.userId != 0){
            this.username = username;
        }
        return (this.userId != 0);
    }

    public List<User> listUsers(){
        return logic.listUsers();
    }

    public List<User> filterUsers(String username){
        return logic.filterUsers(username);
    }

    public int registerUser (String name , String password) {
        return logic.registerUser(name , password);
    }

    public boolean updateUser (int id,String name , String password) {
        return logic.updateUser(id,name , password);
    }

    public boolean deleteUser (int id) {
        return logic.deleteUser(id);
    }

    public void addContact(int contactId, String contactUsername) {
        logic.addContact(this.userId, contactId, contactUsername);
    }
    //MESSAGES
    public List<Message> listMessages(){
        return logic.listMessages();
    }

    public int isOnline(int id , String name){
        return logic.isOnline(id ,name);
    }

    public List<Message> filterMessages(String text){
        return logic.filterMessages(text);
    }

    public List<Message> getNewMessages(int receiverId){
        return logic.getNewMessages(receiverId);
    }

    public List<Message> getPendingMessages(){
        return logic.getPendingMessages(this.userId);
    }

    public boolean addMessage (String type, String text, int senderId, int receiverId) {
        return logic.addMessage(type, text ,senderId, receiverId);
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

    public void checkForMessages() throws InterruptedException {
        ArrayList<Message> messagesList = new ArrayList<>();
        System.out.println("Checking for new messages...");
        messagesList.clear();
        messagesList = (ArrayList<Message>) this.getNewMessages(this.userId);
        for (Message message : messagesList) {
            message.toString();
        }
    }

    public int getIdForUsername(String username) {
        return logic.getIdForUsername(username);
    }

    public void startMainWindow() throws InterruptedException {
        this.mainWindow = new MainWindow(this);
        mainWindow.setTitle("Usuario: " + this.username);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    public void startLoginWindow() {
        this.loginWindow = new LoginWindow(this);
        loginWindow.setTitle("Chat: Registro e Inicio de Sesión");
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }

    public void closeLoginWindow() throws InterruptedException {
        this.loginWindow.dispose();
        if (this.getUserId() != 0){
            this.startMainWindow();

        }
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Contact> readContactList() {
        return logic.readContactList(this.userId);
    }
}
