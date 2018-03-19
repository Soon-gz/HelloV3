package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class PrizeContactInfoModel {

    /**
     * id : 6
     * name : 123321
     * phoneNum : phoneNum
     * fullAddress : fullAddress
     * locationArea : locationArea
     * createId : 4
     * isDefault : 0
     */

    private int id;
    private String name;
    private String phoneNum;
    private String fullAddress;
    private String locationArea;
    private int createId;
    private int isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
