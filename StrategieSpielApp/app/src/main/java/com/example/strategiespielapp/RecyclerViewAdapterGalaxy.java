package com.example.strategiespielapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RecyclerViewAdapterGalaxy extends RecyclerView.Adapter<RecyclerViewAdapterGalaxy.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterGalaxy";

    private int playerID;
    private int planetID;

    private Context mContext;
    private ArrayList<String> mnames = new ArrayList<>();
    private ArrayList<Integer> msize = new ArrayList<>();
    private ArrayList<Integer> mtemp = new ArrayList<>();
    private ArrayList<String> mcoords = new ArrayList<>();
    private ArrayList<String> maccount = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();

    public RecyclerViewAdapterGalaxy(Context mContext, ArrayList<String> mnames, ArrayList<Integer> msize, ArrayList<Integer> mtemp, ArrayList<String> mcoords, ArrayList<String> maccount, ArrayList<Integer> mmaterial, ArrayList<Integer> melectronics, ArrayList<Integer> mfuel) {
        this.mContext = mContext;
        this.mnames = mnames;
        this.msize = msize;
        this.mtemp = mtemp;
        this.mcoords = mcoords;
        this.maccount = maccount;
        this.mmaterial = mmaterial;
        this.melectronics = melectronics;
        this.mfuel = mfuel;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterGalaxy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.galaxy_listitem,parent, false);
        RecyclerViewAdapterGalaxy.ViewHolder holder = new RecyclerViewAdapterGalaxy.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterGalaxy.ViewHolder holder, final int position) {
        holder.name.setText(mnames.get(position));
        holder.account.setText(maccount.get(position));
        holder.size.setText("Größe: " + msize.get(position) + " km²");
        holder.temp.setText("Temperatur: " + mtemp.get(position) + "°C");
        holder.coords.setText("Position: " + mcoords.get(position));

        holder.resource1.setText(String.valueOf(mmaterial.get(position)));
        holder.resource2.setText(String.valueOf(melectronics.get(position)));
        holder.resource3.setText(String.valueOf(mfuel.get(position)));

        holder.resourceImage1.setImageResource(R.drawable.brick1980_1920);
        holder.resourceImage2.setImageResource(R.drawable.processor2217771_1920);
        holder.resourceImage3.setImageResource(R.drawable.oil696579_1920);

        holder.attack.setText(R.string.attack);
        holder.attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpGetRequest get = new HttpGetRequest();
                get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
                    @Override
                    public void onUpdate(String result) {
                        Intent galaxyIntent = new Intent(mContext, GalaxyActivity.class);
                        mContext.startActivity(galaxyIntent);
                    }
                });
                String fileName = "accountData.json";
                try {
                    FileInputStream fis = mContext.openFileInput(fileName);
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
                        AccountPlanet account = gson.fromJson(content, AccountPlanet.class);
                        playerID = account.accountId;
                        planetID = account.planetId;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                get.execute("http://" + new BaseActivity().ip + ":8000/?type=attack&playerid=" + playerID + "&planetid=" + planetID + "&defenderid=" + maccount.get(position));
                // Dialog öffnen mit entweder win oder lose
            }
        });
    }

    @Override
    public int getItemCount() {
        return mnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView account;
        TextView size;
        TextView temp;
        TextView coords;
        Button attack;
        ImageView galaxyImage;
        RelativeLayout parentlayout;

        TextView resource1;
        TextView resource2;
        TextView resource3;

        ImageView resourceImage1;
        ImageView resourceImage2;
        ImageView resourceImage3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            account = itemView.findViewById(R.id.account);
            size = itemView.findViewById(R.id.planetSize);
            temp = itemView.findViewById(R.id.planetTemp);
            coords = itemView.findViewById(R.id.planetCoords);
            attack = itemView.findViewById(R.id.attack);
            galaxyImage = itemView.findViewById(R.id.galaxyImage);
            parentlayout = itemView.findViewById(R.id.listitemresearch);
            resource1 = itemView.findViewById(R.id.resource1);
            resource2 = itemView.findViewById(R.id.resource2);
            resource3 = itemView.findViewById(R.id.resource3);
            resourceImage1 = itemView.findViewById(R.id.resource1image);
            resourceImage2 = itemView.findViewById(R.id.resource2image);
            resourceImage3 = itemView.findViewById(R.id.resource3image);
        }
    }
}
