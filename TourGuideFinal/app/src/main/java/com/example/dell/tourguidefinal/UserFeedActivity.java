package com.example.dell.tourguidefinal;


import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//import static com.example.dell.tourguidefinal.MapView.arrayList;

public class UserFeedActivity extends AppCompatActivity {

    String email;
    static String uid, username;

    public static List<PostingSupport> arrayList = new ArrayList<>();

    FloatingActionButton plus, createPost, searchLocation, myProfile;
    Animation fabOpen, fabClose, rotateColckwise, rotateAnticlockwise;
    boolean isfabOpen = false;

    private static final String TAG = "TabActivity";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        Log.d(TAG, "onCreate: Starting");

        plus = findViewById(R.id.fab_plus);
        createPost = findViewById(R.id.fab_create);
        searchLocation = findViewById(R.id.fab_search);
        myProfile = findViewById(R.id.fab_profile);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotateColckwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        rotateAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfabOpen) {
                    searchLocation.startAnimation(fabClose);
                    createPost.startAnimation(fabClose);
                    myProfile.startAnimation(fabClose);
                    plus.startAnimation(rotateAnticlockwise);
                    myProfile.setClickable(false);
                    createPost.setClickable(false);
                    searchLocation.setClickable(false);
                    isfabOpen = false;
                } else {
                    searchLocation.startAnimation(fabOpen);
                    createPost.startAnimation(fabOpen);
                    myProfile.startAnimation(fabOpen);
                    plus.startAnimation(rotateColckwise);
                    myProfile.setClickable(true);
                    createPost.setClickable(true);
                    searchLocation.setClickable(true);
                    isfabOpen = true;
                }
            }
        });

        searchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MapView.arrayList.clear();
                SearchDialog searchDialog = new SearchDialog();
                searchDialog.show(getSupportFragmentManager(), "search dialog");

            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserFeedActivity.this, CreatePost.class));
                Toast.makeText(UserFeedActivity.this, "Create a Post", Toast.LENGTH_SHORT).show();

            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyProfileDialogue myProfileDialogue = new MyProfileDialogue();
                myProfileDialogue.show(getSupportFragmentManager(),"my profile");

            }
        });


        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent i = getIntent();
        email = i.getStringExtra("email").toString();


        DatabaseReference def = FirebaseDatabase.getInstance().getReference();
        def.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile p = dataSnapshot.getValue(Profile.class);
                //System.out.println(email);
                if (p.getUserEmail().equals(email)) {
                    uid = p.getUid();
                    username = p.getUserName();
                    System.out.println(uid);
                    System.out.println(username);
                    //c.setText(username);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        sectionPageAdapter.addFragment(new MapView(), "Map");
        sectionPageAdapter.addFragment(new PostView(), "Post");
        viewPager.setAdapter(sectionPageAdapter);


    }


}
