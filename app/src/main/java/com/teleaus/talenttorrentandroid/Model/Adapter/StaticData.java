package com.teleaus.talenttorrentandroid.Model.Adapter;

public class StaticData {
    private Integer job_posted;
    private Integer job_completed;
    private Integer expert_connected;
    private Integer business_connected;
    private Integer training_completed;

    public StaticData() {
    }

    public StaticData(Integer job_posted, Integer job_completed, Integer expert_connected, Integer business_connected, Integer training_completed) {
        this.job_posted = job_posted;
        this.job_completed = job_completed;
        this.expert_connected = expert_connected;
        this.business_connected = business_connected;
        this.training_completed = training_completed;
    }

    public Integer getJob_posted() {
        return job_posted;
    }

    public void setJob_posted(Integer job_posted) {
        this.job_posted = job_posted;
    }

    public Integer getJob_completed() {
        return job_completed;
    }

    public void setJob_completed(Integer job_completed) {
        this.job_completed = job_completed;
    }

    public Integer getExpert_connected() {
        return expert_connected;
    }

    public void setExpert_connected(Integer expert_connected) {
        this.expert_connected = expert_connected;
    }

    public Integer getBusiness_connected() {
        return business_connected;
    }

    public void setBusiness_connected(Integer business_connected) {
        this.business_connected = business_connected;
    }

    public Integer getTraining_completed() {
        return training_completed;
    }

    public void setTraining_completed(Integer training_completed) {
        this.training_completed = training_completed;
    }
}
