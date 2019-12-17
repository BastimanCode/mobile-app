package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = findViewById(R.id.test);

        //bessere Altenative: File.createTempfile("accountData", "json", context.getCacheDir()) ?
        String fileName = "accountData.json";
        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
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
                test.setText(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
