package com.teleaus.talenttorrentandroid.Model.Profile;

import com.teleaus.talenttorrentandroid.Model.Login.LoginData;

import java.io.Serializable;

public class ProfileMainModel implements Serializable {
    private String success;
    private profileData data;
    private String access_token;

    public ProfileMainModel() {
    }

    public ProfileMainModel(String success, profileData data, String access_token) {
        this.success = success;
        this.data = data;
        this.access_token = access_token;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public profileData getData() {
        return data;
    }

    public void setData(profileData data) {
        this.data = data;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
