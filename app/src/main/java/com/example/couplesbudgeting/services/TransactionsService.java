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

    public void createTransaction(Transaction newTransaction) {
        Map<String, Object> transaction = createTransactionMap(newTransaction);

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

    public void updateTransaction(String transactionId, Transaction updatedTransaction) {
        Map<String, Object> transaction = createTransactionMap(updatedTransaction);

        db.collection("transactions").document(transactionId).update(transaction);

    }

    public void deleteTransaction(String transactionId) {
        db.collection("transactions").document(transactionId).delete();
    }

    private Map<String, Object> createTransactionMap(Transaction transaction) {
        Cache cache = Cache.getInstance();
        Map<String, Object> returnTransaction = new HashMap<>();
        returnTransaction.put("name", transaction.getName());
        returnTransaction.put("category", transaction.getCategory());
        returnTransaction.put("amount", transaction.getAmount());
        returnTransaction.put("date", transaction.getDate());
        returnTransaction.put("user_id", cache.getUserId());
        returnTransaction.put("group_id", cache.getGroupId());
        return returnTransaction;
    }
}
