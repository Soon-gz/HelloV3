package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/31.
 */

public class MeasureModel implements Serializable{
    /**
     * heightId : 10
     * weightId : 10
     * babyId : 1
     * creatorId : 1
     * height : 15
     * weight : 15
     * inputDate : 2016-12-29
     */

    private int heightId;
    private int weightId;
    private int babyId;
    private int creatorId;
    private float height;
    private float weight;
    private String inputDate;

    public int getHeightId() {
        return heightId;
    }

    public void setHeightId(int heightId) {
        this.heightId = heightId;
    }

    public int getWeightId() {
        return weightId;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
}
