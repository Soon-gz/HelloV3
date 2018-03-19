package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/9.
 */

public class ExchangeModel {

    /**
     * drawId : 69
     * name : 超市充值卡
     * orderNum : bb20170504153951727018
     * points : 1000
     */

    private int drawId;
    private String name;
    private String orderNum;
    private int points;

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
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

    @Override
    public String toString() {
        return "ExchangeModel{" +
                "drawId=" + drawId +
                ", name='" + name + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", points=" + points +
                '}';
    }
}
