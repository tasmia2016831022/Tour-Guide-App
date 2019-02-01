package com.example.dell.tourguidefinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyProfileDialogue extends AppCompatDialogFragment {
    private ImageView ProfileImage;
    private TextView ProfileName, ProfileEmail;

    FirebaseUser Me;
    String myName,myEmail;
    DatabaseReference ProfileInfoDatabase;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_my_profile, null);

        ProfileImage = view.findViewById(R.id.profileimage_dlg_imageview);
        ProfileName = view.findViewById(R.id.profileName);
        ProfileEmail = view.findViewById(R.id.profileEmail);

        Me = FirebaseAuth.getInstance().getCurrentUser();

        if (Me != null) {

            ProfileInfoDatabase = FirebaseDatabase.getInstance().getReference();
            ProfileInfoDatabase.child("Users").addChildEventListener(new ChildEventListener() {
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
                        ProfileName.setText(myName);
                        myEmail = userInformation.getUserEmail();
                        ProfileEmail.setText(myEmail);
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

        builder.setView(view)
                .setTitle("My Profile")
                .setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });



        return builder.create();
    }
}
