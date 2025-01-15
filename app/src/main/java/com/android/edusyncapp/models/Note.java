package com.android.edusyncapp.models;

import java.io.Serializable;
import java.util.List;

public class Note implements Serializable {
    private String id;
    private String userId;
    private String title;
    private String description;
    private List<String> imageUrls;
    private String editTime;

    public Note() {
    }

    public Note(String id, String userId, String title, String description, List<String> imageUrls, String editTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.editTime = editTime;
    }

    public Note(String userId, String title, String description, List<String> imageUrls, String editTime) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.editTime = editTime;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }
}
