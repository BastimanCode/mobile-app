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

public class RecyclerViewAdapterResources extends RecyclerView.Adapter<RecyclerViewAdapterResources.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterResources";

    private Context mContext;
    private ArrayList<Integer> mImages = new ArrayList<>();
    private ArrayList<String> mHeadlines = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mLevels = new ArrayList<>();

    public RecyclerViewAdapterResources(Context mContext, ArrayList<Integer> mImages, ArrayList<String> mHeadlines, ArrayList<String> mDescriptions, ArrayList<Integer> mLevels) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mHeadlines = mHeadlines;
        this.mDescriptions = mDescriptions;
        this.mLevels = mLevels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resources_listitem,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.resourceImage.setImageResource(mImages.get(position));

        holder.headline.setText(mHeadlines.get(position) + position);
        holder.description.setText(mDescriptions.get(position));
        holder.level.setText("Stufe: " + mLevels.get(position));

        //holder.resource1cost.setText((100 + (mLevels.get(position) * mLevels.get(position) * 100)));
        //holder.resource2cost.setText((200 + (mLevels.get(position) * mLevels.get(position) * 200)));
        //holder.resource3cost.setText((100 + (mLevels.get(position) * mLevels.get(position) * 100)));

        holder.resource1cost.setText("10000");
        holder.resource2cost.setText("20000");
        holder.resource3cost.setText("10000");

        holder.resourceImage1.setImageResource(R.drawable.brick1980_1920);
        holder.resourceImage2.setImageResource(R.drawable.processor2217771_1920);
        holder.resourceImage3.setImageResource(R.drawable.oil696579_1920);

        holder.build.setText("Bauen");
        holder.build.setOnClickListener(new View.OnClickListener() {
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
        Button build;
        ImageView resourceImage;
        RelativeLayout parentlayout;

        TextView resource1cost;
        TextView resource2cost;
        TextView resource3cost;

        ImageView resourceImage1;
        ImageView resourceImage2;
        ImageView resourceImage3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headline = itemView.findViewById(R.id.headline);
            description = itemView.findViewById(R.id.description);
            level = itemView.findViewById(R.id.level);
            build = itemView.findViewById(R.id.build);
            resourceImage = itemView.findViewById(R.id.resourceImage);
            parentlayout = itemView.findViewById(R.id.listitemresources);
            resource1cost = itemView.findViewById(R.id.resource1cost);
            resource2cost = itemView.findViewById(R.id.resource2cost);
            resource3cost = itemView.findViewById(R.id.resource3cost);
            resourceImage1 = itemView.findViewById(R.id.resource1image);
            resourceImage2 = itemView.findViewById(R.id.resource2image);
            resourceImage3 = itemView.findViewById(R.id.resource3image);
        }
    }
}


