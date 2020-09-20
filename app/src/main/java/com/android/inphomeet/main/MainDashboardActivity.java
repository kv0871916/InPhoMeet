package com.android.inphomeet.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.inphomeet.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainDashboardActivity extends AppCompatActivity {
ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        chipNavigationBar = findViewById(R.id.buttom_nav_menu);
        bottomMenu();
    }

    private void bottomMenu() {



    }
}