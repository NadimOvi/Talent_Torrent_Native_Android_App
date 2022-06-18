package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

public class JobMeta implements Parcelable {
    private Integer last_page;
    private Integer total;

    public JobMeta() {
    }

    public JobMeta(Integer last_page, Integer total) {
        this.last_page = last_page;
        this.total = total;
    }

    protected JobMeta(Parcel in) {
        if (in.readByte() == 0) {
            last_page = null;
        } else {
            last_page = in.readInt();
        }
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
    }

    public static final Creator<JobMeta> CREATOR = new Creator<JobMeta>() {
        @Override
        public JobMeta createFromParcel(Parcel in) {
            return new JobMeta(in);
        }

        @Override
        public JobMeta[] newArray(int size) {
            return new JobMeta[size];
        }
    };

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (last_page == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(last_page);
        }
        if (total == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(total);
        }
    }
}
