package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UnitsActivity extends AppCompatActivity {

    int playerID;
    int planetID;

    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<Integer> mAmounts = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();
    private ArrayList<Integer> mImageURLs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Einheiten");

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

        HttpGetRequest ships = new HttpGetRequest();
        ships.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                AccountPlanet[] accountPlanets = gson.fromJson(result,AccountPlanet[].class);

                mAmounts.add(accountPlanets[0].scout);
                mAmounts.add(accountPlanets[0].hunter);
                mAmounts.add(accountPlanets[0].cruiser);
                mAmounts.add(accountPlanets[0].battleship);
                mAmounts.add(accountPlanets[0].destroyer);
                mAmounts.add(accountPlanets[0].bomber);
                mAmounts.add(accountPlanets[0].mothership);
                mAmounts.add(accountPlanets[0].colonisationship);
            }
        });
        ships.execute("http://192.168.0.80:8000/?type=refresh&planetid=" + planetID + "&playerid=" + playerID);

        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                ShipData[] shipdata = gson.fromJson(result, ShipData[].class);
                for (int i = 0; i < 8; i++) {
                    mHeadlines.add(shipdata[i].name);
                    mmaterial.add(shipdata[i].material);
                    melectronics.add(shipdata[i].electronics);
                    mfuel.add(shipdata[i].fuel);
                    mDescriptions.add(shipdata[i].description);
                }
                initImageBitmaps();
            }
        });
        get.execute("http://192.168.0.80:8000/?type=shipdata");
    }

    private void initImageBitmaps() {
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterUnits adapter = new RecyclerViewAdapterUnits(this, mImageURLs, mHeadlines, mDescriptions, mAmounts, mmaterial, melectronics, mfuel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Einheiten");
    }

    @Override
    public void onBackPressed() {
        Intent overviewIntent = new Intent(this, MainActivity.class);
        startActivity(overviewIntent);
    }
}
