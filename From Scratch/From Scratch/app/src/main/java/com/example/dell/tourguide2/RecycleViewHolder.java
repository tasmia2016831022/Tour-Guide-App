package com.example.dell.tourguide2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleViewHolder extends RecyclerView.ViewHolder{

     static TextView PostArea;
     static ImageView PostImage;
     static TextView PostType;
     static TextView PostDescription;
     static TextView Poster;

    public RecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        PostArea = itemView.findViewById(R.id.PostAreaName);
        PostImage = itemView.findViewById(R.id.PostImage);
        PostType = itemView.findViewById(R.id.postType);
        PostDescription = itemView.findViewById(R.id.PostDescription);
        Poster = itemView.findViewById(R.id.postContributor);

    }
}
