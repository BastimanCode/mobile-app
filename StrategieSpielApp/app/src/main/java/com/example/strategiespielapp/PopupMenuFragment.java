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

public class PopupMenuFragment extends Fragment implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

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
            case R.id.research:
                Intent researchIntent = new Intent(getActivity(), ResearchActivity.class);
                startActivity(researchIntent);
                return true;
            case R.id.units:
                Intent unitsIntent = new Intent(getActivity(), UnitsActivity.class);
                startActivity(unitsIntent);
                return true;
            case R.id.fleets:
                Intent fleetsIntent = new Intent(getActivity(), FleetsActivity.class);
                startActivity(fleetsIntent);
                return true;
            case R.id.galaxy:
                Intent galaxyIntent = new Intent(getActivity(), GalaxyActivity.class);
                startActivity(galaxyIntent);
                return true;
            default:
                return false;
        }
    }
  /*  @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

                material.setText(String.valueOf(player.metall));
                computerchips.setText(String.valueOf(player.platinen));
                fuel.setText(String.valueOf(player.treibstoff));
            }
        });
        get.execute("http://192.168.0.80:8000/?type=refresh&playerid=1&planetid=1");
    }*/
}