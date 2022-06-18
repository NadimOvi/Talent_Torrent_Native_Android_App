package com.teleaus.talenttorrentandroid.Model.ConnectionModel;

public class StatusModel {
    private boolean success;
    private DataStatus data;
    private String message;

    public StatusModel() {
    }

    public StatusModel(boolean success, DataStatus data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataStatus getData() {
        return data;
    }

    public void setData(DataStatus data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
