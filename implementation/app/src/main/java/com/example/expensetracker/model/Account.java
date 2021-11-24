package com.example.expensetracker.model;

public class Account {

    private String account_type;
    private Double value;

    public Account(String account_type, Double value) {
        this.account_type = account_type;
        this.value = value;
    }

    public String getAccountType() {
        return account_type;
    }

    public Double getValue() {
        return value;
    }

}
