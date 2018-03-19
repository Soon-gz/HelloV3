package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/5/26.
 */

public class CareOrCaredModel {

    /**
     * createTime : 1496392321000
     * toUserId : 2
     * name :
     * headImageurl : b1d237c64cad491b941aba60996a17c0.bmp
     * toType : 1
     * fromUserId : 11
     * relationId : 1
     * fromType : 1
     */

    private long createTime;
    private int toUserId;
    private String name;
    private String headImageurl;
    private int toType;
    private int fromUserId;
    private int relationId;
    private int fromType;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    @Override
    public String toString() {
        return "CareOrCaredModel{" +
                "createTime=" + createTime +
                ", toUserId=" + toUserId +
                ", name='" + name + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", toType=" + toType +
                ", fromUserId=" + fromUserId +
                ", relationId=" + relationId +
                ", fromType=" + fromType +
                ", state=" + state +
                '}';
    }
}
