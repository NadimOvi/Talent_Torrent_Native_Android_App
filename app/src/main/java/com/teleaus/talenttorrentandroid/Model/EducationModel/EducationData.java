package com.teleaus.talenttorrentandroid.Model.EducationModel;

import java.io.Serializable;

public class EducationData implements Serializable {
    private Integer id;
    private Integer user_id;
    private String institute;
    private String degree;
    private String field_of_study;
    private String start_year;
    private String end_year;
    private String grade;
    private String activities;
    private String description;

    public EducationData() {
    }

    public EducationData(Integer id, Integer user_id, String institute, String degree, String field_of_study, String start_year, String end_year, String grade, String activities, String description) {
        this.id = id;
        this.user_id = user_id;
        this.institute = institute;
        this.degree = degree;
        this.field_of_study = field_of_study;
        this.start_year = start_year;
        this.end_year = end_year;
        this.grade = grade;
        this.activities = activities;
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

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getField_of_study() {
        return field_of_study;
    }

    public void setField_of_study(String field_of_study) {
        this.field_of_study = field_of_study;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getEnd_year() {
        return end_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
