package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ExpertDashboard;

public class ExperienceModel {
    private String showExperience;
    private String postExperience;

    public ExperienceModel(String showExperience, String postExperience) {
        this.showExperience = showExperience;
        this.postExperience = postExperience;
    }

    public String getShowExperience() {
        return showExperience;
    }

    public void setShowExperience(String showExperience) {
        this.showExperience = showExperience;
    }

    public String getPostExperience() {
        return postExperience;
    }

    public void setPostExperience(String postExperience) {
        this.postExperience = postExperience;
    }

    @Override
    public String toString() {
        return showExperience;
    }
}
