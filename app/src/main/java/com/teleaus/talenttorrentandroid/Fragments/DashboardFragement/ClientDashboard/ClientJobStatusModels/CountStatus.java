package com.teleaus.talenttorrentandroid.Fragments.DashboardFragement.ClientDashboard.ClientJobStatusModels;

public class CountStatus {
    private int total;
    private int active;
    private int drafted;
    private int ongoing;
    private int completed;
    private int submitted;
    private int inactive;
    private int awarded;
    private String spent;

    public CountStatus() {
    }

    public CountStatus(int total, int active, int drafted, int ongoing, int completed, int submitted, int inactive, int awarded, String spent) {
        this.total = total;
        this.active = active;
        this.drafted = drafted;
        this.ongoing = ongoing;
        this.completed = completed;
        this.submitted = submitted;
        this.inactive = inactive;
        this.awarded = awarded;
        this.spent = spent;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getDrafted() {
        return drafted;
    }

    public void setDrafted(int drafted) {
        this.drafted = drafted;
    }

    public int getOngoing() {
        return ongoing;
    }

    public void setOngoing(int ongoing) {
        this.ongoing = ongoing;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getSubmitted() {
        return submitted;
    }

    public void setSubmitted(int submitted) {
        this.submitted = submitted;
    }

    public int getInactive() {
        return inactive;
    }

    public void setInactive(int inactive) {
        this.inactive = inactive;
    }

    public int getAwarded() {
        return awarded;
    }

    public void setAwarded(int awarded) {
        this.awarded = awarded;
    }

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }
}
