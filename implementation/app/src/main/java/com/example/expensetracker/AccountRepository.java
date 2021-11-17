package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountRepository extends AbstractRepository {

    TextView editAccount;
    TextView editTransType;
    TextView editCategory;
    DBHelper mydb;

    public AccountRepository(DBHelper db) {
        super(db);
        mydb = db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
/*
        editAccount = (TextView) findViewById(R.id.account);
        editTransType = (TextView) findViewById(R.id.transType);
        editCategory = (TextView) findViewById(R.id.category);
*/
    }

    public boolean writeInDB(String accountType, double value) {
        if (mydb.addAccount(accountType, value)) {
            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Record not added", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), AccountRepository.class);
        startActivity(intent);
        return false;
    }

    public Cursor readDB(int accountType) {
        Cursor cursor = mydb.getData(accountType);

        return cursor;
    }
}
