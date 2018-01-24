package com.bank.client;

public class CredentialsManager {
    private static CredentialsManager instance;

    private String username;
    private String password;

    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }

        return instance;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
