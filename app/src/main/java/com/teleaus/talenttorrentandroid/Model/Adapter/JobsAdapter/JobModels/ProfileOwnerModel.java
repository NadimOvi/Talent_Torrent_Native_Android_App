package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProfileOwnerModel implements Parcelable {
    private Integer id;
    private Integer is_public;
    private String avatar_url;
    private String cover_photo_url;
    private String date_of_birth;
    private String phone;
    private String qualification;
    private String experience;
    private List<JobModelSkills> skills;
    private String business_number;
    private String cover_letter;
    private String job_title;
    private String company_name;
    private String company_size;
    private String business_type;
    private String company_registration_number;
    private String location;
    private String country;
    private String created_at;
    private String updated_at;

    public ProfileOwnerModel() {
    }

    public ProfileOwnerModel(Integer id, Integer is_public, String avatar_url, String cover_photo_url, String date_of_birth, String phone, String qualification, String experience, List<JobModelSkills> skills, String business_number, String cover_letter, String job_title, String company_name, String company_size, String business_type, String company_registration_number, String location, String country, String created_at, String updated_at) {
        this.id = id;
        this.is_public = is_public;
        this.avatar_url = avatar_url;
        this.cover_photo_url = cover_photo_url;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.qualification = qualification;
        this.experience = experience;
        this.skills = skills;
        this.business_number = business_number;
        this.cover_letter = cover_letter;
        this.job_title = job_title;
        this.company_name = company_name;
        this.company_size = company_size;
        this.business_type = business_type;
        this.company_registration_number = company_registration_number;
        this.location = location;
        this.country = country;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected ProfileOwnerModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            is_public = null;
        } else {
            is_public = in.readInt();
        }
        avatar_url = in.readString();
        cover_photo_url = in.readString();
        date_of_birth = in.readString();
        phone = in.readString();
        qualification = in.readString();
        experience = in.readString();
        business_number = in.readString();
        cover_letter = in.readString();
        job_title = in.readString();
        company_name = in.readString();
        company_size = in.readString();
        business_type = in.readString();
        company_registration_number = in.readString();
        location = in.readString();
        country = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<ProfileOwnerModel> CREATOR = new Creator<ProfileOwnerModel>() {
        @Override
        public ProfileOwnerModel createFromParcel(Parcel in) {
            return new ProfileOwnerModel(in);
        }

        @Override
        public ProfileOwnerModel[] newArray(int size) {
            return new ProfileOwnerModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIs_public() {
        return is_public;
    }

    public void setIs_public(Integer is_public) {
        this.is_public = is_public;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getCover_photo_url() {
        return cover_photo_url;
    }

    public void setCover_photo_url(String cover_photo_url) {
        this.cover_photo_url = cover_photo_url;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<JobModelSkills> getSkills() {
        return skills;
    }

    public void setSkills(List<JobModelSkills> skills) {
        this.skills = skills;
    }

    public String getBusiness_number() {
        return business_number;
    }

    public void setBusiness_number(String business_number) {
        this.business_number = business_number;
    }

    public String getCover_letter() {
        return cover_letter;
    }

    public void setCover_letter(String cover_letter) {
        this.cover_letter = cover_letter;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_size() {
        return company_size;
    }

    public void setCompany_size(String company_size) {
        this.company_size = company_size;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getCompany_registration_number() {
        return company_registration_number;
    }

    public void setCompany_registration_number(String company_registration_number) {
        this.company_registration_number = company_registration_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (is_public == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(is_public);
        }
        parcel.writeString(avatar_url);
        parcel.writeString(cover_photo_url);
        parcel.writeString(date_of_birth);
        parcel.writeString(phone);
        parcel.writeString(qualification);
        parcel.writeString(experience);
        parcel.writeString(business_number);
        parcel.writeString(cover_letter);
        parcel.writeString(job_title);
        parcel.writeString(company_name);
        parcel.writeString(company_size);
        parcel.writeString(business_type);
        parcel.writeString(company_registration_number);
        parcel.writeString(location);
        parcel.writeString(country);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
    }
}
