package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView email;
    TextView username;
    TextView password;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.enterEmail);
        username = findViewById(R.id.enterUsername);
        password = findViewById(R.id.enterPassword);

        Button b = findViewById(R.id.buttonRegister);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                String emailInput = email.getText().toString();
                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();
                json = "{ \"email\": \"" + emailInput + "\", \"username\": \"" + usernameInput + "\", \"password\": \"" + passwordInput + "\" }";

                HttpPostRequest post = new HttpPostRequest();
                post.setUpdateListener(new HttpPostRequest.OnUpdateListener() {
                    @Override
                    public void onUpdate(String result) {

                        //NUR STELLE 1 DES ARRAY BEARBEITEN (0 IST DATENBANK STATUSMELDUNG)
                        Gson gson = new GsonBuilder().create();
                        Account[] accounts = gson.fromJson(result, Account[].class);
                        Account account = accounts[1];

                        String fileName = "accountData.json";
                        String fileContent = gson.toJson(account);
                        try {
                            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                            fos.write(fileContent.getBytes());
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                post.execute("http://192.168.178.25:8000/?type=register", json);
                break;
            case R.id.buttonBack:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}
