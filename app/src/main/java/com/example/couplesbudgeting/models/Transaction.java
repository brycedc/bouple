package com.example.couplesbudgeting.models;

import java.util.Date;

/**
 * Model for a Transaction object
 */
public class Transaction {
    private String name;
    private String category;
    private Double amount;
    private Date date;

    public Transaction() {
    }

    public Transaction(String name, String category, Double amount, Date date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
