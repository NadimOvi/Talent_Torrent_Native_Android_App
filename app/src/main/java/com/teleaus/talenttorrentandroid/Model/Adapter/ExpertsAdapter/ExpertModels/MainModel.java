package com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels;

import android.os.Parcelable;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class MainModel implements Parcelable {
    private ArrayList<Data> data;
    private Meta meta;

    public MainModel() {
    }

    public MainModel(ArrayList<Data> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    protected MainModel(android.os.Parcel in) {
        data = in.createTypedArrayList(Data.CREATOR);
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(android.os.Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
