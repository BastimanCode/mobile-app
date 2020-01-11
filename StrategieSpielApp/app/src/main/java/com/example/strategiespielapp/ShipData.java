package com.example.strategiespielapp;

public class ShipData {
    String id;
    String name;
    int hitpoints;
    int attack;
    int firerate;
    int shield;
    int material;
    int electronics;
    int fuel;
    String description;

    public ShipData(String id, String name, int hitpoints, int attack, int firerate, int shield, int material, int electronics, int fuel, String description) {
        this.id = id;
        this.name = name;
        this.hitpoints = hitpoints;
        this.attack = attack;
        this.firerate = firerate;
        this.shield = shield;
        this.material = material;
        this.electronics = electronics;
        this.fuel = fuel;
        this.description = description;
    }
}
