package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

import java.util.ArrayList;

public class draftedJobPostModel {
    private String title;
    private String type;
    private int category_id;
    ArrayList<Tags> tags;
    private String end_date;
    private String description;
    private int budget;
    private String post_type;

    public draftedJobPostModel() {
    }

    public draftedJobPostModel(String title, String type, int category_id, ArrayList<Tags> tags, String end_date, String description, int budget, String post_type) {
        this.title = title;
        this.type = type;
        this.category_id = category_id;
        this.tags = tags;
        this.end_date = end_date;
        this.description = description;
        this.budget = budget;
        this.post_type = post_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public ArrayList<Tags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }
}
