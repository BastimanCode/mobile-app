package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    String ip = "192.168.0.80";

    public BaseActivity() {}

    public String getIp() {
        return ip;
    }
}
