package com.android.inphomeet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.android.inphomeet.R;

import java.util.HashMap;

public class NavigationheaderActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_header);
        TextView number = findViewById(R.id.UserInfo);
        TextView username = findViewById(R.id.isVerified);
        SessionManger sessionManger = new SessionManger(this);
        HashMap<String,String> usersDetails =  sessionManger.getUsersDetailsFromSession();
        //String FullName = usersData.get(SessionManger.)
        String Number = usersDetails.get(SessionManger.KEY_PHONENUMBER);
        String Username = usersDetails.get(SessionManger.KEY_USERNAME);

        number.setText("+91-"+Number);
        username.setText("Welcome "+Username);

    }
}