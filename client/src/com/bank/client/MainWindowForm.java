package com.bank.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindowForm {
    private JPanel panel1;
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

    public MainWindowForm() {
        getHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
