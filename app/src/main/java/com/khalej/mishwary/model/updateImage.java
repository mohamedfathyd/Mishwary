package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

public class updateImage {
    @SerializedName("national_image")
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
