package com.example.strategiespielapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    String ip = "192.168.178.25";

    public BaseActivity() {}

    public String getIp() {
        return ip;
    }
}
