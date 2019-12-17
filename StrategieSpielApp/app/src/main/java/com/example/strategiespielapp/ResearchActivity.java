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
                Forschung[] forschungen = gson.fromJson(result, Forschung[].class);
                Forschung forschung = forschungen[0];

                mHeadlines.add("Forschung 1");
                mLevels.add(forschung.forschung1);
                mDescriptions.add("labor bauen");

                mHeadlines.add("Forschung 2");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 3");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 4");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 5");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 6");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 7");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 8");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                mHeadlines.add("Forschung 9");
                mLevels.add(1);
                mDescriptions.add("Labor bauen");

                initImageBitmaps();
            }
        });
        get.execute("http://192.168.0.80:8000/?type=research&playerid=1");
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
        mImageURLs.add(R.drawable.abstract1963838_1920);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterResearch adapter = new RecyclerViewAdapterResearch(this, mImageURLs, mHeadlines, mDescriptions, mLevels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
