package Presentation.View;

import Presentation.Controller.MainController;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private MainController mainController;
    private JPanel mainPanel;
    private JTextField messageTextField;
    private JButton sendMessageButton;
    private JList contactList;
    DefaultListModel<String> contactListModel;

    DefaultListModel<String> contactListModelAUX;
    private JTextArea messagesTextArea;
    private JTextField addContactTextField;
    private JButton addContactButton;
    private JLabel addContactLabel;
    private JPanel messagePanel;
    private JLabel messagesLabel;
    private JPanel contactsPanel;
    private JLabel contactsLabel;
    private JLabel chatLabel;
    private JLabel contactAlertLabel;
    private JTextField searchContactTextField;
    private JButton searchContactButton;
    private JLabel searchContactLabel;
    private Timer checkMessagesTimer;
    private boolean searching = false;


    public MainWindow(MainController mainController) throws InterruptedException {

        this.mainController = mainController;
        this.setContentPane(this.mainPanel);
        this.setSize(1000, 600);
        checkMessagesTimer = null;
        this.refreshContacts();
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
                mainController.showMessages();
                messageTextField.setText("");
            }
        });

        this.addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contactUsername = addContactTextField.getText();
                int contactId = mainController.getIdForUsername(contactUsername);
                if (contactId != 0){
                    mainController.addContact(contactId, contactUsername);
                    MainWindow.this.refreshContacts();
                    // Add contact to JSON
                    // Add contact to interface contact list
                }
                else{
                    contactAlertLabel.setText("User does not exist.");
                }
            }
        });

        this.searchContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchContactTextField.getText();
                if (searchText != ""){
                    MainWindow.this.filterContact(searchText);
                    searching = true;
                }
                else
                    searching = false;

            }

        });
        checkMessagesTimer = new Timer(5000, event -> {
            try {
                MainWindow.this.mainController.checkForMessages();

                if(!searching)
                  MainWindow.this.refreshContacts();
                else
                  MainWindow.this.filterContact(searchContactTextField.getText());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        checkMessagesTimer.start();



    }

    public JTextArea getPendingMessagesArea() {
        return messagesTextArea;
    }

    public void setPendingMessagesArea(JTextArea pendingMessagesArea) {
        this.messagesTextArea = pendingMessagesArea;
    }

    private void refreshContacts() {
        this.contactListModel.removeAllElements();

        mainController.readContactList().forEach(contact -> {
            if(mainController.isOnline(contact.getUserId() , contact.getUsername()) == 1){
                this.contactListModel.addElement(contact.getUsername()+" (Online +)");
            } else
                this.contactListModel.addElement(contact.getUsername()+" (Offline -)");
        });
    }


    private void createUIComponents() {
            this.contactListModel = new DefaultListModel<>();
            this.contactList = new JList<>(contactListModel);
    }

    private void filterContact(String text) {
        this.contactListModelAUX = new DefaultListModel<>();
        mainController.readContactList().forEach(contact -> {
            if(contact.getUsername().contains(text)) {

                if(mainController.isOnline(contact.getUserId() , contact.getUsername()) == 1){
                    this.contactListModelAUX.addElement(contact.getUsername()+" (Online +)");
                } else
                    this.contactListModelAUX.addElement(contact.getUsername()+" (Offline -)");
            }
        });
        searching = true;
        contactList.setModel(contactListModelAUX);


    }
}
