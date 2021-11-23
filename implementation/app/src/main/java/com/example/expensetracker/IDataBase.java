package com.example.expensetracker;

import android.database.Cursor;
import android.view.View;

import java.util.ArrayList;

public interface IDataBase {

    public void readDB();
    public void addToDB(View view);
    public void updateInDB(View view);
    public void deleteFromDB(View view);
}
