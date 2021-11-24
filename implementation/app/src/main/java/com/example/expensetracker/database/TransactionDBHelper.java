package com.example.expensetracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.expensetracker.activities.Subject;
import com.example.expensetracker.model.Account;
import com.example.expensetracker.model.Transaction;

import java.util.ArrayList;

public class TransactionDBHelper extends DBHelper {

    private Subject subject;

    public TransactionDBHelper(Context context) {
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

    public TransactionDBHelper(Context context, Subject subject) {
        super(context);
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public boolean addTransaction(String accountType, String transType, String category, double transValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("accountType", accountType);
        contantValues.put("transType", transType);
        contantValues.put("category", category);
        contantValues.put("transValue", transValue);
        db.insert("transactions", null, contantValues);
        db.close();

        updateAccountsAfterModification(accountType, transType, transValue);

        return true;
    }

    @Override
    public boolean updateTransaction(Integer transID, String accountType, String transType, String category, String newTransValue, Double transValue)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();

        if(!transType.equals("")) { contantValues.put("transType", transType); }
        if(!category.equals("")) { contantValues.put("category", category); }
        if(!transValue.equals("")) { contantValues.put("transValue", newTransValue); }

        db.update("transactions", contantValues, "transactionID = ?", new String[]{transID.toString()});
        db.close();

        String oldTransType = "";
        for(Transaction t : getAllTransactions()) {
            if(t.getTransID() == transID) {
                oldTransType = t.getTransType();
            }
        }

        if(deleteTransaction(transID, accountType, oldTransType, transValue) == 1) {
            addTransaction(accountType, transType, category, Double.parseDouble(newTransValue));
        }

        return true;
    }

    @Override
    public Integer deleteTransaction(Integer transID, String accountType, String transType, Double transValue){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("transactions","transactionID = ?", new String[]{Integer.toString(transID)});
        updateAccountsAfterModification(accountType, transType, transValue*(-1));
        return 1;
    }

    @Override
    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> arrayList= new ArrayList<Transaction>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from transactions",null);

        if (cursor.moveToFirst()) {
            do {
                Integer transID = cursor.getInt(cursor.getColumnIndex("transactionID"));
                String accountType = cursor.getString(cursor.getColumnIndex("accountType"));
                String transType = cursor.getString(cursor.getColumnIndex("transType"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                Double value = cursor.getDouble(cursor.getColumnIndex("transValue"));
                Transaction transaction = new Transaction(accountType, transType, category, value);
                transaction.setTransID(transID);
                arrayList.add(transaction);
            } while (cursor.moveToNext());
        }

        return arrayList;
    }

    @Override
    public ArrayList<Transaction> getTransactions(String accountName){
        ArrayList<Transaction> arrayList= new ArrayList<Transaction>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("Select * from transactions where accountType = ?", new String[] {accountName});

            if (cursor.moveToFirst()) {
                do {
                    Integer transID = cursor.getInt(cursor.getColumnIndex("transactionID"));
                    String accountType = cursor.getString(cursor.getColumnIndex("accountType"));
                    String transType = cursor.getString(cursor.getColumnIndex("transType"));
                    String category = cursor.getString(cursor.getColumnIndex("category"));
                    Double value = cursor.getDouble(cursor.getColumnIndex("transValue"));
                    Transaction transaction = new Transaction(accountType, transType, category, value);
                    transaction.setTransID(transID);
                    arrayList.add(transaction);
                } while (cursor.moveToNext());
            }
        }catch(RuntimeException r) {
            r.printStackTrace();
        }

        return arrayList;
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
    public void updateAccountsAfterModification(String accountType, String transType, Double transValue) {
        ArrayList<Account> accounts = getAllAccounts();
        double value = 0;
        for(Account a : accounts) {
            if(a.getAccountType().equals(accountType)) {
                value = a.getValue();
            }
        }
        if(transType.equals("Income")) {
            value += transValue;
        } else {
            value -= transValue;
        }
        updateAccount(accountType, "", value);
    }
}
