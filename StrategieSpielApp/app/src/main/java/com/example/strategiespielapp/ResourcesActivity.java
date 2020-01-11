package com.example.strategiespielapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ResourcesActivity extends BaseActivity {

    int playerID;
    int planetID;

    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<Integer> mLevels = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImageURLs = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

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
                AccountPlanet[] accountPlanet = gson.fromJson(result, AccountPlanet[].class);
                AccountPlanet accountPlanet1 = accountPlanet[0];

                mLevels.add(accountPlanet1.mine);
                mLevels.add(accountPlanet1.factory);
                mLevels.add(accountPlanet1.oilrig);
                mLevels.add(accountPlanet1.materialstorage);
                mLevels.add(accountPlanet1.electronicstorage);
                mLevels.add(accountPlanet1.fueltank);
            }
        });


        get.execute("http://" + ip + ":8000/?type=refresh&playerid=" + playerID + "&planetid=" + planetID);

        HttpGetRequest resourcedata = new HttpGetRequest();
        resourcedata.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                BuildingsData[] buildingsdata = gson.fromJson(result, BuildingsData[].class);
                for (int i = 0; i < 6; i++) {
                    mHeadlines.add(buildingsdata[i * 26 + 1].name);
                    mmaterial.add(buildingsdata[i * 26 + mLevels.get(i)].material);
                    melectronics.add(buildingsdata[i * 26 + mLevels.get(i)].electronics);
                    mfuel.add(buildingsdata[i * 26 + mLevels.get(i)].fuel);
                    mDescriptions.add("Beschreibung: " + i);

                    initImageBitmaps();

                }

            }
        });
        resourcedata.execute("http://" + ip + ":8000/?type=productions");

    }

    private void initImageBitmaps() {
        mImageURLs.add(R.drawable.mineoutside);
        mImageURLs.add(R.drawable.factory);
        mImageURLs.add(R.drawable.oilrig);
        mImageURLs.add(R.drawable.materialstorage);
        mImageURLs.add(R.drawable.electronicsstorage);
        mImageURLs.add(R.drawable.fueltank);


        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterResources adapter = new RecyclerViewAdapterResources(this, mImageURLs, mHeadlines, mDescriptions, mLevels, mmaterial, melectronics, mfuel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
