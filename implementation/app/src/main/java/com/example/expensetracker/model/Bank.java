package com.example.expensetracker.model;

public class Bank implements IAccount{

    Account bank;

    @Override
    public Account create() {
        Account bank = new Account("BANK", 0.0);
        return bank;
    }

    public Account getBank() {
        return bank;
    }

}
