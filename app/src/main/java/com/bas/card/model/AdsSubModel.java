package com.bas.card.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsSubModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_id")
    @Expose
    private Integer appId;
    @SerializedName("ads_name")
    @Expose
    private String adsName;
    @SerializedName("ads_status")
    @Expose
    private Integer adsStatus;
    @SerializedName("ads_link")
    @Expose
    private String adsLink;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    public Integer getAdsStatus() {
        return adsStatus;
    }

    public void setAdsStatus(Integer adsStatus) {
        this.adsStatus = adsStatus;
    }

    public String getAdsLink() {
        return adsLink;
    }

    public void setAdsLink(String adsLink) {
        this.adsLink = adsLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
