package com.example.couplesbudgeting.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.Goal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to communicated with the Firebase Goals table
 */
public class GoalsService {

    private final FirebaseFirestore db;

    public GoalsService() {
        db = FirebaseFirestore.getInstance();
    }

    public void createGoal(Goal newGoal) {
        Cache cache = Cache.getInstance();

        Map<String, Object> goal = new HashMap<>();
        goal.put("name", newGoal.getName());
        goal.put("deadline", newGoal.getDeadline());
        goal.put("amount", newGoal.getAmount());
        goal.put("is_complete", newGoal.isComplete());
        goal.put("completed", newGoal.getCompleted());
        goal.put("group_id", cache.getGroupId());

        db.collection("goals").add(goal);
    }

    public void getUsersGoals(String groupId) {
        db.collection("goals").whereEqualTo("group_id", groupId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Goal> goals = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String goalName = document.getString("name");
                                Double totalAmount = document.getDouble("amount");
                                Double completed = document.getDouble("completed");
                                Date endDate = document.getDate("deadline");
                                goals.add(new Goal(goalName, totalAmount, completed, endDate));
                            }
                        }
                    }
                });
    }

    public void updateGoalCompleted(String goalId, Goal goal) {
        Cache cache = Cache.getInstance();
        Map<String, Object> newGoal = new HashMap<>();
        newGoal.put("name", goal.getName());
        newGoal.put("deadline", goal.getDeadline());
        newGoal.put("amount", goal.getAmount());
        newGoal.put("is_complete", goal.isComplete());
        newGoal.put("completed", goal.getCompleted());
        newGoal.put("group_id", cache.getGroupId());

        db.collection("goals").document(goalId).update(newGoal);
    }
}
