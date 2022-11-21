package Presentation.View;

import Presentation.Controller.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginWindow extends JFrame{
    private MainController mainController;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JPanel mainPanel;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel buttonPanel;
    private JLabel errorLabel;

    public LoginWindow(MainController mainController) {
        this.mainController = mainController;
        this.setContentPane(this.mainPanel);
        this.setSize(1000, 400);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainController.closeConnection();
                System.exit(0);
                super.windowClosing(e);
            }
        });
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainController.authenticate(usernameTextField.getText(), String.valueOf(passwordField.getPassword()))){
                    errorLabel.setText("Error al iniciar sesión, usuario o contraseña inválidos.");
                }
                else{
                    System.out.println("Successfully Logged In");
                    try {
                        mainController.closeLoginWindow();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        this.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!String.valueOf(passwordField.getPassword()).equals("")) {
                    if (mainController.registerUser(usernameTextField.getText(), String.valueOf(passwordField.getPassword())) != 0) {

                        errorLabel.setText("Registro exitoso! Inicia sesión.");
                    } else {
                        errorLabel.setText("Usuario no disponible.");
                    }
                }
                else {
                    errorLabel.setText("Debe ingresar una contraseña.");
                }
            }
        });
    }
}
