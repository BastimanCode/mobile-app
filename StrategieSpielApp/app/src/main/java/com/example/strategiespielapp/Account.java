package com.example.strategiespielapp;

public class Account {
    int id;
    String email;
    String username;
    String password;
    long last_online;
    int planet_id;

    public Account(int id, String email, String username, String password, long last_online, int planet_id) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.last_online = last_online;
        this.planet_id = planet_id;
    }
}