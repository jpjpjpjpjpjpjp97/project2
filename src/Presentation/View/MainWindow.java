package Presentation.View;

import Presentation.Controller.MainController;
import Presentation.Model.Contact;
import Presentation.Model.Message;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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
    private JScrollPane messagesScrollPane;
    private JPanel messagesPanel;
    private Timer checkMessagesTimer;
    private boolean searching = false;
    private String currentContactUsername;
    private int currentContactId;

    public MainWindow(MainController mainController) throws InterruptedException {
        this.mainController = mainController;
        this.setContentPane(this.mainPanel);
        this.setSize(1000, 600);
        checkMessagesTimer = null;
        this.refreshContacts();
        this.currentContactId = 0;
        this.currentContactUsername = "";
        sendMessageButton.setEnabled(false);
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
                mainController.addMessage("individual", messageTextField.getText(), mainController.getUserId(), currentContactId);
                setCurrentContact(currentContactUsername);
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
                }
                else{
                    contactAlertLabel.setText("User does not exist.");
                }
            }
        });
//        this.searchContactButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String searchText = searchContactTextField.getText();
//                if (!searchText.equals("")){
//                    MainWindow.this.filterContact(searchText);
//                    searching = true;
//                }
//                else
//                    searching = false;
//            }
//        });
        this.searchContactTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                search();
            }
            public void removeUpdate(DocumentEvent e) {
                search();
            }
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            public void search() {
                if (!searchContactTextField.getText().equals("")){
                    MainWindow.this.filterContact(searchContactTextField.getText());
                    searching = true;
                }
                else{
                    MainWindow.this.filterContact(searchContactTextField.getText());
                    searching = false;
                }
            }
        });
        this.contactList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    sendMessageButton.setEnabled(true);
                    messagesPanel.removeAll();
                    MainWindow.this.currentContactUsername = ((String) list.getSelectedValue()).split(" ")[0];
                    MainWindow.this.setCurrentContact(currentContactUsername);
                    List <Contact> contactList = mainController.readContactList();
                    for (Contact contact : contactList) {
                        if (currentContactUsername.equals(contact.getUsername())) {
                            MainWindow.this.currentContactId = contact.getUserId();
                        }
                    }
                    MainWindow.this.refreshContacts();
                    try {
                        mainController.checkForMessages();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(currentContactId + " | " + currentContactUsername);
                }
            }
        });
        checkMessagesTimer = new Timer(5000, event -> {
            try {
                if(!searching) {
                    MainWindow.this.mainController.checkForMessages();
                    MainWindow.this.refreshContacts();
                    setCurrentContact(currentContactUsername);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        checkMessagesTimer.start();
    }

    private void setCurrentContact(String username) {
        mainController.readContactList().forEach(contact -> {
            if (contact.getUsername().equals(username)){
                this.refreshConversation(contact);
            }
        });
    }

    private void refreshConversation(Contact contact) {
        messagesPanel.removeAll();
        contact.getConversation().forEach(message -> {
            addMessageToPanel(message, messagesPanel);
        });
        messagesPanel.updateUI();
    }

    private void addMessageToPanel(Message message, JPanel messagesPanel) {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet styleAttributes = new SimpleAttributeSet();
        if (message.isReceived()){
            StyleConstants.setForeground(styleAttributes, Color.DARK_GRAY);
            StyleConstants.setBackground(styleAttributes, Color.LIGHT_GRAY);
        }
        else {
            StyleConstants.setAlignment(styleAttributes, StyleConstants.ALIGN_RIGHT);
            StyleConstants.setForeground(styleAttributes, Color.DARK_GRAY);
            StyleConstants.setBackground(styleAttributes, Color.GREEN);
        }
        StyleConstants.setBold(styleAttributes, true);
        try{
            doc.insertString(doc.getLength(), message.getText() + " " + message.getCreated(), styleAttributes );
            doc.setParagraphAttributes(0, doc.getLength(), styleAttributes, false);
        }
        catch(Exception e) {
            System.out.println("Error: "+ e.toString());
        }
        messagesPanel.add(textPane);
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

    private void filterContact(String text) {
        List<String> elements = new ArrayList<>();
        mainController.readContactList().forEach(contact -> {
            if(contact.getUsername().contains(text)) {
                if(mainController.isOnline(contact.getUserId() , contact.getUsername()) == 1){
                    elements.add(contact.getUsername()+" (Online +)");
                } else
                    elements.add(contact.getUsername()+" (Offline -)");
            }
        });
        contactListModel.removeAllElements();
        contactListModel.addAll(elements);
    }

    private void createUIComponents() {
        this.contactListModel = new DefaultListModel<>();
        this.contactList = new JList<>(contactListModel);
        this.contactList.setMaximumSize(new Dimension(50, 1000));
        this.messagesPanel = new JPanel();
        this.messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
    }
}
