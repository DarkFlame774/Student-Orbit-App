package com.example.myapplication.models;

import com.google.firebase.Timestamp;

public class Query {
    private String queryId;
    private String title;
    private String description;
    private String userId;
    private Timestamp timestamp;

    public Query() {}  // Firestore requires a no-argument constructor

    public Query(String queryId, String title, String description, String userId, Timestamp timestamp) {
        this.queryId = queryId;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
