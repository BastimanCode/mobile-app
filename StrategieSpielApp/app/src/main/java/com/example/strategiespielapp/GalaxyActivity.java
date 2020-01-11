package com.example.strategiespielapp;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class GalaxyActivity extends BaseActivity {

    private ArrayList<String> mnames = new ArrayList<>();
    private ArrayList<Integer> msize = new ArrayList<>();
    private ArrayList<Integer> mtemp = new ArrayList<>();
    private ArrayList<String> mcoords = new ArrayList<>();
    private ArrayList<String> maccount = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);

        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Gson gson = new GsonBuilder().create();
                AccountPlanet[] planets = gson.fromJson(result, AccountPlanet[].class);
                for (int i = 0; i < planets.length; i++) {
                    mnames.add(planets[i].name);
                    maccount.add(planets[i].username);
                    msize.add(planets[i].size);
                    mtemp.add(planets[i].temperature);
                    mcoords.add(planets[i].x + ", " + planets[i].y);

                    mmaterial.add(planets[i].material);
                    melectronics.add(planets[i].electronics);
                    mfuel.add(planets[i].fuel);

                    initRecyclerView();
                }
            }
        });
        get.execute("http://" + ip + ":8000/?type=planets");
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapterGalaxy adapter = new RecyclerViewAdapterGalaxy(this, mnames, msize, mtemp, mcoords, maccount, mmaterial, melectronics, mfuel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
