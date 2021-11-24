package com.example.expensetracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensetracker.model.Account;
import com.example.expensetracker.model.Transaction;

import java.util.ArrayList;

public abstract class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "database.sqlite" , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table accounts" +
                "(accountID integer primary key autoincrement, accountType text, accountBalance double)" );
        db.execSQL("create table transactions" +
                "(transactionID integer primary key autoincrement, accountType text, transType text, category text, transValue double)" );
        db.execSQL("create table categories" +
                "(categoryID integer primary key autoincrement, category text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS categories");
        onCreate(db);
    }

    public abstract boolean addAccount(String accountType, double value);
    public abstract boolean updateAccount(String accountType, String newAccountType, double value);
    public abstract void deleteAccount(String accountType);
    public abstract ArrayList<Account> getAllAccounts();

    public abstract boolean addTransaction(String accountType, String transType, String category, double transValue);
    public abstract boolean updateTransaction(Integer transID, String accountType, String transType, String category, String newTransValue, Double transValue);
    public abstract Integer deleteTransaction(Integer transID, String accountType, String transType, Double transValue);
    public abstract ArrayList<Transaction> getAllTransactions();
    public abstract ArrayList<Transaction> getTransactions(String accountName);

    public abstract boolean addCategory(String category);
    public abstract boolean updateCategory(String category, String newCategory);
    public abstract Integer deleteCategory(String category);
    public abstract ArrayList<String> getAllCategories();

    public void updateTransactionsAfterModification(String accountType, String newAccountType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();

        contantValues.put("accountType", newAccountType);

        db.update("transactions", contantValues, "accountType = ?", new String[]{accountType});
        db.close();
    }

    public abstract void updateAccountsAfterModification(String accountType, String transType, Double transValue);
}
