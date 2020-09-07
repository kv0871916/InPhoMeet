package com.android.inphomeet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_navigation_header extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_header);
        final TextView UserInfo = findViewById(R.id.UserInfo);
        final TextView isVerified = findViewById(R.id.isVerified);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("Users").child("Username");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                   UserInfo.setText(snapshot.child("FullName").getValue(String.class));
                   isVerified.setText(snapshot.child("PhoneNumber").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_navigation_header.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
