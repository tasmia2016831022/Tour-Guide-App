package com.example.dell.tourguide1;

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

       findViewById(R.id.TakeATour).setOnClickListener(new View.OnClickListener() {
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

        Intent publicFeed = new Intent(this,PublicQueryFeed.class);
        startActivity(publicFeed);
        ///public query page

    }

    public void GotoLoginPage(){

        Intent loginPage = new Intent(this,LoginPage.class);
        startActivity(loginPage);
    }

    public void GotoSignUpPage(){

        Intent signUpPage = new Intent(this,SignupPage.class);
        startActivity(signUpPage);
    }


}
