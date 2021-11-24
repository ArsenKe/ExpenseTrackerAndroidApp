package com.example.expensetracker.model;

public class Cash implements IAccount {

    Account cash;

    @Override
    public Account create() {
        Account cash = new Account("CASH", 0.0);
        return cash;
    }

    public Account getCash() {
        return cash;
    }

}
