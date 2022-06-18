package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

public class CategoryStatusModel {
    private int id;
    private String name;
    private String parent_id;
    private int is_active;
    private int created_at;
    private int updated_at;

    public CategoryStatusModel() {
    }

    public CategoryStatusModel(int id, String name, String parent_id, int is_active, int created_at, int updated_at) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }
}
