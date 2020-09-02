package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btnex;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            finish();
        }
        btnex = (Button) findViewById(R.id.Explore);
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome();

            }
        });
    }
    private void welcome(){
        Intent intent = new Intent(this,MainLoginActivity.class);
        startActivity(intent);
    }
}