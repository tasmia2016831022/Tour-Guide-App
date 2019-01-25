package com.example.dell.tourguide2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreatePost extends AppCompatActivity {

    private EditText description , Area ;
    private String des; /// description
    private ImageButton postImage;
    private Button postSubmit;
    private Uri SelectImageUri = null;
    private DatabaseReference postedData;
    StorageReference imageStore = FirebaseStorage.getInstance().getReference("Posted Image");
    //String postImage;
    private RadioGroup radioGroup;
    String location;
    private static final int Gallery_Request = 1;

    static  String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        radioGroup = findViewById(R.id.radio_CreatePost_rGrp);
        postImage = findViewById(R.id.AddImage_CreatePost_imageButton);
        postedData = FirebaseDatabase.getInstance().getReference();
        description = findViewById(R.id.Description_CreatePost_edittext);
        Area = findViewById(R.id.Area_CreatePost_edittext);
        postSubmit = findViewById(R.id.Post_CreatePost_button);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Request);
            }
        });

        postSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selected);
                type = radioButton.getText().toString().trim();
                startPosting();
            }
        });



    }

    private void startPosting() {

        des = description.getText().toString().trim();
        //int selected = radioGroup.getCheckedRadioButtonId();
        //RadioButton radioButton = findViewById(selected);

       // String type = radioButton.getText().toString().trim();

        final String area = Area.getText().toString().trim();

        location = postedData.child("Posts").child(type).push().getKey();
        UploadImage(type , area);


    }

    private void UploadImage(final String type, final String area) {

        if(SelectImageUri != null)
        {
            StorageReference filePath = imageStore.child(location).child(SelectImageUri.getLastPathSegment());

            filePath.putFile(SelectImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();

//                    System.out.println(downloadUri.toString() + "This is downloadURI");
                   PostingSupport postingSupport = new PostingSupport(type,area,des,downloadUri.toString(),AuthenticatedUserFeed.username,AuthenticatedUserFeed.username);
                   postedData.child("Posts").child(type).child(location).setValue(postingSupport);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Request && resultCode == RESULT_OK)
        {
            SelectImageUri = data.getData();
            postImage.setImageURI(SelectImageUri);

        }
    }
}
