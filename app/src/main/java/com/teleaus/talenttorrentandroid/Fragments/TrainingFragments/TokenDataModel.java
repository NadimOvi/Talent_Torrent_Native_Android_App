package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

public class TokenDataModel {
    private IntentModel intent;

    public TokenDataModel(IntentModel intent) {
        this.intent = intent;
    }

    public TokenDataModel() {
    }

    public IntentModel getIntent() {
        return intent;
    }

    public void setIntent(IntentModel intent) {
        this.intent = intent;
    }
}
