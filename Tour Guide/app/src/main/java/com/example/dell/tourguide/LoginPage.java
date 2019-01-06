package com.example.dell.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.util.Patterns.EMAIL_ADDRESS;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText Loginpage_editTextEmail;
    EditText Loginpage_editTextPassword;
    ProgressDialog progressSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.Loginpage_loginButton).setOnClickListener(this);
        findViewById(R.id.Loginpage_SignUpButton).setOnClickListener(this);
        Loginpage_editTextEmail = findViewById(R.id.Loginpage_emailEdittext);
        Loginpage_editTextPassword = findViewById(R.id.Logipage_passwordEdittext);

        progressSign = new ProgressDialog(this);
    }

    private void DoUserLogin(){

        progressSign.setMessage("Please Wait to Logging in ..");

        String Loginpage_email = Loginpage_editTextEmail.getText().toString().trim();
        String Loginpage_password = Loginpage_editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(Loginpage_email).matches()){
            Loginpage_editTextEmail.setError("Please insert valid Email address ");
            Loginpage_editTextEmail.requestFocus();
            return;
        }
        if(Loginpage_email.isEmpty()){
            progressSign.dismiss();
            Loginpage_editTextEmail.setError("You need to insert Email address ");
            Loginpage_editTextEmail.requestFocus();
            return;
        }
        if(Loginpage_password.isEmpty()){
            progressSign.dismiss();
            Loginpage_editTextPassword.setError("Passweord needed ");
            Loginpage_editTextPassword.requestFocus();
            return;
        }
        if(Loginpage_password.length()<6)
        {
            progressSign.dismiss();
            Loginpage_editTextPassword.setError("Invalid Password ");
            Loginpage_editTextPassword.requestFocus();
            return ;
        }

        progressSign.show();

        mAuth.signInWithEmailAndPassword(Loginpage_email, Loginpage_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressSign.dismiss();
                    Intent intent = new Intent(LoginPage.this,AuthenticatedUserFeed.class);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    progressSign.dismiss();
                    Toast.makeText(getApplicationContext(),task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Loginpage_SignUpButton:
                startActivity(new Intent(this,SignUpPage.class));
                break;

            case R.id.Loginpage_loginButton:
                DoUserLogin();
                break;
        }

    }
}
