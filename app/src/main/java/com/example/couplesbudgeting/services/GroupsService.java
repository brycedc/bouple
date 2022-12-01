package com.example.couplesbudgeting.services;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.cache.Cache;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to communicated with the Firebase Groups table
 */
public class GroupsService {

    private final FirebaseFirestore db;

    public GroupsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void CreateGroup(String accessCode) {
        Cache cache = Cache.getInstance();

        ArrayList<String> users = new ArrayList<>();
        users.add(cache.getEmail());

        Map<String, Object> group = new HashMap<>();
        group.put("access_code", accessCode);
        group.put("owner", cache.getEmail());
        group.put("users", users);

        db.collection("groups")
                .add(group)
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

    public void AddUserToGroup(String groupId, String newUserEmail) {
        db.collection("groups").document(groupId).update("users", FieldValue.arrayUnion(newUserEmail));
    }

    public void DeleteGroup(String groupId) {
        db.collection("groups").document(groupId).delete();
    }

}
