package com.teleaus.talenttorrentandroid.Fragments.JobsFragment;

public class JobTypeModel {
    private String typePostName;
    private String typeShowName;

    public JobTypeModel() {
    }

    public JobTypeModel(String typePostName, String typeShowName) {
        this.typePostName = typePostName;
        this.typeShowName = typeShowName;
    }

    public String getTypePostName() {
        return typePostName;
    }

    public void setTypePostName(String typePostName) {
        this.typePostName = typePostName;
    }

    public String getTypeShowName() {
        return typeShowName;
    }

    public void setTypeShowName(String typeShowName) {
        this.typeShowName = typeShowName;
    }

    @Override
    public String toString() {
        return typeShowName;
    }
}
