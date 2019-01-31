package com.example.dell.tourguidefinal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.dell.tourguidefinal.MapView.arrayList;

public class PostView extends Fragment {

    RecyclerView recyclerView;
    RecyclePostAdapter recyclePostAdapter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_postview,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        return view;
    }

    private void loadData() {
        //arrayList.clear();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("lodading ..");
        progressDialog.show();
        try
        {
            recyclePostAdapter = new RecyclePostAdapter(getContext());
            recyclerView.setAdapter(recyclePostAdapter);
            progressDialog.dismiss();

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
