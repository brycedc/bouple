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

    public void createTransaction(Transaction newTransaction) {

    }

    public Transaction getTransaction() {

        return new Transaction();
    }

    public void updateTransaction(Transaction transactionToUpdate) {
        //Update DB

    }

    public void deleteTransaction(int transactionID) {

    }
}
