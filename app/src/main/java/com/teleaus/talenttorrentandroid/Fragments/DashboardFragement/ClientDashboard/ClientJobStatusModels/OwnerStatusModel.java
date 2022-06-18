package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

public class OwnerStatusModel {
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String type;
    private ProfileStatus profile;
    private String verified_at;
    private String created_at;
    private String updated_at;

    public OwnerStatusModel() {
    }

    public OwnerStatusModel(int id, String first_name, String last_name, String username, String type, ProfileStatus profile, String verified_at, String created_at, String updated_at) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.type = type;
        this.profile = profile;
        this.verified_at = verified_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProfileStatus getProfile() {
        return profile;
    }

    public void setProfile(ProfileStatus profile) {
        this.profile = profile;
    }

    public String getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(String verified_at) {
        this.verified_at = verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
