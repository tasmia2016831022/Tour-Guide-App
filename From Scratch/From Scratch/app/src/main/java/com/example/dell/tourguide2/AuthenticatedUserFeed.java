package com.example.dell.tourguide2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticatedUserFeed extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button SearchButton , CreatePost;
    private EditText SearchLocation;
    RadioGroup radioGroup;
    String email;
    static  String username;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticated_user_feed);

        SearchLocation = findViewById(R.id.SearchLocation_Auth_edittext);
        SearchButton = findViewById(R.id.Search_Auth_button);
        CreatePost = findViewById(R.id.CreatePost_Auth_button);
        radioGroup = findViewById(R.id.radio_Auth_rGrp);

        CreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthenticatedUserFeed.this, com.example.dell.tourguide2.CreatePost.class));
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(selected);

                String type = radioButton.getText().toString().trim();
                String place = SearchLocation.getText().toString().trim();

                Log.d("AuthenticatedUserFeed Click",type);
                Log.d("AuthenticatedUserFeed",type);
                Log.d("AuthenticatedUserFeed",type);
                Log.d("AuthenticatedUserFeed",place);
                Intent intent = new Intent(AuthenticatedUserFeed.this,MapActivity.class);
                intent.putExtra("place",place);
                intent.putExtra("type",type);
                startActivity(intent);




            }
        });

        Intent i = getIntent();
        email = i.getStringExtra("email");

        mDrawer = findViewById(R.id.drawer_AuthenticatedUser);

        mToggle = new ActionBarDrawerToggle(AuthenticatedUserFeed.this,mDrawer,R.string.open,R.string.close);
        mToggle.syncState();
        mDrawer.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListner();


        DatabaseReference def = FirebaseDatabase.getInstance().getReference();
        def.child("Users:").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile p = dataSnapshot.getValue(Profile.class);
                if(p.getUserEmail().equals(email)){
                    username = p.getUserName();
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

    private void logingOut()
    {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this,HomePage.class));
//        Toast.makeText(AuthenticatedUserFeed.this,username,Toast.LENGTH_SHORT).show();
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewU);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //for support action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.MyProfile_Drawer:
                startActivity(new Intent(AuthenticatedUserFeed.this,MyProfile.class));
                break;

            case R.id.Logout_Drawer:
                logingOut();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
