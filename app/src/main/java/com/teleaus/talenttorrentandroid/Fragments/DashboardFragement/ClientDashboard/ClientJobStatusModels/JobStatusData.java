package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

import java.util.ArrayList;

public class JobStatusData {
    private int id;
    private String uuid;
    private String assigned_to;
    private String title;
    private String description;
    private String budget;
    private String confirmed_budget;
    private String type;
    private String length;
    private String status;
    private String estimated_end_date;
    private String end_date;
    private OwnerStatusModel owner;
    private CategoryStatusModel category;
    private ArrayList<String> tags;
    private String created_at;
    private String updated_at;

    public JobStatusData() {
    }

    public JobStatusData(int id, String uuid, String assigned_to, String title, String description, String budget, String confirmed_budget, String type, String length, String status, String estimated_end_date, String end_date, OwnerStatusModel owner, CategoryStatusModel category, ArrayList<String> tags, String created_at, String updated_at) {
        this.id = id;
        this.uuid = uuid;
        this.assigned_to = assigned_to;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.confirmed_budget = confirmed_budget;
        this.type = type;
        this.length = length;
        this.status = status;
        this.estimated_end_date = estimated_end_date;
        this.end_date = end_date;
        this.owner = owner;
        this.category = category;
        this.tags = tags;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getConfirmed_budget() {
        return confirmed_budget;
    }

    public void setConfirmed_budget(String confirmed_budget) {
        this.confirmed_budget = confirmed_budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimated_end_date() {
        return estimated_end_date;
    }

    public void setEstimated_end_date(String estimated_end_date) {
        this.estimated_end_date = estimated_end_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public OwnerStatusModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerStatusModel owner) {
        this.owner = owner;
    }

    public CategoryStatusModel getCategory() {
        return category;
    }

    public void setCategory(CategoryStatusModel category) {
        this.category = category;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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
