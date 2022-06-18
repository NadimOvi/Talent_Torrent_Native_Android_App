package com.teleaus.talenttorrentandroid.Model.EducationModel;

import java.io.Serializable;
import java.util.List;

public class EducationsModel implements Serializable {
    private boolean success;
    private List<EducationData> data;

    public EducationsModel(boolean success, List<EducationData> data) {
        this.success = success;
        this.data = data;
    }

    public EducationsModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<EducationData> getData() {
        return data;
    }

    public void setData(List<EducationData> data) {
        this.data = data;
    }
}
