package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FleetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleets);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpGetRequest get = new HttpGetRequest();
                get.execute("http://192.168.0.80:8000/?type=attack&destination=2&playerid=1&planetid=1&scout=0&hunter=0&cruiser=0&battleship=0&destroyer=75&bomber=0&mothership=0&colonisationship=0");
            }
        });
    }
}
