package com.example.expensetracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.R;
import com.example.expensetracker.database.DBHelper;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    public DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        //login with "admin" and "admin"

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().isEmpty() && ! password.getText().toString().isEmpty()) {
                    RegistrationUtil   validation = new RegistrationUtil(username.getText().toString(),password.getText().toString() );

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter information !!!", Toast.LENGTH_SHORT).show();
                }
                RegistrationUtil   validation = new RegistrationUtil(username.getText().toString(),password.getText().toString() );
                if (validation.validation()) {
                    openUserActivity();
                } else
                    Toast.makeText(MainActivity.this, "LOGIN FAILED !!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void openUserActivity () {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}