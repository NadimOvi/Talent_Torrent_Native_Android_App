package com.teleaus.talenttorrentandroid.Fragments.NotificationFragments;

public class NotificationData {
    private int id;
    private String message;
    private String status;
    private String url;
    private String created_at;

    public NotificationData() {
    }

    public NotificationData(int id, String message, String status, String url, String created_at) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.url = url;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
