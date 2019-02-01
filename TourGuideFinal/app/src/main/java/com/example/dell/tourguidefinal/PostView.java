package com.example.dell.tourguidefinal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static com.example.dell.tourguidefinal.UserFeedActivity.arrayList;

//import static com.example.dell.tourguidefinal.MapView.arrayList;

//import static com.example.dell.tourguidefinal.MapView.arrayList;

public class PostView extends Fragment {

    RecyclerView recyclerView;
    RecyclePostAdapter recyclePostAdapter;
    ProgressDialog progressDialog;
    List<PostingSupport>supportList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_postview,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();

        return view;
    }

    private void loadData() {
        //arrayList.clear();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("lodading ..");
        progressDialog.show();
        try
        {
            //supportList = arrayList;
            Log.d("Array Size ",Integer.toString(arrayList.size()));

            recyclePostAdapter = new RecyclePostAdapter(arrayList,getActivity());
            progressDialog.dismiss();
            recyclePostAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclePostAdapter);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
