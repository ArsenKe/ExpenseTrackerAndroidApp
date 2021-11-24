package com.example.expensetracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.activities.Subject;
import com.example.expensetracker.model.Account;
import com.example.expensetracker.model.Transaction;

import java.util.ArrayList;

public class CategoryDBHelper extends DBHelper {

    private Subject subject;

    public CategoryDBHelper(Context context) {
        super(context);
    }

    @Override
    public boolean addAccount(String accountType, double value) {
        return false;
    }

    @Override
    public boolean updateAccount(String accountType, String newAccountType, double value) {
        return false;
    }

    @Override
    public void deleteAccount(String accountType) {

    }

    @Override
    public ArrayList<Account> getAllAccounts() {
        return null;
    }

    @Override
    public boolean addTransaction(String accountType, String transType, String category, double transValue) {
        return false;
    }

    @Override
    public boolean updateTransaction(Integer transID, String accountType, String transType, String category, String newTransValue, Double transValue) {
        return false;
    }

    @Override
    public Integer deleteTransaction(Integer transID, String accountType, String transType, Double transValue) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactions(String accountName) {
        return null;
    }

    public CategoryDBHelper(Context context, Subject subject) {
        super(context);
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public boolean addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("category", category);
        db.insert("categories", null, contantValues);
        db.close();
        return true;
    }

    @Override
    public boolean updateCategory(String category, String newCategory)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("category", newCategory);
        db.update("categories", contantValues, "category = ?", new String[]{category});
        db.close();
        return true;
    }

    @Override
    public Integer deleteCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("categories","category = ?", new String[]{category});
    }

    @Override
    public ArrayList<String> getAllCategories(){
        ArrayList<String> arrayList= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from categories",null);

        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndex("category"));
                arrayList.add(category);
            } while (cursor.moveToNext());
        }

        return arrayList;
    }

    @Override
    public void updateAccountsAfterModification(String accountType, String transType, Double transValue) {

    }
}
