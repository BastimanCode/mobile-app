package com.example.strategiespielapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView email;
    TextView password;
    String json;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.enterEmail);
        password = findViewById(R.id.enterPassword);
        Button login = findViewById(R.id.buttonLogin);
        Button register = findViewById(R.id.buttonRegister);

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

                HttpPostRequest post = new HttpPostRequest();
                post.setUpdateListener(new HttpPostRequest.OnUpdateListener() {
                    @Override
                    public void onUpdate(String result) {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

                        if(result.equals("ERROR")) {
                            alertDialog.setTitle("Login");
                            alertDialog.setMessage("Login fehlgeschlagen!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        } else {
                            alertDialog.setTitle("Login");
                            alertDialog.setMessage("Login Erfolgreich!");
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
                });
                post.execute("http://192.168.0.80:8000/?type=login", content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();
                json = "{ \"email\": \"" + emailS + "\", \"password\": \"" + passwordS + "\" }";
                //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

                HttpPostRequest post = new HttpPostRequest();
                post.setUpdateListener(new HttpPostRequest.OnUpdateListener() {
                    @Override
                    public void onUpdate(String result) {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();

                        if(result.equals("ERROR")) {
                            alertDialog.setTitle("Login");
                            alertDialog.setMessage("Login fehlgeschlagen!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
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

                            Gson gson = new GsonBuilder().create();
                            Account[] accounts = gson.fromJson(result, Account[].class);
                            Account account = accounts[0];

                            //bessere Alternative: createTempfile("accountData", "json", context.getCacheDir()) ?
                            String fileName = "accountData.json";
                            String fileContent = gson.toJson(account);
                            try {
                                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                fos.write(fileContent.getBytes());
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            alertDialog.setTitle("Login");
                            alertDialog.setMessage("Login Erfolgreich!");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent mainIntent = new Intent(c, GalaxyActivity.class);
                                            startActivity(mainIntent);
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
                post.execute("http://192.168.0.80:8000/?type=login", json);


                break;
            case R.id.buttonRegister:
                Intent registerIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            default:
                break;
        }
    }

}
