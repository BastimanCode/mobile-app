package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FleetsActivity extends AppCompatActivity {

    int playerID;
    int planetID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleets);

        Button button = findViewById(R.id.button);

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpGetRequest get = new HttpGetRequest();
                get.execute("http://192.168.0.80:8000/?type=attack&destination=2&playerid=" + playerID + "&planetid=" + planetID + "&scout=0&hunter=200&cruiser=0&battleship=0&destroyer=100&bomber=0&mothership=20&colonisationship=0");
            }
        });
    }
}
