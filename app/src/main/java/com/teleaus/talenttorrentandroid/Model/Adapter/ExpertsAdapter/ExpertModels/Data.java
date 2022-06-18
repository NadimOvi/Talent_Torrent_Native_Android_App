package com.teleaus.talenttorrentandroid.Model.Adapter.ExpertsAdapter.ExpertModels;

import android.os.Parcelable;

import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class Data implements Parcelable {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String username;
    private String type;
    private Profile profile;
    private String verified_at;
    private String created_at;
    private String updated_at;

    public Data() {
    }

    public Data(Integer id, String first_name, String last_name, String email, String username, String type, Profile profile, String verified_at, String created_at, String updated_at) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.type = type;
        this.profile = profile;
        this.verified_at = verified_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected Data(android.os.Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        username = in.readString();
        type = in.readString();
        verified_at = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(String verified_at) {
        this.verified_at = verified_at;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(email);
        parcel.writeString(username);
        parcel.writeString(type);
        parcel.writeString(verified_at);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}
