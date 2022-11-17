package Logic;

import Data.Data;
import Data.JSONSerializer;
import Presentation.Model.Contact;
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
    private static JSONSerializer jsonSerializer;

    int portNumber;
    String host;

    Client(Data data){
        this.portNumber = 8080;
        this.host = "localhost";
        this.data = data;
        this.jsonSerializer = new JSONSerializer();
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
        System.out.println("Executing running method...");
    }

    public int authenticate(String username, String password) {
        try {
            outputStream.writeUTF("authenticate");
            outputStream.flush();
            outputStream.writeUTF(username);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();

            int receivedId = inputStream.readInt();
            System.out.format("Authenticated Id: %s \n", receivedId);
            return receivedId;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return 0;
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
    public int registerUser(String name , String password) {
        try {
            outputStream.writeUTF("registerUser");
            outputStream.flush();
            outputStream.writeUTF(name);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();

            int userId = inputStream.readInt();
            jsonSerializer.writeContactList(userId, new ArrayList<>());
            System.out.format("Registered Id: %s \n", userId);
            return userId;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return 0;
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
            outputStream.writeUTF("PendingMessages");
            outputStream.flush();
            outputStream.writeInt(userId);
            outputStream.flush();

            ArrayList<Message> pendingMessages = (ArrayList<Message>) inputStream.readObject();
            for (Message newMessage : pendingMessages) {
                System.out.println(newMessage.toString());
                // SAVE TO JSON ON CORRECT CONTACT
                this.deleteMessage(newMessage.getId());
            }
            return pendingMessages;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void addContact(int userId, int contactId, String contactUsername) {
        List<Contact> contactList = jsonSerializer.readContactList(userId);
        contactList.add(new Contact(contactId, contactUsername, new ArrayList<>()));
        jsonSerializer.writeContactList(userId, contactList);
    }

    //MESSAGE
    public List<Message> listMessages() {
        try {
            outputStream.writeUTF("listMessages");
            outputStream.flush();

            ArrayList<Message> messageList = (ArrayList<Message>) inputStream.readObject();
            for (Message message : messageList) {
                System.out.println(message.toString());
            }
            return messageList;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Message> filterMessages(String text) {
        try {
            outputStream.writeUTF("filterMessages");
            outputStream.flush();
            outputStream.writeUTF(text);
            outputStream.flush();

            ArrayList<Message> filteredMessageList = (ArrayList<Message>) inputStream.readObject();
            for (Message message : filteredMessageList) {
                System.out.println(message.toString());
            }
            return filteredMessageList;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addMessage(String type, String text, int senderId, int receiverId) {
        try {
            Message newMessage = new Message(type, text, senderId, receiverId, false);

            outputStream.writeUTF("addMessage");
            outputStream.flush();
            outputStream.writeObject(newMessage);
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Add Message: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public boolean updateMessage(int id , String text , int id_conversation) {
        try {
            outputStream.writeUTF("updateMessage");
            outputStream.flush();
            outputStream.writeUTF(String.valueOf(id));
            outputStream.flush();
            outputStream.writeUTF(text);
            outputStream.flush();
            outputStream.writeUTF(String.valueOf(id_conversation));
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Update: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public boolean deleteMessage(int id) {
        try {
            outputStream.writeUTF("deleteMessage");
            outputStream.flush();
            outputStream.writeInt(id);
            outputStream.flush();

            String receivedText = inputStream.readUTF();
            System.out.format("Delete: %s \n", receivedText);
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public List<Message> getNewMessages(int receiverId) {
        ArrayList<Message> messageList = new ArrayList<>();
        try {
            outputStream.writeUTF("getNewMessages");
            outputStream.flush();
            outputStream.writeInt(receiverId);
            outputStream.flush();

            messageList = (ArrayList<Message>) inputStream.readObject();
            for (Message newMessage : messageList) {
                System.out.println(newMessage.toString());
                // SAVE TO JSON ON CORRECT CONTACT
                this.deleteMessage(newMessage.getId());
            }
            return messageList;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return messageList;
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

    public int getIdForUsername(String username) {
        try {
            outputStream.writeUTF("getIdForUsername");
            outputStream.flush();
            outputStream.writeUTF(username);
            outputStream.flush();
            return inputStream.readInt();

        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return 0;
    }

    public List<Contact> readContactList(int userId) {
        return jsonSerializer.readContactList(userId);
    }
}
