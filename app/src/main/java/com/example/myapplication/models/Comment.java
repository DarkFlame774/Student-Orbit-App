package com.example.myapplication.models;

import com.google.firebase.Timestamp;

public class Comment {
    private String commentId;
    private String queryId;
    private String userId;
    private String text;
    private Timestamp timestamp;

    public Comment() {}

    public Comment(String commentId, String queryId, String userId, String content, Timestamp timestamp) {
        this.commentId = commentId;
        this.queryId = queryId;
        this.userId = userId;
        this.text = content;
        this.timestamp = timestamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getQueryId() {
        return queryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.text = content;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
