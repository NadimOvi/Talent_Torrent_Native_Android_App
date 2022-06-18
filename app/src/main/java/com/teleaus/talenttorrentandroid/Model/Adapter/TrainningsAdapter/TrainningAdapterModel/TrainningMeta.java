package com.teleaus.talenttorrentandroid.Model.Adapter.TrainningsAdapter.TrainningAdapterModel;

public class TrainningMeta {
    private Integer last_page;
    private Integer next_page_url;
    private Integer prev_page_url;
    private Integer total;

    public TrainningMeta() {
    }

    public TrainningMeta(Integer last_page, Integer next_page_url, Integer prev_page_url, Integer total) {
        this.last_page = last_page;
        this.next_page_url = next_page_url;
        this.prev_page_url = prev_page_url;
        this.total = total;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public Integer getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(Integer next_page_url) {
        this.next_page_url = next_page_url;
    }

    public Integer getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(Integer prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
