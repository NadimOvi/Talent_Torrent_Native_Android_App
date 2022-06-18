package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

import java.util.ArrayList;

public class AppliedExpertModel {
    private boolean success;
    private ArrayList<AppliedProfileData> data;

    public AppliedExpertModel() {
    }

    public AppliedExpertModel(boolean success, ArrayList<AppliedProfileData> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<AppliedProfileData> getData() {
        return data;
    }

    public void setData(ArrayList<AppliedProfileData> data) {
        this.data = data;
    }
}
