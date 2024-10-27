package com.example.myapplication.models;

public class ClassSchedule {
    private String className;
    private String startTime;
    private String endTime;
    private String day;
    private String room;
    private String instructor;

    public ClassSchedule(String className, String startTime, String endTime, String day, String room, String instructor) {
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.room = room;
        this.instructor = instructor;
    }

    public String getClassName() {
        return className;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public String getRoom() {
        return room;
    }

    public String getInstructor() {
        return instructor;
    }
}
