package com.example.strategiespielapp;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ResearchActivity extends BaseActivity {

    int playerID;
    int planetID;

    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<Integer> mLevels = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();
    private ArrayList<Integer> mImageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Forschung");

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
                Research[] researches = gson.fromJson(result, Research[].class);
                Research research = researches[0];

                mLevels.add(research.industry);
                mLevels.add(research.laser);
                mLevels.add(research.logistics);
                mLevels.add(research.engine);
                mLevels.add(research.weapon);
                mLevels.add(research.shield);
                mLevels.add(research.armor);
            }
        });
        get.execute("http://" + ip + ":8000/?type=research&playerid=" + playerID);

        HttpGetRequest researchdata = new HttpGetRequest();
        researchdata.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                ResearchData[] researchData = gson.fromJson(result, ResearchData[].class);
                for (int i = 0;i < 7; i++){

                    mHeadlines.add(researchData[i * 11 + 1].name);
                    mmaterial.add(researchData[i * 11 + mLevels.get(i)].material);
                    melectronics.add(researchData[i * 11 + mLevels.get(i)].electronics);
                    mfuel.add(researchData[i * 11 + mLevels.get(i)].fuel);
                    mDescriptions.add(researchData[i * 11 + mLevels.get(i)].description);

                    initImageBitmaps();

                }

            }
        });
        researchdata.execute("http://" + ip + ":8000/?type=researches");

    }

    private void initImageBitmaps() {
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
        RecyclerViewAdapterResearch adapter = new RecyclerViewAdapterResearch(this, mImageURLs, mHeadlines, mDescriptions, mLevels, mmaterial, melectronics, mfuel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onBackPressed() {
        Intent overviewIntent = new Intent(this, MainActivity.class);
        startActivity(overviewIntent);
    }
}
