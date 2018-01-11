package com.example.bsrbank.model;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class Transaction {

    public Transaction(int amount, String title, String sourceAccountNumber, String destinationAccountNumber, Account account) {
        this.amount = amount;
        this.title = title;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.account = account;
    }

    public Transaction(int amount, String title, String sourceAccountNumber, String destinationAccountNumber) {
        this.amount = amount;
        this.title = title;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.account = null;
    }

    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amount;

    private String title;

    private String sourceAccountNumber;

    private String destinationAccountNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_number", referencedColumnName = "no")
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
