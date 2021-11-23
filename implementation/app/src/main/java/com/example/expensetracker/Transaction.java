package com.example.expensetracker;

public class Transaction {

    private int transID;
    private String accountName;
    private String transType;
    private String category;
    private Double value;

    public Transaction(String accountName, String transType, String category, Double value) {
        this.accountName = accountName;
        this.transType = transType;
        this.category = category;
        this.value = value;
    }

    public Integer getTransID() {return transID; }
    public void setTransID(Integer transID) {this.transID = transID; }

    public String getAccountName() {
        return accountName;
    }

    public String getTransType() {
        return transType;
    }

    public String getCategory() {
        return category;
    }

    public Double getValue() {
        return value;
    }


}
