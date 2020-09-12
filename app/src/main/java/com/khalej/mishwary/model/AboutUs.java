package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

public class AboutUs {
    @SerializedName("id")
    int id;
    @SerializedName("content")
    String content;

    @SerializedName("link")
    String link;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
