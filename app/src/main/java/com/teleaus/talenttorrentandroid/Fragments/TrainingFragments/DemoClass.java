package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

public class DemoClass {
    private boolean success;
    private String data;

    public DemoClass(boolean success, String data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
