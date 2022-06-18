package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard;

public class QualificationModel {
    private String showQualification;
    private String postQualification;

    public QualificationModel() {
    }

    public QualificationModel(String showQualification, String postQualification) {
        this.showQualification = showQualification;
        this.postQualification = postQualification;
    }

    public String getShowQualification() {
        return showQualification;
    }

    public void setShowQualification(String showQualification) {
        this.showQualification = showQualification;
    }

    public String getPostQualification() {
        return postQualification;
    }

    public void setPostQualification(String postQualification) {
        this.postQualification = postQualification;
    }
    @Override
    public String toString() {
        return showQualification;
    }
}
