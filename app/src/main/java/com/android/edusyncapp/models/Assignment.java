package com.android.edusyncapp.models;

public class Assignment {
    private String id;
    private String userId;
    private String subject;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String addDate;

    public Assignment() {
    }

    public Assignment(String userId, String subject, String title, String description, String dueDate, String status) {
        this.userId = userId;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Assignment(String id, String userId, String subject, String title, String description, String dueDate, String status, String addDate) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.addDate = addDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAddDate() {
        return addDate;
    }
    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
