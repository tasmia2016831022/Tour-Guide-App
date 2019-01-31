package com.example.dell.tourguidefinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity {

    ImageView ProfileImage;
    EditText UserName;
    EditText Email_SignupPage;
    EditText Password_SignupPage;
    Button SignUpButton;
    TextView HaveAnAccountLogin;
    ProgressDialog progressDialog;

    private static final int Gallery_Request = 1;
    private Uri ProfileImageUri = null;
    StorageReference profileImageStore = FirebaseStorage.getInstance().getReference("User Profile Image");

    private DatabaseReference UserdataStorage;
    static String uid;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ProfileImage = findViewById(R.id.profileimage_signup_imageview);
        UserName = findViewById(R.id.name_signup_edittext);
        Email_SignupPage = findViewById(R.id.email_signup_edittext);
        Password_SignupPage = findViewById(R.id.password_signup_edittext);
        HaveAnAccountLogin = findViewById(R.id.login_signup_textview);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        UserdataStorage = FirebaseDatabase.getInstance().getReference();
        SignUpButton = findViewById(R.id.signup_signup_button);


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RgisterUser();

            }
        });

        HaveAnAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                profileImageIntent.setType("image/*");
                startActivityForResult(profileImageIntent, Gallery_Request);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Request && resultCode == RESULT_OK) {
            ProfileImageUri = data.getData();
            ProfileImage.setImageURI(ProfileImageUri);
        }
    }

    private void RgisterUser() {

        progressDialog.setMessage("Please Wait to Sign Up");

        final String name = UserName.getText().toString().trim();
        final String email = Email_SignupPage.getText().toString().trim();
        String password = Password_SignupPage.getText().toString();

        if (name.isEmpty()) {
            UserName.setError("Name Required ");
            UserName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            Email_SignupPage.setError("Email Required ");
            Email_SignupPage.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email_SignupPage.setError("Valid Email Required ");
            Email_SignupPage.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Password_SignupPage.setError("Password Required ");
            Password_SignupPage.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Password_SignupPage.setError("Enter at least 6 digit ");
            Password_SignupPage.requestFocus();
            return;
        }

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            uid = user.getUid();

                            if (ProfileImageUri != null) {
                                StorageReference filepath = profileImageStore.child(ProfileImageUri.getLastPathSegment());

                                filepath.putFile(ProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Uri downloadUri = taskSnapshot.getDownloadUrl();

                                        UserInformation userInformation = new UserInformation(name, email, uid, downloadUri.toString());
                                        ///key changed
                                        UserdataStorage.child("Users").child(uid).setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task1) {
                                                if (task1.isSuccessful())
                                                    Toast.makeText(SignupActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }

                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


    }
}
