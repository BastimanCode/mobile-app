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

public class RecyclerViewAdapterResearch extends RecyclerView.Adapter<RecyclerViewAdapterResearch.ViewHolder>{

    int playerID;
    int planetID;

    private static final String TAG = "RecyclerViewAdapterResearch";

    private Context mContext;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mLevels = new ArrayList<>();
    private ArrayList<Integer> mmaterial = new ArrayList<>();
    private ArrayList<Integer> melectronics = new ArrayList<>();
    private ArrayList<Integer> mfuel = new ArrayList<>();


    public RecyclerViewAdapterResearch(Context mContext, ArrayList<Integer> mImages, ArrayList<String> mHeadlines, ArrayList<String> mDescriptions, ArrayList<Integer> mLevels, ArrayList<Integer> mmaterial, ArrayList<Integer> melectronics, ArrayList<Integer> mfuel) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mHeadlines = mHeadlines;
        this.mDescriptions = mDescriptions;
        this.mLevels = mLevels;
        this.mmaterial = mmaterial;
        this.melectronics = melectronics;
        this.mfuel = mfuel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.research_listitem,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.researchImage.setImageResource(mImages.get(position));

        holder.headline.setText(mHeadlines.get(position));
        holder.description.setText(mDescriptions.get(position));
        holder.level.setText("Stufe: " + mLevels.get(position));

        holder.resource1cost.setText(String.valueOf(mmaterial.get(position)));
        holder.resource2cost.setText(String.valueOf(melectronics.get(position)));
        holder.resource3cost.setText(String.valueOf(mfuel.get(position)));

        holder.resourceImage1.setImageResource(R.drawable.brick1980_1920);
        holder.resourceImage2.setImageResource(R.drawable.processor2217771_1920);
        holder.resourceImage3.setImageResource(R.drawable.oil696579_1920);

        if (mLevels.get(position) >= 10) {
            holder.research.setText("Max Level");
        } else {
            holder.research.setText("Erforschen");
            holder.research.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpGetRequest get = new HttpGetRequest();
                    get.setUpdateListener(new HttpGetRequest.OnUpdateListener() {
                        @Override
                        public void onUpdate(String result) {
                            Intent buildingsIntent = new Intent(mContext, ResearchActivity.class);
                            mContext.startActivity(buildingsIntent);
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
                    get.execute("http://" + new BaseActivity().ip + ":8000/?type=researching&playerid=" + playerID + "&planetid=" + planetID + "&level=" + mLevels.get(position) + "&buildid=" + (position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeadlines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView headline;
        TextView description;
        TextView level;
        Button research;
        ImageView researchImage;
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
            level = itemView.findViewById(R.id.amount);
            research = itemView.findViewById(R.id.attack);
            researchImage = itemView.findViewById(R.id.galaxyImage);
            parentlayout = itemView.findViewById(R.id.listitemresearch);
            resource1cost = itemView.findViewById(R.id.resource1cost);
            resource2cost = itemView.findViewById(R.id.resource2cost);
            resource3cost = itemView.findViewById(R.id.resource3cost);
            resourceImage1 = itemView.findViewById(R.id.resource1image);
            resourceImage2 = itemView.findViewById(R.id.resource2image);
            resourceImage3 = itemView.findViewById(R.id.resource3image);
        }
    }
}
