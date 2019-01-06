package com.example.dell.tourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button HomePage_TakeATour;
    private Button HomePage_LogInButton;
    private Button HomePage_SignUpButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       HomePage_TakeATour = (Button) findViewById(R.id.take_a_tour);
       HomePage_TakeATour.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    GotoPublicFeed();
           }
       });


        HomePage_LogInButton = (Button) findViewById(R.id.homepage_loginButton);
        HomePage_LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoLoginPage();
            }
        });

       HomePage_SignUpButton = (Button) findViewById(R.id.homepage_signupButton);
        HomePage_SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoSignUpPage();
            }
        });


    }

    public void GotoPublicFeed(){


    }

    public void GotoLoginPage(){

        Intent loginPage = new Intent(this,LoginPage.class);
        startActivity(loginPage);
    }

    public void GotoSignUpPage(){

        Intent signUpPage = new Intent(this,SignUpPage.class);
        startActivity(signUpPage);
    }



}
