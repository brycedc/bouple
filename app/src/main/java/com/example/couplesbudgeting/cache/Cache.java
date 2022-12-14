package com.example.couplesbudgeting.cache;

public class Cache {

    private static Cache instance = null;

    private String userId = null;
    private String email = null;
    private String groupId = null;



    private Cache() {}

    public static Cache getInstance() {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        System.out.println("Setting group id to: " + groupId);
        this.groupId = groupId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        System.out.println("Setting email to: " + email);
        this.email = email;
    }
}
