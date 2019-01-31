package com.example.dell.tourguidefinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText Email_LoginPage;
    EditText Password_LoginPage;
    Button LoginButton;
    TextView CreateAccount;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        Email_LoginPage = findViewById(R.id.email_login_edittext);
        Password_LoginPage = findViewById(R.id.password_login_edittext);
        LoginButton = findViewById(R.id.login_login_button);
        CreateAccount = findViewById(R.id.signup_login_textview);
        progressDialog = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    UserLogin();
            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                //finish();
                startActivity(intent);
            }
        });
    }

    private void UserLogin() {
        progressDialog.setMessage("Please wait to login..");

        final String email = Email_LoginPage.getText().toString().trim();
        String password = Password_LoginPage.getText().toString().trim();

        if (email.isEmpty()) {
            Email_LoginPage.setError("Email is required");
            Email_LoginPage.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email_LoginPage.setError("Please enter a valid email");
            Email_LoginPage.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Password_LoginPage.setError("Password is required");
            Password_LoginPage.requestFocus();
            return;
        }

        if (Password_LoginPage.length() < 6) {
            Password_LoginPage.setError("Minimum lenght of password should be 6");
            Password_LoginPage.requestFocus();
            return;
        }

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    progressDialog.dismiss();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String uid = user.getUid();

                    finish();
                    Intent intent = new Intent(LoginActivity.this, UserFeedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("email", email);
                    Log.d("checking: ", email);
                    //intent.putExtra("email",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
