package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

public class JobModelSkills implements Parcelable {
    protected JobModelSkills(Parcel in) {
    }

    public static final Creator<JobModelSkills> CREATOR = new Creator<JobModelSkills>() {
        @Override
        public JobModelSkills createFromParcel(Parcel in) {
            return new JobModelSkills(in);
        }

        @Override
        public JobModelSkills[] newArray(int size) {
            return new JobModelSkills[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
