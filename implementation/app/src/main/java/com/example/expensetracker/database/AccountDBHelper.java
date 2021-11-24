package com.example.expensetracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.activities.Subject;
import com.example.expensetracker.model.Account;
import com.example.expensetracker.model.Transaction;

import java.util.ArrayList;

public class AccountDBHelper extends DBHelper {

    private Subject subject;

    public AccountDBHelper(Context context) {
        super(context);
    }

    public AccountDBHelper(Context context, Subject subject) {

        super(context);
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public boolean addAccount(String accountType, double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("accountType", accountType);
        contantValues.put("accountBalance", value);
        db.insert("accounts", null, contantValues);
        db.close();
        return true;
    }

    @Override
    public boolean updateAccount(String accountType, String newAccountType, double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        if(newAccountType.equals("")) {
            contantValues.put("accountType", accountType);
        } else {
            contantValues.put("accountType", newAccountType);
        }
        contantValues.put("accountBalance", value);
        db.update("accounts", contantValues, "accountType = ?", new String[]{accountType});
        db.close();

        if(!newAccountType.equals("")) { updateTransactionsAfterModification(accountType, newAccountType); }
        return true;
    }

    /*
        public boolean updateAccount(String accountType, String newAccountType, double value)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contantValues = new ContentValues();
            if(newAccountType.equals("")) {
                contantValues.put("accountType", accountType);
            } else {
                contantValues.put("accountType", newAccountType);
            }
            contantValues.put("accountBalance", value);
            db.update("accounts", contantValues, "accountType = ?", new String[]{accountType});
            db.close();

            if(!newAccountType.equals("")) { updateTransactionsAfterModification(accountType, newAccountType); }
            return true;
        }
    */
    @Override
    public void deleteAccount(String accountType){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("accounts","accountType = ?", new String[]{accountType});
        db.delete("transactions","accountType = ?", new String[]{accountType});
    }

    @Override
    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> arrayList= new ArrayList<Account>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accounts",null);

        if (cursor.moveToFirst()) {
            do {
                String accountType = cursor.getString(cursor.getColumnIndex("accountType"));
                Double value = cursor.getDouble(cursor.getColumnIndex("accountBalance"));
                Account account = new Account(accountType, value);
                arrayList.add(account);
            } while (cursor.moveToNext());
        }

        return arrayList;
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

    @Override
    public boolean addCategory(String category) {
        return false;
    }

    @Override
    public boolean updateCategory(String category, String newCategory) {
        return false;
    }

    @Override
    public Integer deleteCategory(String category) {
        return null;
    }

    @Override
    public ArrayList<String> getAllCategories() {
        return null;
    }

    @Override
    public void updateAccountsAfterModification(String accountType, String transType, Double transValue) {}

}
