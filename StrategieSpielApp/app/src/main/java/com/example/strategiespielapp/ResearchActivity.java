package com.example.strategiespielapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

<<<<<<< HEAD
=======
import com.google.gson.Gson;
>>>>>>> b769b72f1b245fb142f0195369f71b0dd99127f1
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
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
                Forschung[] forschungen = gson.fromJson(result, Forschung[].class);
                Forschung forschung = forschungen[0];

                mHeadlines.add("Forschung 1");
                mLevels.add(forschung.forschung1);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 2");
                mLevels.add(forschung.forschung2);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 3");
                mLevels.add(forschung.forschung3);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 4");
<<<<<<< HEAD
                mLevels.add("Stufe: " + forschung.forschung1);
=======
                mLevels.add(forschung.forschung1);
>>>>>>> b769b72f1b245fb142f0195369f71b0dd99127f1
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 5");
                mLevels.add(forschung.forschung2);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 6");
                mLevels.add(forschung.forschung3);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 7");
<<<<<<< HEAD
                mLevels.add("Stufe: " + forschung.forschung1);
=======
                mLevels.add(forschung.forschung1);
>>>>>>> b769b72f1b245fb142f0195369f71b0dd99127f1
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 8");
                mLevels.add(forschung.forschung2);
                mDescriptions.add("Labor bauen");
                mHeadlines.add("Forschung 9");
                mLevels.add(forschung.forschung3);
                mDescriptions.add("Labor bauen");
                initImageBitmaps();
            }
        });
        get.execute("http://192.168.178.25:8000/?type=research");
<<<<<<< HEAD

        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");
        mHeadlines.add("Forschung 1");
        mLevels.add(0);
        mDescriptions.add("Labor bauen");

        initImageBitmaps();
=======
>>>>>>> b769b72f1b245fb142f0195369f71b0dd99127f1
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
