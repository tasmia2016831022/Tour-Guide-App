package com.example.dell.tourguidefinal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.dell.tourguidefinal.MapView.arrayList;

public class RecyclePostAdapter extends RecyclerView.Adapter<RecycleViewHolder>{

    private Context context;

    public RecyclePostAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_layout,viewGroup,false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {

        PostingSupport Postingposition = arrayList.get(i);
        RecycleViewHolder.PostArea.setText(Postingposition.getArea());
        RecycleViewHolder.PostType.setText(Postingposition.getType());
        RecycleViewHolder.PostName.setText(Postingposition.getName());
        RecycleViewHolder.PostDescription.setText(Postingposition.getDescription());
        RecycleViewHolder.Poster.setText(Postingposition.getUserName());


        Picasso.get().load(Postingposition.getImageuri()).into(RecycleViewHolder.PostImage);

        RecycleViewHolder.PostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(context, Postingposition.getArea(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return 0;
        return arrayList.size();
    }
}
