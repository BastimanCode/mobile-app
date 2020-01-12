package com.example.strategiespielapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class DefenseActivity extends BaseActivity {

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
        setContentView(R.layout.activity_defense);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Verteidigung");

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

        HttpGetRequest defense = new HttpGetRequest();
        defense.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                AccountPlanet[] accountPlanets = gson.fromJson(result,AccountPlanet[].class);

                mAmounts.add(accountPlanets[0].rocketlauncher);
                mAmounts.add(accountPlanets[0].lasergun);
                mAmounts.add(accountPlanets[0].iongun);
                mAmounts.add(accountPlanets[0].shockwavecannon);
                mAmounts.add(accountPlanets[0].plasmacannon);
                mAmounts.add(accountPlanets[0].antimatterradiator);
                mAmounts.add(accountPlanets[0].spacemines);
                mAmounts.add(accountPlanets[0].planetshield);
            }
        });
        defense.execute("http://" + ip + ":8000/?type=refresh&planetid=" + planetID + "&playerid=" + playerID);

        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                ShipData[] defenses = gson.fromJson(result, ShipData[].class);
                for (int i = 0; i < 8; i++) {
                    mHeadlines.add(defenses[i].name);
                    mmaterial.add(defenses[i].material);
                    melectronics.add(defenses[i].electronics);
                    mfuel.add(defenses[i].fuel);
                    mDescriptions.add(defenses[i].description);
                }
                initImageBitmaps();
            }
        });
        get.execute("http://" + ip + ":8000/?type=defensedata");
    }

    private void initImageBitmaps() {
        mImageURLs.add(R.drawable.rocket);
        mImageURLs.add(R.drawable.laser);
        mImageURLs.add(R.drawable.ionen);
        mImageURLs.add(R.drawable.shockwave);
        mImageURLs.add(R.drawable.plasma);
        mImageURLs.add(R.drawable.antimatter);
        mImageURLs.add(R.drawable.spacemine);
        mImageURLs.add(R.drawable.shield);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterDefense adapter = new RecyclerViewAdapterDefense(this, mImageURLs, mHeadlines, mDescriptions, mAmounts, mmaterial, melectronics, mfuel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
