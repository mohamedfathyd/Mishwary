package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_category {
    @SerializedName("id")
    int id;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("en_title")
    String en_title;
    @SerializedName("image")
    String image;
    @SerializedName("price")
    double price;
    @SerializedName("ar_desc")
    String details;
    @SerializedName("country_price")
    String country_price;
    @SerializedName("country_ar_current")
    String country_ar_current;
    @SerializedName("country_en_current")
    String country_en_current;
    @SerializedName("sub_categories")
    List<contact_category> sub_categories;

    public String getCountry_price() {
        return country_price;
    }

    public void setCountry_price(String country_price) {
        this.country_price = country_price;
    }

    public String getCountry_ar_current() {
        return country_ar_current;
    }

    public void setCountry_ar_current(String country_ar_current) {
        this.country_ar_current = country_ar_current;
    }

    public String getCountry_en_current() {
        return country_en_current;
    }

    public void setCountry_en_current(String country_en_current) {
        this.country_en_current = country_en_current;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<contact_category> getSub_categories() {
        return sub_categories;
    }

    public void setSub_categories(List<contact_category> sub_categories) {
        this.sub_categories = sub_categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
