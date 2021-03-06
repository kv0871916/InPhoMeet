package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


 //   FirebaseAuth fAuth;
 private BroadcastReceiver MyReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnex;
        btnex = findViewById(R.id.Explore);
        MyReceiver = new MyReceiver();
        broadcastIntent();
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome();
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

    private void welcome(){
            Intent intent = new Intent(this, MainLoginActivity.class);
            startActivity(intent);
    }
}