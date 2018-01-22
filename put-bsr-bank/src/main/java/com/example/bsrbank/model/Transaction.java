package com.example.bsrbank.model;

import com.bank.types.HistoryEntry;
import com.bank.types.TransferRequest;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class Transaction {

    public Transaction(int amount, String title, String source, int balance, Account account) {
        this.amount = amount;
        this.title = title;
        this.source = source;
        this.balance = balance;
        this.account = account;
    }

    public Transaction(int amount, String title, String source) {
        this.amount = amount;
        this.title = title;
        this.source = source;
        this.balance = 0;
        this.account = null;
    }

    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amount;

    private String title;

    private String source;

    private int balance;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public static HistoryEntry toHistoryEntry(final Transaction transaction) {
        HistoryEntry entry = new HistoryEntry();
        entry.setAmount(transaction.amount);
        entry.setBalance(transaction.balance);
        entry.setSource(transaction.source);
        entry.setTitle(transaction.title);
        return entry;
    }

}
