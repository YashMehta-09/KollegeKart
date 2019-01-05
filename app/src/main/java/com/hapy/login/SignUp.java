package com.hapy.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    private FirebaseAuth firebaseAuth;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mName;
    private EditText mPhoneNumber;
    private EditText mBirthDate;
    private Button mSignUpBtn;
    private Button mSignInBtn;
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


     /*   //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainPageDrawer.class));
        }*/

        mEmail = (EditText) findViewById(R.id.editText4);
        mPassword = (EditText) findViewById(R.id.editText5);
        mSignUpBtn = (Button) findViewById(R.id.button3);
        mName = (EditText) findViewById(R.id.editText3);
        mPhoneNumber = (EditText) findViewById(R.id.editText6);
        mBirthDate = (EditText) findViewById(R.id.editText7);
        mSignInBtn = (Button) findViewById(R.id.button5);

        progressDialog = new ProgressDialog(this);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(s);
            }
        });
        //attaching listener to button
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }


        });
    }

    private void registerUser() {
        //getting email and password from edit texts
        final String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String phonenumber = mPhoneNumber.getText().toString().trim();
        final String birthdate = mBirthDate.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phonenumber) || TextUtils.isEmpty(birthdate)) {
            Toast.makeText(SignUp.this, "Please Fill Up The fields", Toast.LENGTH_LONG).show();
            return;
        }

        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            Map<String, String> map = new HashMap<>();
                            map.put("name", name);
                            map.put("phonenumber", phonenumber);
                            map.put("birthdate", birthdate);
                            map.put("image", "default");
                            mDatabase.child("Users").child("UsersData").child(firebaseAuth.getCurrentUser().getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            //display some message here
                            Toast.makeText(SignUp.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}




