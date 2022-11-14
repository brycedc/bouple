package com.example.couplesbudgeting.services;


import com.example.couplesbudgeting.models.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * Used to communicated with the Firebase Transactions table
 */
public class TransactionsService {

    private final FirebaseFirestore db;

    public TransactionsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createTransaction(String name, String category, Double amount, Date state) {
        Transaction newTransaction = new Transaction(name, category, amount, state);
    }

    public Transaction getTransaction() {

        return new Transaction();
    }

    public void updateTransaction(int transactionID) {
        //Get Transaction to update
        //Transaction transactionToUpdate = //Call to DB

        //Update object
//        transactionToUpdate.setName();
//        transactionToUpdate.setCategory();
//        transactionToUpdate.setAmount();
//        transactionToUpdate.setDate();

        //Update DB

    }

    public void deleteTransaction(int transactionID) {

    }
}
