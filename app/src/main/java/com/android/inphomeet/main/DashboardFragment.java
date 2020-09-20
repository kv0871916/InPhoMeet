package com.android.inphomeet.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.inphomeet.ELoginActivity;
import com.android.inphomeet.R;
import com.android.inphomeet.SessionManger;

import java.util.HashMap;


public class DashboardFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView number = (TextView) view.findViewById(R.id.Welcometext);
//        final TextView username = (TextView) view.findViewById(R.id.text);
//        SessionManger sessionManger = new SessionManger(ELoginActivity.this);
//        HashMap<String,String> usersDetails =  sessionManger.getUsersDetailsFromSession();
//        String Number = usersDetails.get(SessionManger.KEY_PHONENUMBER);
//        String Username = usersDetails.get(SessionManger.KEY_USERNAME);
//
//        number.setText("+91-"+Number);
//        username.setText("Welcome "+username);
       // SessionManger sessionManger = new SessionManger(DashboardFragment.this);
        return view;

    }
}