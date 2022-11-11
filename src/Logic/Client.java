package Logic;

import Presentation.Model.Message;
import Presentation.Model.User;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    private static Socket clientSocket = null;
    private static ObjectInputStream inputStream = null;
    private static BufferedReader inputLine = null;
    private static ObjectOutputStream outputStream = null;
    private static BufferedInputStream bufferedInputStream = null;
    private static boolean closed = false;

    int portNumber;
    String host;

    Client(){
        this.portNumber = 8080;
        this.host = "localhost";

        try{
            clientSocket = new Socket(host, portNumber);
//            inputLine = new BufferedReader(new InputStreamReader(System.in));
//            inputStream = new ObjectInputStream(clientSocket.getInputStream());
//            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

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
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            outputStream.writeUTF("authenticate");
            outputStream.flush();
            outputStream.writeUTF(testUsername);
            outputStream.flush();
            outputStream.writeUTF(testPassword);
            outputStream.flush();

            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            String receivedText = inputStream.readUTF();
            System.out.format("Auth: %s \n", receivedText);
            Message message = new Message("individual", "Hello World!", 1, 2, false);
            if (receivedText.equals("Successfully Authenticated")) {
                outputStream.writeObject(message);
                outputStream.flush();
            }
            outputStream.writeUTF("stop");
            outputStream.writeUTF("close");
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }

    public boolean registerUser(String name , String password) {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            outputStream.writeUTF("registerUser");
            outputStream.flush();
            outputStream.writeUTF(name);
            outputStream.flush();
            outputStream.writeUTF(password);
            outputStream.flush();

            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            String receivedText = inputStream.readUTF();
            System.out.format("Register: %s \n", receivedText);
            outputStream.writeUTF("close");
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            System.err.println("No Server found. Please ensure that the Server program is running and try again.");
        }
        return false;
    }


}
