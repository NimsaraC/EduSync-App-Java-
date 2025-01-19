package com.android.edusyncapp.models;

import java.io.Serializable;

public class Event implements Serializable {
    private String id;
    private String title;
    private String date;
    private String time;
    private String location;
    private String description;

    public Event() {
    }

    public Event(String title, String date, String time, String location, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public Event(String id, String title, String date, String time, String location, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
