package com.example.couplesbudgeting.cache;

public class Cache {

    private static Cache instance = null;

    private String username = null;
    private String userId = null;
    private String groupId = null;

    private Cache() {}

    public static Cache getInstance() {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        this.groupId = groupId;
    }
}
