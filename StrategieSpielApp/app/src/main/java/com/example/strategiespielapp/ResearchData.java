package com.example.strategiespielapp;

public class ResearchData {
    String name;
    int level;
    int bonus;
    int material;
    int electronics;
    int fuel;
    String description;

    public ResearchData(String name, int level, int bonus, int material, int electronics, int fuel, String description) {
        this.name = name;
        this.level = level;
        this.bonus = bonus;
        this.material = material;
        this.electronics = electronics;
        this.fuel = fuel;
        this.description = description;
    }
}
