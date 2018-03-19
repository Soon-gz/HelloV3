package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class PrizeLuckyModel {

    /**
     * drawId : 56
     * id : 7
     * entity : 50
     * name : 积分（50分）
     * orderNum : aa20170504110124854005
     * points : 100
     * imgurl : 1
     */

    private int drawId;
    private int id;
    private int entity;
    private String name;
    private String orderNum;
    private int points;
    private String imgurl;

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
