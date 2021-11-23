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

        updateAccountsAfterModification(accountType, transType, transValue);

        return true;
    }

    public boolean addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("category", category);
        db.insert("categories", null, contantValues);
        db.close();
        return true;
    }

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

    public boolean updateCategory(String category, String newCategory)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("category", newCategory);
        db.update("categories", contantValues, "category = ?", new String[]{category});
        db.close();
        return true;
    }

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

    public void deleteAccount(String accountType){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("accounts","accountType = ?", new String[]{accountType});
        db.delete("transactions","accountType = ?", new String[]{accountType});
    }

    public Integer deleteTransaction(Integer transID, String accountType, String transType, Double transValue){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("transactions","transactionID = ?", new String[]{Integer.toString(transID)});
        updateAccountsAfterModification(accountType, transType, transValue*(-1));
        return 1;
    }

    public Integer deleteCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("categories","category = ?", new String[]{category});
    }

    public Account getAccountData(String accountName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from accounts where accountType = ?", new String[] {accountName});
        Account ret = new Account(res.getString(res.getColumnIndex("accountType")), res.getDouble(res.getColumnIndex("accountBalance")));
        return ret;
    }

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

    public void updateTransactionsAfterModification(String accountType, String newAccountType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();

        contantValues.put("accountType", newAccountType);

        db.update("transactions", contantValues, "accountType = ?", new String[]{accountType});
        db.close();
    }
}
