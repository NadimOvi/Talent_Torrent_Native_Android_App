package com.teleaus.talenttorrentandroid.Model.Adapter.JobsAdapter.JobModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class JobData implements Parcelable {

    private Integer id;
    private String uuid;
    private String assigned_to;
    private String title;
    private String description;
    private String budget;
    private String confirmed_budget;
    private String type;
    private String length;
    private String status;
    private String estimated_end_date;
    private String end_date;
    private OwnerModel owner;
    private CategoryModel category;
    private ArrayList<String> tags;
    private String created_at;
    private String updated_at;

    private boolean isShrink = true;
    private boolean isExpand = true;


    public JobData() {
    }

    public JobData(Integer id, String uuid, String assigned_to, String title, String description, String budget, String confirmed_budget, String type, String length, String status, String estimated_end_date, String end_date, OwnerModel owner, CategoryModel category, ArrayList<String> tags, String created_at, String updated_at, boolean isShrink, boolean isExpand) {
        this.id = id;
        this.uuid = uuid;
        this.assigned_to = assigned_to;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.confirmed_budget = confirmed_budget;
        this.type = type;
        this.length = length;
        this.status = status;
        this.estimated_end_date = estimated_end_date;
        this.end_date = end_date;
        this.owner = owner;
        this.category = category;
        this.tags = tags;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isShrink = isShrink;
        this.isExpand = isExpand;
    }

    protected JobData(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        uuid = in.readString();
        assigned_to = in.readString();
        title = in.readString();
        description = in.readString();
        budget = in.readString();
        confirmed_budget = in.readString();
        type = in.readString();
        length = in.readString();
        status = in.readString();
        estimated_end_date = in.readString();
        end_date = in.readString();
        owner = in.readParcelable(OwnerModel.class.getClassLoader());
        category = in.readParcelable(CategoryModel.class.getClassLoader());
        tags = in.createStringArrayList();
        created_at = in.readString();
        updated_at = in.readString();
        isShrink = in.readByte() != 0;
        isExpand = in.readByte() != 0;
    }

    public static final Creator<JobData> CREATOR = new Creator<JobData>() {
        @Override
        public JobData createFromParcel(Parcel in) {
            return new JobData(in);
        }

        @Override
        public JobData[] newArray(int size) {
            return new JobData[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getConfirmed_budget() {
        return confirmed_budget;
    }

    public void setConfirmed_budget(String confirmed_budget) {
        this.confirmed_budget = confirmed_budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimated_end_date() {
        return estimated_end_date;
    }

    public void setEstimated_end_date(String estimated_end_date) {
        this.estimated_end_date = estimated_end_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
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
        parcel.writeString(uuid);
        parcel.writeString(assigned_to);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(budget);
        parcel.writeString(confirmed_budget);
        parcel.writeString(type);
        parcel.writeString(length);
        parcel.writeString(status);
        parcel.writeString(estimated_end_date);
        parcel.writeString(end_date);
        parcel.writeParcelable(owner, i);
        parcel.writeParcelable(category, i);
        parcel.writeStringList(tags);
        parcel.writeString(created_at);
        parcel.writeString(updated_at);
        parcel.writeByte((byte) (isShrink ? 1 : 0));
        parcel.writeByte((byte) (isExpand ? 1 : 0));
    }
}
