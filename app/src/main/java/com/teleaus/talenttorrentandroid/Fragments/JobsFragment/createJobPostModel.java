package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class createJobPostModel {
    private String title;
    private String type;
    private int category_id;
    ArrayList<String> tags;
    private String end_date;
    private String description;
    private int budget;

    public createJobPostModel() {
    }

    public createJobPostModel(String title, String type, int category_id, ArrayList<String> tags, String end_date, String description, int budget) {
        this.title = title;
        this.type = type;
        this.category_id = category_id;
        this.tags = tags;
        this.end_date = end_date;
        this.description = description;
        this.budget = budget;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
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
}
