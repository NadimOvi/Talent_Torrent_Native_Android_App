package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

public class IntentModel {
    private String id;
    private String client_secret;

    public IntentModel() {
    }

    public IntentModel(String id, String client_secret) {
        this.id = id;
        this.client_secret = client_secret;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
}
