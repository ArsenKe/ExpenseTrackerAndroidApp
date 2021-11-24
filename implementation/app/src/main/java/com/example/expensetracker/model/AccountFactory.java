package com.example.expensetracker.model;

public class AccountFactory {

    //use getShape method to get object of type shape
    public IAccount getAccount(String accountType){
        if(accountType == null){
            return null;
        }
        if(accountType.equalsIgnoreCase("BANK")){
            return new Bank();

        } else if(accountType.equalsIgnoreCase("CASH")){
            return new Cash();

        } else if(accountType.equalsIgnoreCase("CARD")){
            return new Card();
        }

        return null;
    }
}
