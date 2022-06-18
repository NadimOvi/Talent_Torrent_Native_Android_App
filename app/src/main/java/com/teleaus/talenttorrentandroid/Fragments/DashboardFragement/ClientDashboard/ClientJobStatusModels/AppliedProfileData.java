package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

public class AppliedProfileData {
    private int id;
    private String budget;
    private String message;
    private String status;
    private int user_id;
    private AppliedUserModel user;

    public AppliedProfileData() {
    }

    public AppliedProfileData(int id, String budget, String message, String status, int user_id, AppliedUserModel user) {
        this.id = id;
        this.budget = budget;
        this.message = message;
        this.status = status;
        this.user_id = user_id;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public AppliedUserModel getUser() {
        return user;
    }

    public void setUser(AppliedUserModel user) {
        this.user = user;
    }
}
