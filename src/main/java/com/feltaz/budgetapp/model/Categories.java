package com.feltaz.budgetapp.model;

public enum Categories {
    FOODANDDRINKS("Food & drinks"),
    TRANSPORTATION("Transportation"),
    ENTERTAINMENT("Entertainment"),
    INCOME("income"),
    FINANCIAL("Financial Expenses");

    private String displayName;

    Categories(String displayName){
        this.displayName=displayName;
    }

    @Override
    public String toString(){return displayName;}
}
