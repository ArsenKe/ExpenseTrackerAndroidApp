package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractRepository extends AppCompatActivity {

    public DBHelper mydb;
    TextView editAccount;
    TextView editTransType;
    TextView editCategory;

    public AbstractRepository(DBHelper db) {
        mydb = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        setContentView(R.layout.activity_main);

        editAccount = (TextView) findViewById(R.id.account);
        editTransType = (TextView) findViewById(R.id.transType);
        editCategory = (TextView) findViewById(R.id.category);
*/
    }

    //public abstract boolean writeInDB(View view);
    //public abstract Cursor readDB(View view);
}