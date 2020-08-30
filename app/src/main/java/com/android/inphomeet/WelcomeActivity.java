package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnotp = (Button) findViewById(R.id.getotp);


        btnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votp();
            }
        });

    }

    private void votp() {
        Intent vop = new Intent(this,SendOTPActivity.class);
        startActivity(vop);
    }



}