package com.teleaus.talenttorrentandroid.Model.Adapter;

public class StaticModel {
    private boolean success;
    private StaticData data;

    public StaticModel(boolean success, StaticData data) {
        this.success = success;
        this.data = data;
    }

    public StaticModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StaticData getData() {
        return data;
    }

    public void setData(StaticData data) {
        this.data = data;
    }
}
