package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class UserActivity extends AppCompatActivity {

    /*
    private int id;
    private String name, email;
    private Set<Accounts> accounts;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

    }
}