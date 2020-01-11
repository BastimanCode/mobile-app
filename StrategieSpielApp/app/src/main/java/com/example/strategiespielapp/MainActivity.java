package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    int playerID;
    int planetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView test = findViewById(R.id.test);

        //bessere Altenative: File.createTempfile("accountData", "json", context.getCacheDir()) ?
        //Nur zum testen
        String fileName = "accountData.json";
        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                String content = stringBuilder.toString();
                Gson gson = new GsonBuilder().create();
                AccountPlanet account = gson.fromJson(content, AccountPlanet.class);
                playerID = account.accountId;
                planetID = account.planetId;
                test.setText(playerID + ", " + planetID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                AccountPlanet[] accountPlanet = gson.fromJson(result,AccountPlanet[].class);
                AccountPlanet player = accountPlanet[0];

                TextView size = findViewById(R.id.a_size);
                TextView planetname = findViewById(R.id.planetname);
                TextView temperature = findViewById(R.id.a_temperature);
                TextView position = findViewById(R.id.a_position);
                TextView points = findViewById(R.id.a_points);
                TextView user = findViewById(R.id.a_user);

                planetname.setText(player.name);
                size.setText(player.size + " Millionen km²");
                temperature.setText(player.temperature + "°C");

                position.setText(player.x + ", " + player.y);
                points.setText("0");
                user.setText(player.username);
            }
        });
        get.execute("http://192.168.0.80:8000/?type=refresh&playerid=" + "1" + "&planetid=" + "1");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

