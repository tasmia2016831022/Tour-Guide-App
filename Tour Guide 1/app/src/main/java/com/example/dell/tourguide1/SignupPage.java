package com.example.dell.tourguide1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupPage extends AppCompatActivity implements View.OnClickListener{

    ProgressDialog progressing;
    EditText signuppageEmail,signuppagePassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        signuppageEmail = findViewById(R.id.SignupPage_emailEdittext);
        signuppagePassword = findViewById(R.id.SignupPage_passwordEdittext);
        progressing = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.SignupPage_signupButton).setOnClickListener(this);
        findViewById(R.id.SignupPage_alredyLoginButton).setOnClickListener(this);
    }

    private void registerUser() {

        progressing.setMessage("Please wait to SignUp");
        String email = signuppageEmail.getText().toString().trim();
        String password = signuppagePassword.getText().toString().trim();

        if (email.isEmpty()) {
            signuppageEmail.setError("Email is required");
            signuppagePassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signuppageEmail.setError("Please enter a valid email");
            signuppagePassword.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signuppagePassword.setError("Password is required");
            signuppagePassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            signuppagePassword.setError("Minimum lenght of password should be 6");
            signuppagePassword.requestFocus();
            return;
        }

        progressing.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressing.dismiss();
                    finish();
                    startActivity(new Intent(SignupPage.this, UserProfile.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.SignupPage_signupButton:
                registerUser();
                break;

            case R.id.SignupPage_alredyLoginButton:
                finish();
                startActivity(new Intent(this, LoginPage.class));
                break;
        }

    }
}
