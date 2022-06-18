package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

import java.util.List;

public class JobStatusMainModel {
    private boolean success;
    private String status;
    private List<JobStatusData> data;
    private CountStatus count;

    public JobStatusMainModel(boolean success, String status, List<JobStatusData> data, CountStatus count) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.count = count;
    }

    public JobStatusMainModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JobStatusData> getData() {
        return data;
    }

    public void setData(List<JobStatusData> data) {
        this.data = data;
    }

    public CountStatus getCount() {
        return count;
    }

    public void setCount(CountStatus count) {
        this.count = count;
    }
}
