package com.example.couplesbudgeting.models;

import java.util.Date;

public class Goal {

    private String name;
    private double amount;
    private double completed;
    private Date deadline;

    public Goal(String name, double amount, double completed, Date deadline) {
        this.name = name;
        this.amount = amount;
        this.completed = completed;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCompleted() {
        return completed;
    }

    public void setCompleted(double completed) {
        this.completed = completed;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
