package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.inphomeet.R;

public class NavigationheaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_header);
        TextView number = findViewById(R.id.UserInfo);
        TextView username = findViewById(R.id.isVerified);
    }
}