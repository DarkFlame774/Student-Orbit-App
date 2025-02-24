package com.example.myapplication.models;

public class AttendanceSummary {
    private String subjectName;
    private double attendancePercentage; // Assuming percentage is a double

    public AttendanceSummary(String subjectName, double attendancePercentage) {
        this.subjectName = subjectName;
        this.attendancePercentage = attendancePercentage;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }
}
