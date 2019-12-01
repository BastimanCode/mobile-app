package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class BuildingsActivity extends AppCompatActivity {

    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<Integer> mLevels = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);

        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");
        mHeadlines.add("Gebäude ");
        mLevels.add(0);
        mDescriptions.add("Ausbauen um xy zu erhalten");


        initImageBitmaps();
    }

    private void initImageBitmaps(){
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);
        mImageURLs.add(R.drawable.powerplant4349830_1920);


        initRecyclerView();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterBuildings adapter = new RecyclerViewAdapterBuildings(this,mImageURLs, mHeadlines, mDescriptions, mLevels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
