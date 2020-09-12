package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

public class Myorder {
    @SerializedName("id")
     int id;
    @SerializedName("user_id")
    int user_id;
    @SerializedName("client_address")
    String address;
    @SerializedName("name")
    String name;
    @SerializedName("client_longitude")
    String lat;
    @SerializedName("client_latitude")
    String log;
    @SerializedName("driver_id")
    int representative_id;
     @SerializedName("user_name")
    String representative_name;
    @SerializedName("place_address")
    String representative_phone;
    @SerializedName("user_image")
    String representative_image;
    @SerializedName("des")
    String description;
    @SerializedName("total")
    String total;
    @SerializedName("user_phone")
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRepresentative_id() {
        return representative_id;
    }

    public void setRepresentative_id(int representative_id) {
        this.representative_id = representative_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public String getRepresentative_name() {
        return representative_name;
    }

    public void setRepresentative_name(String representative_name) {
        this.representative_name = representative_name;
    }

    public String getRepresentative_phone() {
        return representative_phone;
    }

    public void setRepresentative_phone(String representative_phone) {
        this.representative_phone = representative_phone;
    }

    public String getRepresentative_image() {
        return representative_image;
    }

    public void setRepresentative_image(String representative_image) {
        this.representative_image = representative_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

   }
