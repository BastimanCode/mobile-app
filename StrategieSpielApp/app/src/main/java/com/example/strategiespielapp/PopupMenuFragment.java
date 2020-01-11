package com.example.strategiespielapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PopupMenuFragment extends Fragment implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    int playerID;
    int planetID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_popup_menu, container, false);

        Button b = (Button) v.findViewById(R.id.menu_button);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        showPopup(view);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.navigation_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.overview:
                Intent overviewIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(overviewIntent);
                return true;
            case R.id.resources:
                Intent resourcesIntent = new Intent(getActivity(), ResourcesActivity.class);
                startActivity(resourcesIntent);
                return true;
            case R.id.buildings:
                Intent buildingsIntent = new Intent(getActivity(), BuildingsActivity.class);
                startActivity(buildingsIntent);
                return true;
            case R.id.attack:
                Intent researchIntent = new Intent(getActivity(), ResearchActivity.class);
                startActivity(researchIntent);
                return true;
            case R.id.units:
                Intent unitsIntent = new Intent(getActivity(), UnitsActivity.class);
                startActivity(unitsIntent);
                return true;
            case R.id.defense:
                Intent defenseIntent = new Intent(getActivity(), DefenseActivity.class);
                startActivity(defenseIntent);
                return true;
            case R.id.galaxy:
                Intent galaxyIntent = new Intent(getActivity(), GalaxyActivity.class);
                startActivity(galaxyIntent);
                return true;
            case R.id.logout:
                getContext().deleteFile("accountData.json");
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
            default:
                return false;
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String fileName = "accountData.json";
        try {
            FileInputStream fis = getContext().openFileInput(fileName);
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
                AccountPlanet[] accountPlanet = gson.fromJson(result,AccountPlanet[].class);
                AccountPlanet player = accountPlanet[0];

                TextView material = getView().findViewById(R.id.material);
                TextView computerchips = getView().findViewById(R.id.computerchips);
                TextView fuel = getView().findViewById(R.id.fuel);

                material.setText(String.valueOf(player.material));
                computerchips.setText(String.valueOf(player.electronics));
                fuel.setText(String.valueOf(player.fuel));
            }
        });
        get.execute("http://" + new BaseActivity().ip + ":8000/?type=refresh&playerid=" + playerID + "&planetid=" + planetID);
    }
}