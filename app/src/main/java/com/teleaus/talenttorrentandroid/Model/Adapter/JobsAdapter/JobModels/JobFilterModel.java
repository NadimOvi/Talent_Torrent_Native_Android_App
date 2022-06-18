package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class JobFilterModel implements Parcelable {
    private String success;
    private ArrayList<JobData> data;

    public JobFilterModel(String success, ArrayList<JobData> data) {
        this.success = success;
        this.data = data;
    }

    public JobFilterModel() {
    }

    protected JobFilterModel(Parcel in) {
        success = in.readString();
        data = in.createTypedArrayList(JobData.CREATOR);
    }

    public static final Creator<JobFilterModel> CREATOR = new Creator<JobFilterModel>() {
        @Override
        public JobFilterModel createFromParcel(Parcel in) {
            return new JobFilterModel(in);
        }

        @Override
        public JobFilterModel[] newArray(int size) {
            return new JobFilterModel[size];
        }
    };

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<JobData> getData() {
        return data;
    }

    public void setData(ArrayList<JobData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(success);
        parcel.writeTypedList(data);
    }
}
