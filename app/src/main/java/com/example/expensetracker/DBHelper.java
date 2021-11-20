package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ACCOUNT_ID  = "accountID";
    public static final String COLUMN_ACCOUNT_TYPE = "accountType";
    public static final String COLUMN_ACCOUNT_BALANCE = "accountBalance";

    public DBHelper(Context context) {
        super(context, "accounts.sqlite" , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table accounts" +
                "(accountID integer primary key autoincrement, accountType text, accountBalance double)" );
        //db.execSQL("create table transactions" +
        //      "(accountID integer primary key autoincrement, accountType text, transType text, category text, transValue double)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        //db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }

    public boolean addAccount(String accountType, double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("accountType", accountType);
        contantValues.put("accountBalance", value);
        db.insert("accounts", null, contantValues);
        db.close();
        return true;
    }

    public boolean addTransaction(String accountType, String transType, String category, double transValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("accountType", accountType);
        contantValues.put("transType", transType);
        contantValues.put("category", category);
        contantValues.put("transValue", transValue);
        db.insert("transactions", null, contantValues);
        db.close();
        return true;
    }

    public boolean updateAccount(int accountID, String accountType, double value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("accountId", accountID);
        contantValues.put("accountType", accountType);
        contantValues.put("accountBalance", value);
        db.update("transactions", contantValues, "id = ?", new String[]{Integer.toString(accountID)});
        db.close();
        return true;
    }

    public Integer deleteAccount(Integer accountID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("accounts","id = ?", new String[]{Integer.toString(accountID)});
    }

    public Cursor getData(int accountID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from accounts where accountID = " + accountID + "", null);
        return res;
    }

    public ArrayList<String> getAllAccounts(){
        ArrayList<String> arraylist= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accounts",null);

        if (cursor.moveToFirst()) {
            do {
                arraylist.add(cursor.getString(cursor.getColumnIndex("accountType")));
            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}
