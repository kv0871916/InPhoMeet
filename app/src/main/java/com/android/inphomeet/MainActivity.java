package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

 //   FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnex;
        btnex = findViewById(R.id.Explore);
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome();
            }
        });
    }
    private void welcome(){
            Intent intent = new Intent(this, NewUserActivity.class);
            startActivity(intent);
    }
}