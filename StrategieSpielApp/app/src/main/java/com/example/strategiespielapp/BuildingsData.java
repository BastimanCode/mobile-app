package com.example.strategiespielapp;

public class BuildingsData {
    String name;
    int level;
    int material;
    int electronics;
    int fuel;
    int output;

    public BuildingsData(String name, int level, int material, int electronics, int fuel, int output) {
        this.name = name;
        this.level = level;
        this.material = material;
        this.electronics = electronics;
        this.fuel = fuel;
        this.output = output;
    }
}
