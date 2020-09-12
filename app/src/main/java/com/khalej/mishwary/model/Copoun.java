package com.khalej.mishwary.model;

import com.google.gson.annotations.SerializedName;

public class Copoun {
    @SerializedName("id")
    int id;
    @SerializedName("en_title")
    String en_title;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("coupon_serial")
    String coupon_serial;
    @SerializedName("coupon_value")
    String coupon_value;
    @SerializedName("is_active")
    int is_active;
    @SerializedName("status")
    int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getCoupon_serial() {
        return coupon_serial;
    }

    public void setCoupon_serial(String coupon_serial) {
        this.coupon_serial = coupon_serial;
    }

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
