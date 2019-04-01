package com.laxman.newsheadline.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopHeadlineResponse {
    @SerializedName("articles")
    @Expose
    private List<TopHeadlineModelClass> topHeadlineModelClasses = null;

    public List<TopHeadlineModelClass> getTopHeadlineModelClasses() {
        return topHeadlineModelClasses;
    }

    public void setTopHeadlineModelClasses(List<TopHeadlineModelClass> topHeadlineModelClasses) {
        this.topHeadlineModelClasses = topHeadlineModelClasses;
    }
}
