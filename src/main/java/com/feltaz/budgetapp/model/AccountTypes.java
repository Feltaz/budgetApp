package com.feltaz.budgetapp.model;

public enum AccountTypes {
    GENERAL("General"),
    CHECKING("Checking"),
    CASH("Cash"),
    SAVINGS("Savings");
    private String displayName;
    AccountTypes(String displayName){
        this.displayName=displayName;
    }
    @Override
    public String toString(){return displayName;}
}
