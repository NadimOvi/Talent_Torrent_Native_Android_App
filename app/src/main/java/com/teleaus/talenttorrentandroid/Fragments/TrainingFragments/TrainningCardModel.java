package com.teleaus.talenttorrentandroid.Fragments.TrainingFragments;

public class TrainningCardModel {
    private String cardPostName;
    private String cardShowName;

    public TrainningCardModel(String cardPostName, String cardShowName) {
        this.cardPostName = cardPostName;
        this.cardShowName = cardShowName;
    }

    public TrainningCardModel() {
    }

    public String getCardPostName() {
        return cardPostName;
    }

    public void setCardPostName(String cardPostName) {
        this.cardPostName = cardPostName;
    }

    public String getCardShowName() {
        return cardShowName;
    }

    public void setCardShowName(String cardShowName) {
        this.cardShowName = cardShowName;
    }

    @Override
    public String toString() {
        return cardShowName;
    }
}
