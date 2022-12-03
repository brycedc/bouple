package com.example.couplesbudgeting.services;


import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to communicated with the Firebase Transactions table
 */
public class TransactionsService {

    public interface ITransactionsReturn {
        void onSuccess(List<Transaction> transactions);
    }

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

    /**
     * Get all transactions between two dates
     * @param earlyDate Date to start fetch
     * @param lateDate Date to end fetch
     * @param reportsTransactions Used to return the values
     */
    public void getTransactionInTimeframe(Date earlyDate, Date lateDate, ITransactionsReturn reportsTransactions) {
        db.collection("transactions")
                .whereEqualTo("group_id", Cache.getInstance().getGroupId())
                .whereGreaterThan("date", earlyDate)
                .whereLessThan("date", lateDate)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Transaction> transactions = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String name = document.getData().get("name").toString();
                            String category = document.getData().get("category").toString();
                            double amount = Double.parseDouble(document.getData().get("amount").toString());
                            long milliseconds = Long.parseLong(document.getData().get("milliseconds").toString());

                            Date date = new Date(milliseconds);

                            Transaction newTransaction = new Transaction(name, category, amount, date);
                            transactions.add(newTransaction);
                        }
                        reportsTransactions.onSuccess(transactions);
                    }
                });
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
        returnTransaction.put("milliseconds", transaction.getDate().getTime());
        returnTransaction.put("user_id", cache.getUserId());
        returnTransaction.put("group_id", cache.getGroupId());
        return returnTransaction;
    }
}
