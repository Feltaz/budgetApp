package com.feltaz.budgetapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Record {
    int id;
    String account;
    double amount;
    String category;
    public Record(int id,String account,double amount,String category) {
        this.id=id;
        this.account=account;
        this.amount=amount;
        this.category=category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}