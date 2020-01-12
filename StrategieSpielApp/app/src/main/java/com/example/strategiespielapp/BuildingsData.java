package com.example.strategiespielapp;

public class BuildingsData {
    String name;
    int level;
    int material;
    int electronics;
    int fuel;
    int output;
    String description;

    public BuildingsData(String name, int level, int material, int electronics, int fuel, int output, String description) {
        this.name = name;
        this.level = level;
        this.material = material;
        this.electronics = electronics;
        this.fuel = fuel;
        this.output = output;
        this.description = description;
    }
}
