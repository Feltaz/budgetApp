package com.feltaz.budgetapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Record {
    int id;
    String category;
    Account account = new Account();
    double amount;
    LocalDate date;
    LocalTime time;
    Currencies currency;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCurrency(Currencies currency) {
        this.currency = currency;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    //public void setCategory(Categories category) {
      //  this.category = category;
    //}

    public Currencies getCurrency() {
        return currency;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String value) {
        this.category=value;
    }
}
