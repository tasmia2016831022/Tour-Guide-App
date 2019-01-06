package com.example.dell.tourguide;

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

public class SignUpPage extends AppCompatActivity implements View.OnClickListener{

    EditText SignUpPage_editTextEmail , SignUpPageeditTextPassword;
    private ProgressDialog progressSign;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        SignUpPage_editTextEmail = findViewById(R.id.SignupPage_emailEdittext);
        SignUpPageeditTextPassword = findViewById(R.id.SignupPage_passwordEdittext);

        progressSign = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.SignupPage_signupButton).setOnClickListener(this);
        findViewById(R.id.SignupPage_alredyLoginButton).setOnClickListener(this);


    }

    private void registerUser(){
        progressSign.setMessage("Please Wait to Sign in ..");
        progressSign.show();
        String email = SignUpPage_editTextEmail.getText().toString().trim();
        String password = SignUpPageeditTextPassword.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            progressSign.dismiss();
            SignUpPage_editTextEmail.setError("A valid Email is required.");
            SignUpPage_editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length()<6)
        {
            progressSign.dismiss();
            SignUpPageeditTextPassword.setError("Minimum length(6) password is required");
            SignUpPageeditTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    progressSign.dismiss();
                    Intent intent = new Intent(SignUpPage.this,LoginPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);                }
                else
                {
                    progressSign.dismiss();
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"This account is already registered",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SignupPage_signupButton:

                registerUser();

                break;

            case R.id.SignupPage_alredyLoginButton:
                startActivity(new Intent(this,LoginPage.class));
                break;
        }
    }
}
