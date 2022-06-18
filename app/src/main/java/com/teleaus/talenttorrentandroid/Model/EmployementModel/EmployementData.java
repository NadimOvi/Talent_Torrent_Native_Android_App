package com.teleaus.talenttorrentandroid.Model.EmployementModel;

import java.io.Serializable;

public class EmployementData implements Serializable {

    private Integer id;
    private Integer user_id;
    private String company_name;
    private String job_title;
    private String location;
    private String start_date;
    private String end_date;
    private boolean current;
    private String work_type;
    private String description;

    public EmployementData() {
    }

    public EmployementData(Integer id, Integer user_id, String company_name, String job_title, String location, String start_date, String end_date, boolean current, String work_type, String description) {
        this.id = id;
        this.user_id = user_id;
        this.company_name = company_name;
        this.job_title = job_title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.current = current;
        this.work_type = work_type;
        this.description = description;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
