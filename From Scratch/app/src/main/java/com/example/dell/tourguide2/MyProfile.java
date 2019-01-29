package com.example.dell.tourguide2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {

    private ImageView ProfileImage;
    private TextView MyName;
    private TextView MyEmail;
    private Button Logout, Home; /// home == authUserFeed
    FirebaseUser Me;
    FirebaseDatabase MyInformation;
    FirebaseStorage MyProfileImage;
    String uid;
    String myName, myEmail;
    DatabaseReference ProfileInfoRefDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ProfileImage = findViewById(R.id.ProfileImage_MyProfile_ImageView);
        MyName = findViewById(R.id.DispalyName_MyProfile_textview);
        MyEmail = findViewById(R.id.DisplayEmail_MyProfile_textview);
        Logout = findViewById(R.id.Logout_MyProfile);
        Home = findViewById(R.id.GoToHomePage_MyProfile);

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent authIntent = new Intent(MyProfile.this, AuthenticatedUserFeed.class);
                startActivity(authIntent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MyProfile.this, HomePage.class));
            }
        });


        Me = FirebaseAuth.getInstance().getCurrentUser();

        if (Me != null) {

            ProfileInfoRefDatabase = FirebaseDatabase.getInstance().getReference();
            ProfileInfoRefDatabase.child("Users:").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                    // myEmail = userInformation.getUserEmail();
                    System.out.println(userInformation.getUserEmail());
                    System.out.println(myEmail);
                    if (userInformation.getUserEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        userInformation.setProfileImageurl(userInformation.getProfileImageurl());
                        Picasso.get().load(userInformation.getProfileImageurl()).into(ProfileImage);

                        myName = userInformation.getUserName();
                        MyName.setText(myName);
                        myEmail = userInformation.getUserEmail();
                        MyEmail.setText(myEmail);
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

    }

}
