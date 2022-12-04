package com.example.couplesbudgeting.services;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.couplesbudgeting.MainActivity;
import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.User;
import com.example.couplesbudgeting.ui.groupID.GroupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Used to communicated with the Firebase Groups table
 */
public class GroupsService {

    private final FirebaseFirestore db;

    public GroupsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createGroup(String groupId, Context currentContext, AppCompatActivity currentActivity) {
        // Searches for group Id first before creating new one
        db.collection("access_codes").document(groupId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null && documentSnapshot.exists()) {
                    Toast.makeText(currentContext, "Group already exists, try a different ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Creates a new access code
                    Map<String, String> access_code = new HashMap<>();
                    access_code.put("group_id", String.valueOf(UUID.randomUUID()));
                    db.collection("access_codes").document(groupId).set(access_code);

                    // Creates new groupID
                    Cache cache = Cache.getInstance();

                    ArrayList<String> users = new ArrayList<>();
                    users.add(cache.getUserId());

                    Map<String, Object> group = new HashMap<>();
                    group.put("access_code", groupId);
                    group.put("owner", cache.getUserId());
                    group.put("users", users);

                    db.collection("groups").document(Objects.requireNonNull(access_code.get("group_id")))
                            .set(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Cache.getInstance().setGroupId(groupId);
                                    new UsersService().updateGroupId(groupId,Cache.getInstance().getUserId());
                                    launchMainActivity(currentActivity);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void AddUserToGroup(String groupId, Context currentContext, AppCompatActivity currentActivity) {
        // Searches for group Id first before adding new user
        db.collection("access_codes").document(groupId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null && documentSnapshot.exists()) {
                    // Adds user to group
                    String newUserId = Cache.getInstance().getUserId();
                    db.collection("groups").document(Objects.requireNonNull(documentSnapshot.getString("group_id"))).update("users", FieldValue.arrayUnion(newUserId));
                    Cache.getInstance().setGroupId(groupId);
                    new UsersService().updateGroupId(groupId,Cache.getInstance().getUserId());
                    launchMainActivity(currentActivity);
                    Toast.makeText(currentContext,
                            String.format("Successfully added %s, to group id %s", newUserId, groupId), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(currentContext, "Group does not exist", Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void DeleteGroup(String groupId) {
        db.collection("groups").document(groupId).delete();
    }

    private void launchMainActivity(AppCompatActivity currentActivity) {
        Intent intent = new Intent(currentActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
    }

}
