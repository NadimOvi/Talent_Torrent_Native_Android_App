package com.teleaus.talenttorrentandroid.Fragments.RegistrationFragments;

public class UserTypeModel {
    private String typePostName;
    private String typeShowName;

    public UserTypeModel(String typePostName, String typeShowName) {
        this.typePostName = typePostName;
        this.typeShowName = typeShowName;
    }

    public UserTypeModel() {
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
