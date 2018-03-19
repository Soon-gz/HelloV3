package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/2/23.
 */

public class EventJoinModel {
    /**
     * id : 14
     * babyId : 2
     * eventId : 1
     * babyNumber : 2
     * parentsNumber : 2
     * name : 小宝贝
     */

    private int id;
    private int babyId;
    private int eventId;
    private int babyNumber;
    private int parentsNumber;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getBabyNumber() {
        return babyNumber;
    }

    public void setBabyNumber(int babyNumber) {
        this.babyNumber = babyNumber;
    }

    public int getParentsNumber() {
        return parentsNumber;
    }

    public void setParentsNumber(int parentsNumber) {
        this.parentsNumber = parentsNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
