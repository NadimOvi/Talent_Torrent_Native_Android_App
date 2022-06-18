package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

public class JobCatagories implements Parcelable {
    private Integer id;
    private String name;
    private Integer is_active;

    public JobCatagories() {
    }

    public JobCatagories(Integer id, String name, Integer is_active) {
        this.id = id;
        this.name = name;
        this.is_active = is_active;
    }

    protected JobCatagories(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            is_active = null;
        } else {
            is_active = in.readInt();
        }
    }

    public static final Creator<JobCatagories> CREATOR = new Creator<JobCatagories>() {
        @Override
        public JobCatagories createFromParcel(Parcel in) {
            return new JobCatagories(in);
        }

        @Override
        public JobCatagories[] newArray(int size) {
            return new JobCatagories[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(name);
        if (is_active == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(is_active);
        }
    }
}
