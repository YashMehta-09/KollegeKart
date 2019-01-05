package com.hapy.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public void SignUp(View view) {

        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mLoginBtn;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.editText1);
        mPasswordField = (EditText) findViewById(R.id.editText2);

        mLoginBtn = (Button) findViewById(R.id.button);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             final String email = mEmailField.getText().toString();
                                             final String password = mPasswordField.getText().toString();

                                             if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                                                 Toast.makeText(MainActivity.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
                                             } else {

                                                 mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<AuthResult> task) {

                                                         if (!task.isSuccessful()) {
                                                             mEmailField.setText("");
                                                             mPasswordField.setText("");
                                                             Toast.makeText(MainActivity.this, "SignIn Problem", Toast.LENGTH_LONG).show();
                                                         } else {

                                                             adminCheck();
                                                         }
                                                     }
                                                 });
                                             }

                                         }
                                     }
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public void adminCheck()
    {
        if(mAuth.getCurrentUser().getUid().equals("dqrtlBrUk1bOdxNGjCQ1VPjgTR23")){

            Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        }
        else{

            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainPageDrawer.class);
            startActivity(intent);
        }
    }
}

