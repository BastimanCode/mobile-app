package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ResearchActivity extends AppCompatActivity {

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
        get.execute("http://192.168.0.80:8000/?type=research&playerid=1");

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
                    mDescriptions.add("hi" + i);

                    initImageBitmaps();

                }

            }
        });
        researchdata.execute("http://192.168.0.80:8000/?type=researches");

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
}
