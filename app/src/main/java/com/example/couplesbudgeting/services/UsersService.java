package com.example.couplesbudgeting.services;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to communicated with the Firebase Users table
 */
public class UsersService {

    private final FirebaseFirestore db;


    public UsersService() {
         db = FirebaseFirestore.getInstance();
    }

    public void AddUser(User newUser) {
        System.out.println("Adding user");

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", newUser.getEmailAddress());
        user.put("group_id", null);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
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

    public void GetUser(String emailAddress) {
        System.out.println("Getting user");
        db.collection("users")
                .whereEqualTo("email", emailAddress)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cache cache = Cache.getInstance();
                                cache.setEmail(emailAddress);
                                if (document.getData().get("group_id") != null) {
                                    cache.setGroupId(document.getData().get("group_id").toString());
                                }
                                else {
                                    cache.setGroupId(null);
                                }

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
