package com.example.couplesbudgeting.services;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to communicated with the Firebase Transactions table
 */
public class TransactionsService {

    private final FirebaseFirestore db;

    public TransactionsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createTransaction(String name, String category, Double amount, Date date) {
//        Transaction newTransaction = new Transaction(name, category, amount, date);
        Cache cache = Cache.getInstance();

        Map<String, Object> transaction = new HashMap<>();
        transaction.put("name", name);
        transaction.put("category", category);
        transaction.put("amount", amount);
        transaction.put("date", date);
        transaction.put("user_id", cache.getUserId());
        transaction.put("group_id", cache.getGroupId());

        db.collection("transactions")
                .add(transaction)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
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
