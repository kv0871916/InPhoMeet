package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fAuth = FirebaseAuth.getInstance();
    }
    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if(!isFinishing()){
                if(fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}