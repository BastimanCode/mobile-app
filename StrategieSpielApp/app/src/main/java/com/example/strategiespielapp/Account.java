package com.example.strategiespielapp;

public class Account {
    int id;
    String email;
    String username;
    String password;
    int lastOnline;

    public Account(int id, String email, String username, String password, int lastOnline) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.lastOnline = lastOnline;
    }
}