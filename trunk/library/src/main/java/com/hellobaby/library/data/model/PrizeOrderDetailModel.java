package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by ShuWen on 2017/5/17.
 */

public class PrizeOrderDetailModel implements Serializable {

    /**
     * createTime : 1494466288000
     * courierNumber :
     * fullAddress :
     * state : 0
     * orderNum : D1-20170511093128329000
     * locationArea :
     * type : 1
     * id : 2
     * phoneNum :
     * name :
     * points : 100
     * prizeName : 话费充值卡（10元）
     * typeId : 5
     * createId : 4
     */

    private long createTime;
    private String courierNumber;
    private String fullAddress;
    private int state;
    private String orderNum;
    private String locationArea;
    private int type;
    private int id;
    private String phoneNum;
    private String name;
    private int points;
    private String prizeName;
    private int typeId;
    private int createId;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }
}
