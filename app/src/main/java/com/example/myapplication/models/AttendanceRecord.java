package com.example.myapplication.models;

public class AttendanceRecord {
    private String date;
    private boolean present;

    public AttendanceRecord() {} // Required for Firebase

    public AttendanceRecord(String date, boolean present) {
        this.date = date;
        this.present = present;
    }

    // Getters and Setters
}
