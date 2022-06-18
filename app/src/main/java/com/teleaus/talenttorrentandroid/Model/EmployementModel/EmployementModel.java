package com.teleaus.talenttorrentandroid.Model.EmployementModel;

import com.teleaus.talenttorrentandroid.Model.EducationModel.EducationData;

import java.io.Serializable;
import java.util.List;

public class EmployementModel implements Serializable {
    private boolean success;
    private List<EmployementData> data;

    public EmployementModel(boolean success, List<EmployementData> data) {
        this.success = success;
        this.data = data;
    }

    public EmployementModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<EmployementData> getData() {
        return data;
    }

    public void setData(List<EmployementData> data) {
        this.data = data;
    }
}
