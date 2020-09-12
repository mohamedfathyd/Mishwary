package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class contact_sub_category {
    @SerializedName("id")
    int id;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("en_title")
    String en_title;
    @SerializedName("price")
    double price;
    @SerializedName("des")
    String details;
    @SerializedName("ar_des")
    String ar_des;
    @SerializedName("en_des")
    String en_des;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("longitude")
   double longitude;
    @SerializedName("banner")
    String banner;
    @SerializedName("logo")
    String logo;
    @SerializedName("category_id")
    int category_id;
    @SerializedName("address")
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAr_des() {
        return ar_des;
    }

    public void setAr_des(String ar_des) {
        this.ar_des = ar_des;
    }

    public String getEn_des() {
        return en_des;
    }

    public void setEn_des(String en_des) {
        this.en_des = en_des;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
