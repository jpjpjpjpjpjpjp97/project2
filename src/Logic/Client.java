package Logic;

import Data.Data;
import Presentation.Model.Message;
import Presentation.Model.User;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable{
    private static Socket clientSocket = null;
    private static ObjectInputStream inputStream = null;
    private static BufferedReader inputLine = null;
    private static ObjectOutputStream outputStream = null;
    private static BufferedInputStream bufferedInputStream = null;
    private static boolean closed = false;
    private static Data data;

    int portNumber;
    String host;

    Client(Data data){
        this.portNumber = 8080;
        this.host = "localhost";
        this.data = data;
        try{
            clientSocket = new Socket(host, portNumber);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Unknown " + host);
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
    }

    @Override
    public void run() {

    }

    public boolean authenticate(String testUsername, String testPassword) {
        try {
            outputStream.writeUTF("authenticate");
            outputStream.flush();
            outputStream.writeUTF(testUsername);
            outputStream.flush();
            outputStream.writeUTF(testPassword);
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Auth: %s \n", receivedText);
            Message message = new Message("individual", "Hello World!", 1, 2, false);
            if (receivedText.equals("Successfully Authenticated")) {
                outputStream.writeObject(message);
                outputStream.flush();
            }
            outputStream.writeUTF("stop");
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public List<User> listUsers() {
        try {
            outputStream.writeUTF("listUsers");
            outputStream.flush();

            ArrayList<User> userList = (ArrayList<User>) inputStream.readObject();
            for (User user : userList) {
                System.out.println(user.toString());
            }
            return userList;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<User> filterUsers(String text) {
        try {
            outputStream.writeUTF("filterUsers");
            outputStream.flush();
            outputStream.writeUTF(text);
            outputStream.flush();

            ArrayList<User> filteredUserList = (ArrayList<User>) inputStream.readObject();
            for (User user : filteredUserList) {
                System.out.println(user.toString());
            }
            return filteredUserList;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean registerUser(String name , String password) {
        try {
            outputStream.writeUTF("registerUser");
            outputStream.flush();
            outputStream.writeUTF(name);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Register: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public boolean updateUser(int id , String name , String password) {
        try {
            outputStream.writeUTF("updateUser");
            outputStream.flush();
            outputStream.writeUTF(String.valueOf(id));
            outputStream.flush();
            outputStream.writeUTF(name);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Update: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public boolean deleteUser(int id) {
        try {
            outputStream.writeUTF("deleteUser");
            outputStream.flush();
            outputStream.writeUTF(String.valueOf(id));
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Delete: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public List<Message> getPendingMessages(int userId) {
        try {
            outputStream.writeUTF("pendingMessages");
            outputStream.flush();

            ArrayList<Message> pendingMessages = (ArrayList<Message>) inputStream.readObject();
            for (Message newMessage : pendingMessages) {
                System.out.println(newMessage.toString());
            }
            return pendingMessages;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void closeConnection(){
        try {
            outputStream.writeUTF("close");
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
    }
}
