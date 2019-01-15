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

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText loginpageEmail,loginpagePassword;
    ProgressDialog progressing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        loginpageEmail = findViewById(R.id.Loginpage_emailEdittext);
        loginpagePassword = findViewById(R.id.Logipage_passwordEdittext);

        progressing = new ProgressDialog(this);

        findViewById(R.id.Loginpage_SignUpButton).setOnClickListener(this);
        findViewById(R.id.Loginpage_loginButton).setOnClickListener(this);

    }

    private void userLogin()
    {
        progressing.setMessage("Please wait to login..");

        String loginemail = loginpageEmail.getText().toString().trim();
        String loginpassword = loginpagePassword.getText().toString().trim();

        if (loginemail.isEmpty()) {
            loginpageEmail.setError("Email is required");
            loginpageEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
            loginpageEmail.setError("Please enter a valid email");
            loginpageEmail.requestFocus();
            return;
        }

        if (loginpassword.isEmpty()) {
            loginpagePassword.setError("Password is required");
            loginpagePassword.requestFocus();
            return;
        }

        if (loginpassword.length() < 6) {
            loginpageEmail.setError("Minimum lenght of password should be 6");
            loginpagePassword.requestFocus();
            return;
        }

        progressing.show();

        mAuth.signInWithEmailAndPassword(loginemail, loginpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressing.dismiss();
                    finish();
                    Intent intent = new Intent(LoginPage.this, UserProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

   /** @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, UserProfile.class));
        }
    }
**/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Loginpage_SignUpButton:
                finish();
                startActivity(new Intent(this, SignupPage.class));
                break;

            case R.id.Loginpage_loginButton:
                userLogin();
                break;
        }
    }
}
