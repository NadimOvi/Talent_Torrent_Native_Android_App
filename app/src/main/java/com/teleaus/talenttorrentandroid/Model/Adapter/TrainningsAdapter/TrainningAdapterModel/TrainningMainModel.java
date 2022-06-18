package com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TrainningMainModel implements Parcelable {
    private boolean success;
    private ArrayList<TrainningData> data;
    private TrainningMeta meta;
    private ArrayList<upcomingTrainings> upcomingTrainings;

    public TrainningMainModel(boolean success, ArrayList<TrainningData> data, TrainningMeta meta, ArrayList<com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.upcomingTrainings> upcomingTrainings) {
        this.success = success;
        this.data = data;
        this.meta = meta;
        this.upcomingTrainings = upcomingTrainings;
    }

    public TrainningMainModel() {
    }

    protected TrainningMainModel(Parcel in) {
        success = in.readByte() != 0;
    }

    public static final Creator<TrainningMainModel> CREATOR = new Creator<TrainningMainModel>() {
        @Override
        public TrainningMainModel createFromParcel(Parcel in) {
            return new TrainningMainModel(in);
        }

        @Override
        public TrainningMainModel[] newArray(int size) {
            return new TrainningMainModel[size];
        }
    };

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<TrainningData> getData() {
        return data;
    }

    public void setData(ArrayList<TrainningData> data) {
        this.data = data;
    }

    public TrainningMeta getMeta() {
        return meta;
    }

    public void setMeta(TrainningMeta meta) {
        this.meta = meta;
    }

    public ArrayList<com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.upcomingTrainings> getUpcomingTrainings() {
        return upcomingTrainings;
    }

    public void setUpcomingTrainings(ArrayList<com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel.upcomingTrainings> upcomingTrainings) {
        this.upcomingTrainings = upcomingTrainings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (success ? 1 : 0));
    }
}
