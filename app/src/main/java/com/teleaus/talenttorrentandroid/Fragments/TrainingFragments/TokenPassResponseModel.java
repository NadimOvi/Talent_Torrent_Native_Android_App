package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

public class TokenPassResponseModel {
    private boolean success;
    private TokenDataModel data;

    public TokenPassResponseModel(boolean success, TokenDataModel data) {
        this.success = success;
        this.data = data;
    }

    public TokenPassResponseModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public TokenDataModel getData() {
        return data;
    }

    public void setData(TokenDataModel data) {
        this.data = data;
    }
}
