package com.feltaz.budgetapp.model;

public enum Currencies {
    USD("USD"),
    EUR("EUR"),
    TND("TND");

    private String displayName;

    Currencies(String displayName) {
        this.displayName=displayName;
    }
    public String displayName(){return displayName;}

    @Override public String toString() {
        return displayName;
    }

}
