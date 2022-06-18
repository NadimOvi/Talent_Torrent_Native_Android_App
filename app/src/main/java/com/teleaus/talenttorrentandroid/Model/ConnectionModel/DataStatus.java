package com.teleaus.talenttorrentandroid.Model.ConnectionModel;

public class DataStatus {
    private int id;
    private int receiver_id;
    private String status;
    private String created_at;
    private String updated_at;

    public DataStatus(int id, int receiver_id, String status, String created_at, String updated_at) {
        this.id = id;
        this.receiver_id = receiver_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public DataStatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
