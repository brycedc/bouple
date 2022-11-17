package com.example.couplesbudgeting.services;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.models.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * Used to communicated with the Firebase Transactions table
 */
public class TransactionsService {

    private FirebaseFirestore db;

    public TransactionsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createTransaction(Context context, String name, String category, String amount, Date state) {
        // Makes sure the database is initialized
        if(db == null) {
            db = FirebaseFirestore.getInstance();
        }

        // Creates a new transaction
        Transaction newTransaction = new Transaction(name, category, amount, state);

        // Adds the new transaction into the database
        db.collection("transaction").add(newTransaction).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "Your data was added successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
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
