package com.hapy.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Address extends AppCompatActivity {



    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;

    public void PlaceOrder(View view){

        Toast.makeText(this, "Your Order Has Been Placed.ThankYou For Shopping", Toast.LENGTH_SHORT).show();
        mDatabaseRef.child("Cart").child(firebaseAuth.getCurrentUser().getUid()).removeValue();
        Intent intent = new Intent(this, MainPageDrawer.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();



    }
}
