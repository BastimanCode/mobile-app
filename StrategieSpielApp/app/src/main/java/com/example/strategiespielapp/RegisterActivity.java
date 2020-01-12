package com.example.strategiespielapp;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    TextView email;
    TextView username;
    TextView password;
    String json;
    Context c = this;

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
    public void onClick(View view) {
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
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();

                        if (result.equals("ERROR")) {
                            alertDialog.setTitle("Registrieren");
                            alertDialog.setMessage("Registrieren fehlgeschlagen!");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            int start = result.lastIndexOf('[');
                            int end = result.indexOf(']') + 1;
                            result = result.substring(start, end);

                            if (result.equals("[]")) {
                                alertDialog.setTitle("Login");
                                alertDialog.setMessage("Login fehlgeschlagen!");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {


                                Gson gson = new GsonBuilder().create();
                                Account[] accounts = gson.fromJson(result, Account[].class);
                                Account account = accounts[0];

                                String fileName = "accountData.json";
                                String fileContent = gson.toJson(account);
                                try {
                                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    fos.write(fileContent.getBytes());
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                alertDialog.setTitle("Regisrieren");
                                alertDialog.setMessage("Registrieren Erfolgreich!");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent mainIntent = new Intent(c, MainActivity.class);
                                                startActivity(mainIntent);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    }
                });
                post.execute("http://" + ip + ":8000/?type=register", json);
                break;
            case R.id.buttonBack:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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
