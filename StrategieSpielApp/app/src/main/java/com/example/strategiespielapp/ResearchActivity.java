package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ResearchActivity extends AppCompatActivity {

    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<String> mLevels = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add("Stufe: 0");
        mDescriptions.add("Labor bauen");

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        mImageURLs.add(R.drawable.abstract1963838_1920);
        mImageURLs.add(R.drawable.abstract1963838_1920);
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
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mImageURLs, mHeadlines, mDescriptions, mLevels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
