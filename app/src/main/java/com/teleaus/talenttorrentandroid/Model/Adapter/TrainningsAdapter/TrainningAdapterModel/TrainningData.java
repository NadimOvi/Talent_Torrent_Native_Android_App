package com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainningData implements Parcelable {
    private Integer id;
    private String title;
    private String details;
    private String location;
    private Integer fee;
    private String duration;
    private String start_date;
    private Integer lunch;
    private Integer snacks;
    private Integer assessment;
    private String certificate;
    private String created_at;
    private String updated_at;

    public TrainningData() {
    }

    public TrainningData(Integer id, String title, String details, String location, Integer fee, String duration, String start_date, Integer lunch, Integer snacks, Integer assessment, String certificate, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.location = location;
        this.fee = fee;
        this.duration = duration;
        this.start_date = start_date;
        this.lunch = lunch;
        this.snacks = snacks;
        this.assessment = assessment;
        this.certificate = certificate;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected TrainningData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        details = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            fee = null;
        } else {
            fee = in.readInt();
        }
        duration = in.readString();
        start_date = in.readString();
        if (in.readByte() == 0) {
            lunch = null;
        } else {
            lunch = in.readInt();
        }
        if (in.readByte() == 0) {
            snacks = null;
        } else {
            snacks = in.readInt();
        }
        if (in.readByte() == 0) {
            assessment = null;
        } else {
            assessment = in.readInt();
        }
        certificate = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(location);
        if (fee == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fee);
        }
        dest.writeString(duration);
        dest.writeString(start_date);
        if (lunch == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lunch);
        }
        if (snacks == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(snacks);
        }
        if (assessment == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(assessment);
        }
        dest.writeString(certificate);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrainningData> CREATOR = new Creator<TrainningData>() {
        @Override
        public TrainningData createFromParcel(Parcel in) {
            return new TrainningData(in);
        }

        @Override
        public TrainningData[] newArray(int size) {
            return new TrainningData[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    public Integer getSnacks() {
        return snacks;
    }

    public void setSnacks(Integer snacks) {
        this.snacks = snacks;
    }

    public Integer getAssessment() {
        return assessment;
    }

    public void setAssessment(Integer assessment) {
        this.assessment = assessment;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
