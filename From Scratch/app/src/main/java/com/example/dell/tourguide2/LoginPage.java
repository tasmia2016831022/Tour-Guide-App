package com.example.dell.tourguide2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
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

public class LoginPage extends AppCompatActivity {

    FirebaseAuth mAuth ;

    private EditText LoginpageEmail,LoginPagePassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        LoginpageEmail = findViewById(R.id.Email_LogIn_edittext);
        LoginPagePassword = findViewById(R.id.Password_LogIn_edittext);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.Login_LogIn_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        progressDialog.setMessage("Please wait to login..");

        final String email = LoginpageEmail.getText().toString().trim();
        String password = LoginPagePassword.getText().toString().trim();

        if (email.isEmpty()) {
            LoginpageEmail.setError("Email is required");
            LoginpageEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LoginpageEmail.setError("Please enter a valid email");
            LoginpageEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            LoginPagePassword.setError("Password is required");
            LoginPagePassword.requestFocus();
            return;
        }

        if (LoginPagePassword.length() < 6) {
            LoginPagePassword.setError("Minimum lenght of password should be 6");
            LoginPagePassword.requestFocus();
            return;
        }

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    progressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(LoginPage.this, AuthenticatedUserFeed.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("email",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
