package com.example.couplesbudgeting.services;

import com.example.couplesbudgeting.cache.Cache;
import com.example.couplesbudgeting.models.Goal;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
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
        goal.put("completed", newGoal.getCompleted());
        goal.put("group_id", cache.getGroupId());

        db.collection("goals").add(goal);
    }

    public void updateGoalCompleted(String goalId, double completed) {
        db.collection("goals").document(goalId).update("completed", completed);
    }
}
