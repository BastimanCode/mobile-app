package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

public class UnitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Einheiten");
    }

    @Override
    public void onBackPressed() {
        Intent overviewIntent = new Intent(this, MainActivity.class);
        startActivity(overviewIntent);
    }
}
