package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/2/23.
 */

public class ServerCarebabyCache {

    /**
     * careUserId : 1
     * alert : chenping想要关注你的宝宝GG孤狐
     * babyId : 24
     */

    private int careUserId;
    private String alert;
    private int babyId;

    public int getCareUserId() {
        return careUserId;
    }

    public void setCareUserId(int careUserId) {
        this.careUserId = careUserId;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    @Override
    public String toString() {
        return "ServerCarebabyCache{" +
                "careUserId=" + careUserId +
                ", alert='" + alert + '\'' +
                ", babyId=" + babyId +
                '}';
    }
}
