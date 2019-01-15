package com.example.dell.tourguide1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MapMarkerDetail extends AppCompatActivity {

    private TextView catagory , catagoryType;
    private TextView catagoryName;
    ImageView imageView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    DatabaseReference childreference = reference.child("url");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker_detail);

        catagory = findViewById(R.id.catagory);
        catagoryType = findViewById(R.id.catagoryType);
        catagoryName = findViewById(R.id.catagoryName);
        imageView = findViewById(R.id.mapImage);

        loadInfo();
    }

    private void loadInfo() {

        ///Ekhane textview er change guli korte hobe
    }

    @Override
    protected void onStart() {
        super.onStart();

        childreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                Picasso.get().load(message).into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(MapMarkerDetail.this,"Error loading",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
