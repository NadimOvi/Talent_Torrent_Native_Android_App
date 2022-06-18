package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobMainModel implements Parcelable {
    private String success;
    private ArrayList<JobData>data;
    private JobMeta meta;
    private ArrayList<JobCatagories> catagories;

    public JobMainModel(String success, ArrayList<JobData> data, JobMeta meta, ArrayList<JobCatagories> catagories) {
        this.success = success;
        this.data = data;
        this.meta = meta;
        this.catagories = catagories;
    }

    public JobMainModel() {
    }

    protected JobMainModel(Parcel in) {
        success = in.readString();
        data = in.createTypedArrayList(JobData.CREATOR);
        meta = in.readParcelable(JobMeta.class.getClassLoader());
        catagories = in.createTypedArrayList(JobCatagories.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(success);
        dest.writeTypedList(data);
        dest.writeParcelable(meta, flags);
        dest.writeTypedList(catagories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JobMainModel> CREATOR = new Creator<JobMainModel>() {
        @Override
        public JobMainModel createFromParcel(Parcel in) {
            return new JobMainModel(in);
        }

        @Override
        public JobMainModel[] newArray(int size) {
            return new JobMainModel[size];
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

    public JobMeta getMeta() {
        return meta;
    }

    public void setMeta(JobMeta meta) {
        this.meta = meta;
    }

    public ArrayList<JobCatagories> getCatagories() {
        return catagories;
    }

    public void setCatagories(ArrayList<JobCatagories> catagories) {
        this.catagories = catagories;
    }
}
