package com.example.expensetracker.activities;

import com.example.expensetracker.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<DBHelper> observers = new ArrayList<DBHelper>();
    private String accountType;
    private String transType;
    private Double transValue;

    public void setState(String accountType, String transType, Double transValue) {
        this.accountType = accountType;
        this.transType = transType;
        this.transValue = transValue;
        notifyAllObservers();
    }

    public void attach(DBHelper observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (DBHelper observer : observers) {
            observer.updateAccountsAfterModification(accountType, transType, transValue);
        }
    }
}