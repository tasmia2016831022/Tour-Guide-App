package com.example.dell.tourguide1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Auth_Profile extends AppCompatActivity {

    private Button search,create;
    private EditText text;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth__profile);

        create = findViewById(R.id.create_post);
        search = findViewById(R.id.search_post);
        text = findViewById(R.id.place);
        radioGroup = findViewById(R.id.radio_query);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Auth_Profile.this,Create_Post.class));
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                RadioButton rb =(RadioButton) findViewById(selected);

                String type = rb.getText().toString().trim();
                String place = text.getText().toString().trim();
                Log.d("Auth_Profile Click",type);
                Log.d("Auth_Profile",type);
                Log.d("Auth_Profile",type);
                Log.d("Auth_Profile",place);
                Intent intent = new Intent(Auth_Profile.this,Search_trial.class);
                intent.putExtra("place",place);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }
}
