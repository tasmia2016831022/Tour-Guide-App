package com.example.dell.tourguide2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button TakeATourHomePage;
    private Button LoginHomePage;
    private Button SignupHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TakeATourHomePage = findViewById(R.id.take_a_tour_Homepage_button);
        LoginHomePage = findViewById(R.id.Login_Homepage_button);
        SignupHomePage = findViewById(R.id.Signup_Homepage_button);
        
        
        TakeATourHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPublicFeed();
            }
        });
        
        LoginHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLoginPage();
            }
        });
        
        SignupHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoSignUpPage();
            }
        });

    }

    private void GotoSignUpPage() {

        Intent intent = new Intent(HomePage.this, SignUpPage.class);
        startActivity(intent);
    }

    private void GoToLoginPage() {
        Intent intent = new Intent(HomePage.this,LoginPage.class);
        startActivity(intent);
    }

    private void GoToPublicFeed() {

    }

}
