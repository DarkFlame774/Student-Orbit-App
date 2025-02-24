package com.example.myapplication.models;

public class AttendanceRecord {
    private String subjectName;
    private int lectureNumber;
    private boolean isPresent;
    private String attendanceTime;

    public AttendanceRecord(String subjectName, int lectureNumber, boolean isPresent, String attendanceTime) {
        this.subjectName = subjectName;
        this.lectureNumber = lectureNumber;
        this.isPresent = isPresent;
        this.attendanceTime = attendanceTime;
    }

    public String getSubjectName() { return subjectName; }
    public int getLectureNumber() { return lectureNumber; }
    public boolean isPresent() { return isPresent; }
    public String getAttendanceTime() { return attendanceTime; }
}
