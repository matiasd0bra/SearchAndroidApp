package com.android.dobratinich.searchapp.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchItem {
    @SerializedName("site_id")
    @Expose
    private String siteId;

    @SerializedName("results")
    @Expose
    private List<Item> results;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<Item> getResults() {
        return results;
    }

    public void setResults(List<Item> results) {
        this.results = results;
    }
}
