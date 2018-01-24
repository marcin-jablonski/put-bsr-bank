package com.bank.client;

import com.bank.types.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindowForm {
    public JPanel panel1;
    private JPanel accountListPanel;
    private JPanel TransferPanel;
    private JPanel withdrawPanel;
    private JPanel paymentPanel;
    private JList accountList;
    private JButton paymentButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JTextField paymentAmountTextField;
    private JTextField paymentAccountTextField;
    private JTextField withdrawAmountTextField;
    private JTextField withdrawAccountTextField;
    private JTextField transferFromTextField;
    private JTextField transferToTextField;
    private JButton getHistoryButton;
    private JPanel historyPanel;
    private JTextField historyAccountTextField;
    private JTextField transferTitleTextField;
    private JTextField transferAmountTextField;

    private BankPortType bankPort;

    public MainWindowForm(List<String> accounts) {

        DefaultListModel<String> model = new DefaultListModel<>();
        accountList.setModel(model);

        for (String accountNo :
                accounts) {
            model.addElement(accountNo);
        }

        BankPortTypeService service = new BankPortTypeService();
        service.setHandlerResolver(new CustomHeaderHandler());
        bankPort = service.getBankPortTypeSoap11();

        getHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryRequest request = new HistoryRequest();
                request.setAccountNumber(historyAccountTextField.getText());
                HistoryResponse response = null;
                try {
                    response = bankPort.history(request);
                    StringBuilder historyString = new StringBuilder();

                    for (HistoryEntry entry :
                            response.getHistory()) {
                        historyString.append("[Source: ").append(entry.getSource()).append(" , title: ").append(entry.getTitle()).append(" , amount: ").append(entry.getAmount()).append(", balance after: ").append(entry.getBalance()).append("]");
                    }
                    JOptionPane.showMessageDialog(null, historyString.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }


            }
        });
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentRequest request = new PaymentRequest();
                PaymentResponse response = null;
                try {
                    int amount = (int) (Float.parseFloat(paymentAmountTextField.getText()) * 100);
                    request.setAccount(paymentAccountTextField.getText());
                    request.setAmount(amount);
                    response = bankPort.payment(request);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, response.getMessage());
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WithdrawRequest request = new WithdrawRequest();
                WithdrawResponse response = null;
                try {
                    int amount = (int) (Float.parseFloat(withdrawAmountTextField.getText()) * 100);
                    request.setAccount(withdrawAccountTextField.getText());
                    request.setAmount(amount);
                    response = bankPort.withdraw(request);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, response.getMessage());
            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferRequest request = new TransferRequest();
                TransferResponse response = null;
                try {
                    int amount = (int) (Float.parseFloat(transferAmountTextField.getText()) * 100);
                    request.setAmount(amount);
                    request.setTitle(transferTitleTextField.getText());
                    request.setSourceAccount(transferFromTextField.getText());
                    request.setDestinationAccount(transferToTextField.getText());
                    response = bankPort.transfer(request);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, response.getMessage());
            }
        });
    }
}
