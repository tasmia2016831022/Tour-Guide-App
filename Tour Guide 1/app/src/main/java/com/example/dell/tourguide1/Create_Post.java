package com.example.dell.tourguide1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Create_Post extends AppCompatActivity {

    private EditText postET,Area,Name;
    private String post;
    private ImageButton profileImage;
    private Button postSubmit;
    private StorageReference dataStorage;
    private DatabaseReference profileDatabase;
    private DatabaseReference userImageInfo;
    private DatabaseReference globalpost;
    private RadioGroup radioGroup;
    String node;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__post);

        radioGroup = findViewById(R.id.radio_group);
        profileImage = (ImageButton) findViewById(R.id.ProfileImage);
        profileDatabase = FirebaseDatabase.getInstance().getReference();
        postET = (EditText) findViewById(R.id.postEditText);
        Area = findViewById(R.id.area);
        Name = findViewById(R.id.name);

        dataStorage = FirebaseStorage.getInstance().getReference();


        postSubmit = (Button) findViewById(R.id.submitPost);



        postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }


    public  void startPosting()
    {
        post = postET.getText().toString().trim();
        int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton rb =(RadioButton) findViewById(selected);

        String type = rb.getText().toString().trim();

        final String area = Area.getText().toString().trim();
        final String name = Name.getText().toString().trim();

        normal_post normalPost = new normal_post(name,area,post,type);

        profileDatabase.child("Posts")
                .child(type).push().setValue(normalPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful())
                   Toast.makeText(Create_Post.this,"Posting Successfull",Toast.LENGTH_LONG
                   ).show();
               else
                   Toast.makeText(Create_Post.this,task.getException().toString(),Toast.LENGTH_LONG
                   ).show();
            }
        });


    }
}
