package com.android.edusyncapp.models;

public class Timetable_Event {
    private String id;
    private String startTime;
    private String endTime;
    private String eventName;
    private String eventLocation;
    private String date;
    private String studentId;

    public Timetable_Event() {
    }

    public Timetable_Event(String id, String startTime, String endTime, String eventName, String eventLocation, String date, String studentId) {
        id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.date = date;
        this.studentId = studentId;
    }

    public Timetable_Event(String startTime, String endTime, String eventName, String eventLocation, String date, String studentId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.date = date;
        this.studentId = studentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
}
