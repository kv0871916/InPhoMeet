package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnex = (Button) findViewById(R.id.Explore);
        btnex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcome();

            }
        });
    }
    private void welcome(){
        Intent intent = new Intent(this,SendOTPActivity.class);
        startActivity(intent);
    }
}