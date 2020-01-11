package com.example.strategiespielapp;

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

public class MainActivity extends BaseActivity {

    int playerID;
    int planetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
<<<<<<< HEAD
                AccountPlanet account = gson.fromJson(content, AccountPlanet.class);
                playerID = account.accountId;
                planetID = account.planetId;
                test.setText(playerID + ", " + planetID);
=======
                Account account = gson.fromJson(content, Account.class);
                playerID = account.id;
                planetID = account.planet_id;
>>>>>>> d7b71c16a0b61fc3cf125d64b213df46db7679d5
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
                size.setText(player.size + " Mio km²");
                temperature.setText(player.temperature + "°C");

                position.setText(player.x + ", " + player.y);
                points.setText("0");
                user.setText(player.username);
            }
        });
<<<<<<< HEAD
        get.execute("http://192.168.0.80:8000/?type=refresh&playerid=" + "1" + "&planetid=" + "1");
=======
        get.execute("http://" + ip + ":8000/?type=refresh&playerid=" + playerID + "&planetid=" + planetID);
>>>>>>> d7b71c16a0b61fc3cf125d64b213df46db7679d5

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}

