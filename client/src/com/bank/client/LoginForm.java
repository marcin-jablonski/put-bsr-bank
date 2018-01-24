package com.bank.client;

import com.bank.types.BankPortType;
import com.bank.types.BankPortTypeService;
import com.bank.types.LoginRequest;
import com.bank.types.LoginResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JButton loginButton;
    private JPanel mainLoginPanel;
    private JTextField passwordTextField;
    private JTextField usernameTextField;

    private static JFrame self;

    public LoginForm() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CredentialsManager.getInstance().setCredentials(usernameTextField.getText(), passwordTextField.getText());

                BankPortTypeService service = new BankPortTypeService();
                service.setHandlerResolver(new CustomHeaderHandler());
                BankPortType bankPort = service.getBankPortTypeSoap11();

                LoginRequest request = new LoginRequest();
                LoginResponse response;
                try {
                    response = bankPort.login(request);
                    JFrame frame = new JFrame("MainWindowFrame");
                    frame.setContentPane(new MainWindowForm(response.getAccounts()).panel1);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.pack();
                    frame.setVisible(true);
                    self.setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginFrame");
        frame.setContentPane(new LoginForm().mainLoginPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        self = frame;
    }
}
