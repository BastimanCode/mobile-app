package com.example.strategiespielapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Übersicht");

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

                Account account = gson.fromJson(content, Account.class);
                playerID = account.id;
                planetID = account.planet_id;
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

        get.execute("http://" + ip + ":8000/?type=refresh&playerid=" + playerID + "&planetid=" + planetID);
        setSupportActionBar(toolbar);

        get.execute("http://" + ip + ":8000/?type=refresh&playerid=" + playerID + "&planetid=" + planetID);
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Beenden");
        alertDialog.setMessage("Soll das Spiel beendet werden?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "JA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NEIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}

