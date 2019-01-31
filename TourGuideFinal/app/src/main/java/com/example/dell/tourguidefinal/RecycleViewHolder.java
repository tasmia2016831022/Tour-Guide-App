package com.example.dell.tourguidefinal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleViewHolder extends RecyclerView.ViewHolder{

     static TextView PostArea;
     static TextView PostName;
     static ImageView PostImage;
     static TextView PostType;
     static TextView PostDescription;
     static TextView Poster;

    public RecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        PostArea = itemView.findViewById(R.id.PostAreaName);
        PostName = itemView.findViewById(R.id.PostName);
        PostImage = itemView.findViewById(R.id.PostImage);
        PostType = itemView.findViewById(R.id.postType);
        PostDescription = itemView.findViewById(R.id.PostDescription);
        Poster = itemView.findViewById(R.id.postContributor);

    }
}
