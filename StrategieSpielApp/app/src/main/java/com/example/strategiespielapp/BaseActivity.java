package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    String ip = "192.168.178.25";

    public BaseActivity() {}

    public String getIp() {
        return ip;
    }
}
