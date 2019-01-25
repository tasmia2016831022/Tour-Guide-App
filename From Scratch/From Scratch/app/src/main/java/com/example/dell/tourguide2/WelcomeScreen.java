package com.example.dell.tourguide2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent welcomeToHome = new Intent(WelcomeScreen.this,HomePage.class);
                startActivity(welcomeToHome);

                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
