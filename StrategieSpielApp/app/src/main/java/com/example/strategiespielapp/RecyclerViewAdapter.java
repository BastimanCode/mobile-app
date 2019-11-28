package com.example.strategiespielapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<String> mLevels = new ArrayList<>();

    public RecyclerViewAdapter(Context mContext, ArrayList<Integer> mImages, ArrayList<String> mHeadlines, ArrayList<String> mDescriptions, ArrayList<String> mLevels) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mHeadlines = mHeadlines;
        this.mDescriptions = mDescriptions;
        this.mLevels = mLevels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemresearch,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.researchImage.setImageResource(mImages.get(position));

        holder.headline.setText(mHeadlines.get(position));
        holder.description.setText(mDescriptions.get(position));
        holder.level.setText(mLevels.get(position));

        holder.research.setText("Erforschen");
        holder.research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HTTP-Request an Server zum Erforschen
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
        TextView level;
        Button research;
        ImageView researchImage;
        RelativeLayout parentlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.headline);
            description = itemView.findViewById(R.id.description);
            level = itemView.findViewById(R.id.level);
            research = itemView.findViewById(R.id.research);
            researchImage = itemView.findViewById(R.id.researchimage);
            parentlayout = itemView.findViewById(R.id.listitemresearch);
        }
    }
}
