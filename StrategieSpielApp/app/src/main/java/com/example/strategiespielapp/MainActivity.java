package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                AccountPlanet[] accountPlanet = gson.fromJson(result,AccountPlanet[].class);
                AccountPlanet player = accountPlanet[0];

                TextView size = findViewById(R.id.a_size);
                TextView planetname = findViewById(R.id.planetname);
                TextView temperature= findViewById(R.id.a_temperature);
                TextView position = findViewById(R.id.a_position);
                TextView points = findViewById(R.id.a_points);
                TextView user = findViewById(R.id.a_user);

                size.setText(String.valueOf(player.size));
                temperature.setText(String.valueOf(player.temperatur));
                position.setText(player.x + ", " + player.y);
                points.setText("212");
                user.setText(player.username);
            }
        });
        get.execute("http://192.168.0.80:8000/?type=refresh&playerid=1&planetid=1");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

