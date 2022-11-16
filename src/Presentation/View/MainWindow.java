package Presentation.View;

import Presentation.Controller.MainController;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private MainController mainController;
    private JPanel mainPanel;
    private JTextField messageTextField;
    private JButton sendMessageButton;
    private JList list1;

    private JTextArea messagesTextArea;
    private Timer checkMessagesTimer;

    public JTextArea getPendingMessagesArea() {
        return messagesTextArea;
    }

    public void setPendingMessagesArea(JTextArea pendingMessagesArea) {
        this.messagesTextArea = pendingMessagesArea;
    }

    public MainWindow(MainController mainController) throws InterruptedException {
        this.mainController = mainController;
        this.setContentPane(this.mainPanel);
        this.setSize(1600, 1000);
        checkMessagesTimer = null;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainWindow.this.setEnabled(false);
                mainController.closeConnection();
                if (checkMessagesTimer != null) {
                    checkMessagesTimer.stop();
                }
                super.windowClosing(e);
            }
        });
        this.sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.addMessage("individual", messageTextField.getText(), 1, 2);
            }
        });



         checkMessagesTimer = new Timer(5000, event -> {
            try {
                MainWindow.this.mainController.checkForMessages();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        checkMessagesTimer.start();


    }
}
