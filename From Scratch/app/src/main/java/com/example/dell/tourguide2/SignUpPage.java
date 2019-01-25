package com.example.dell.tourguide2;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SignUpPage extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private EditText SignupName;
    private EditText SignupEmail;
    private EditText SignupPassword;
    private Button SignupButton;
    private Button SignupAlreadyLoginButton;

    private static final int Gallery_Request = 1;
    private ImageView ProfileImage;
    private Uri ProfileImageUri = null;
    StorageReference profileImageStore = FirebaseStorage.getInstance().getReference("User Profile Image");


    private DatabaseReference UserDataStorage;
    String UserData;
    String uid;

    static String UserImageurl;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        SignupName = findViewById(R.id.Name_SignUp_edittext);
        SignupEmail = findViewById(R.id.Email_SignUp_edittext);
        SignupPassword = findViewById(R.id.Password_SignUp_edittext);
        ProfileImage = findViewById(R.id.ProfileImage_SignUp_imageview);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        UserDataStorage = FirebaseDatabase.getInstance().getReference();

        SignupButton = findViewById(R.id.Signup_SignUp_button);
        SignupAlreadyLoginButton = findViewById(R.id.Login_SignUp_button);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser(); // Also will save user data in database
            }
        });

        SignupAlreadyLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpPage.this,LoginPage.class));
            }
        });

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                profileImageIntent.setType("image/*");
                startActivityForResult(profileImageIntent,Gallery_Request);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Request && resultCode==RESULT_OK)
        {
            ProfileImageUri = data.getData();
            ProfileImage.setImageURI(ProfileImageUri);
        }
    }

    private void RegisterUser() {

        progressDialog.setMessage("Please Wait to Sign Up");

        final String name = SignupName.getText().toString().trim();
        final String email = SignupEmail.getText().toString().trim();
        String password = SignupPassword.getText().toString();

        if (name.isEmpty())
        {
            SignupName.setError("Name Required ");
            SignupName.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
             SignupEmail.setError("Email Required " );
             SignupEmail.requestFocus();
             return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            SignupEmail.setError("Valid Email Required ");
            SignupPassword.requestFocus();
            return;
        }
        if (password.isEmpty())
        {
            SignupPassword.setError("Password Required ");
            SignupPassword.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            SignupPassword.setError("Enter at least 6 digit ");
            SignupPassword.requestFocus();
            return;
        }

       progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            progressDialog.dismiss();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            uid = user.getUid();

                            if(ProfileImageUri != null)
                            {
                                StorageReference filepath = profileImageStore.child(ProfileImageUri.getLastPathSegment());

                                filepath.putFile(ProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Uri downloadUri =taskSnapshot.getDownloadUrl();

                                        UserInformation userInformation = new UserInformation(name,email,uid,downloadUri.toString());
                                        UserDataStorage.child("Users:").child(name).setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task1) {
                                                if(task1.isSuccessful())
                                                    Toast.makeText(SignUpPage.this,"Successfull",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                        startActivity(new Intent(SignUpPage.this,AuthenticatedUserFeed.class));
                                    }
                                });

                            }

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(this, "Authentication failed.",
                              //      Toast.LENGTH_SHORT).show();
                            //updateUI(null);

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

    }
}
