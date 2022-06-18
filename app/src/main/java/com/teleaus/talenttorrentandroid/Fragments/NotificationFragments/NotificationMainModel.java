package com.teleaus.talenttorrentandroid.Fragments.NotificationFragments;

import java.util.ArrayList;

public class NotificationMainModel {
    private boolean success;
    private ArrayList<NotificationData> data;

    public NotificationMainModel() {
    }

    public NotificationMainModel(boolean success, ArrayList<NotificationData> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<NotificationData> getData() {
        return data;
    }

    public void setData(ArrayList<NotificationData> data) {
        this.data = data;
    }
}
