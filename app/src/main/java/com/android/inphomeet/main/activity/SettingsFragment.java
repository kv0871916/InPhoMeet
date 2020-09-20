package com.android.inphomeet.main.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.inphomeet.MainLoginActivity;
import com.android.inphomeet.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_settings, container, false);
       final Button logout = (Button) view.findViewById(R.id.logout);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               logout();
           }
       });
       return view;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), MainLoginActivity.class));
        Toast.makeText(getActivity(), "User is logged out", Toast.LENGTH_SHORT).show();
    }
}