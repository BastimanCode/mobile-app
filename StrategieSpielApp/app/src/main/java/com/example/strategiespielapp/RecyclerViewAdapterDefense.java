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


public class RecyclerViewAdapterDefense extends RecyclerView.Adapter<RecyclerViewAdapterDefense.ViewHolder>{

    int playerID;
    int planetID;

    private static final String TAG = "RecyclerViewAdapterDefense";

    private Context mContext;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mAmounts = new ArrayList<>();
    private ArrayList<Integer> mMaterial = new ArrayList<>();
    private ArrayList<Integer> mElectronics = new ArrayList<>();
    private ArrayList<Integer> mFuel = new ArrayList<>();

    public RecyclerViewAdapterDefense(Context mContext, ArrayList<Integer> mImages, ArrayList<String> mHeadlines, ArrayList<String> mDescriptions, ArrayList<Integer> mAmounts, ArrayList<Integer> mMaterial, ArrayList<Integer> mElectronics, ArrayList<Integer> mFuel) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mHeadlines = mHeadlines;
        this.mDescriptions = mDescriptions;
        this.mAmounts = mAmounts;
        this.mMaterial = mMaterial;
        this.mElectronics = mElectronics;
        this.mFuel = mFuel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.units_listitem,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.unitsimage.setImageResource(mImages.get(position));

        holder.headline.setText(mHeadlines.get(position));
        holder.description.setText(mDescriptions.get(position));
        holder.amount.setText(String.valueOf(mAmounts.get(position)));

        holder.resource1cost.setText(String.valueOf(mMaterial.get(position)));
        holder.resource2cost.setText(String.valueOf(mElectronics.get(position)));
        holder.resource3cost.setText(String.valueOf(mFuel.get(position)));

        holder.resourceImage1.setImageResource(R.drawable.brick1980_1920);
        holder.resourceImage2.setImageResource(R.drawable.processor2217771_1920);
        holder.resourceImage3.setImageResource(R.drawable.oil696579_1920);



        holder.buildTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build(10,position);
            }
        });
        holder.buildFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build(5,position);
            }
        });
        holder.buildOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build(1,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeadlines.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView headline;
        TextView description;
        TextView amount;
        Button buildTen;
        Button buildFive;
        Button buildOne;
        ImageView unitsimage;
        RelativeLayout parentlayout;

        TextView resource1cost;
        TextView resource2cost;
        TextView resource3cost;

        ImageView resourceImage1;
        ImageView resourceImage2;
        ImageView resourceImage3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            amount = itemView.findViewById(R.id.amount);
            buildTen = itemView.findViewById(R.id.build);
            buildFive = itemView.findViewById(R.id.build2);
            buildOne = itemView.findViewById(R.id.build3);

            unitsimage = itemView.findViewById(R.id.UnitImage);
            parentlayout = itemView.findViewById(R.id.listitemunits);
            resource1cost = itemView.findViewById(R.id.resource1cost);
            resource2cost = itemView.findViewById(R.id.resource2cost);
            resource3cost = itemView.findViewById(R.id.resource3cost);
            resourceImage1 = itemView.findViewById(R.id.resource1image);
            resourceImage2 = itemView.findViewById(R.id.resource2image);
            resourceImage3 = itemView.findViewById(R.id.resource3image);
        }
    }

    public void build(int number, final int position){
        HttpGetRequest get = new HttpGetRequest();
        get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
            @Override
            public void onUpdate(String result) {
                Intent defenseIntent = new Intent(mContext, DefenseActivity.class);
                mContext.startActivity(defenseIntent);

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
                Account account = gson.fromJson(content, Account.class);
                playerID = account.id;
                planetID = account.planet_id;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        get.execute("http://" + new BaseActivity().ip + ":8000/?type=defensebuild&playerid=" + playerID + "&planetid=" + planetID + "&amount=" + mAmounts.get(position) + "&buildid=" + (position) + "&amounttobuild=" + number);

    }

}


