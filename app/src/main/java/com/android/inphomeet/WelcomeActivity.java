package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnotp;
    private Button chat;
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MyReceiver = new MyReceiver();
        broadcastIntent();
        btnotp = (Button) findViewById(R.id.logout);
        chat = (Button) findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat();
            }
        });
        btnotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votp();

            }
        });

    }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }
    private void chat() {
        startActivity(new Intent(getApplicationContext(),MainChatsActivity.class));
        Toast.makeText(this, "Welcome to our app", Toast.LENGTH_SHORT).show();
    }
    private void votp() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainLoginActivity.class));
        Toast.makeText(this, "User is Logged out", Toast.LENGTH_SHORT).show();
        finish();
    }



}