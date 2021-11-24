package com.example.expensetracker.model;

public class Card implements IAccount {

    Account card;

    @Override
    public Account create() {
        Account card = new Account("CARD", 0.0);
        return card;
    }

    public Account getCard() {
        return card;
    }

}
