package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private List<AttendanceRecord> attendance;
    private String monday, tuesday, wednesday, thursday, friday;

    public Student() {} // Required for Firebase

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.attendance = new ArrayList<>();
    }

    // Getters and Setters
}

