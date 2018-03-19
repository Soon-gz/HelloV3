package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/4.
 */

public class ReviewModel implements Serializable{
//    0 吃饭
//    1 喝水
//    2 午休
//    3上厕所
//    4 活动
    /**
     * babyId : 1
     * babyName : 宝贝1
     * headImageurl : /static/images/babyHead/baby001.jpg
     * eating : 2
     * drinking : 2
     * noonbreak : 2
     * toilet : 2
     * activity : 2
     * remark : 宝宝评语
     */
    public static final int TYPE_EAT = 0;
    public static final int TYPE_DRINK = 1;
    public static final int TYPE_NOONBREAK = 2;
    public static final int TYPE_TOILET = 3;
    public static final int TYPE_ACTIVITY = 4;
    private int babyId;
    private String babyName;
    private String headImageurl;
    private Integer eating;
    private Integer drinking;
    private Integer noonbreak;
    private Integer toilet;
    private Integer activity;
    private String remark;
    private int createId;

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    /**
     * score : 123
     */
    private Integer score;
    private String teacherName;//发送的老师
    private String inputDate;//输入的时间

    public int getBabyId() {
        return babyId;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }
    public String getHeadImageurlAbs() {
        return Const.URL_BabyHead+headImageurl;
    }


    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public Integer getEating() {
        return eating!=null?eating:0;
    }

    public void setEating(Integer eating) {
        this.eating = eating;
    }

    public Integer getDrinking() {
        return drinking!=null?drinking:0;
    }

    public void setDrinking(Integer drinking) {
        this.drinking = drinking;
    }

    public Integer getNoonbreak() {
        return noonbreak!=null?noonbreak:0;
    }

    public void setNoonbreak(Integer noonbreak) {
        this.noonbreak = noonbreak;
    }

    public Integer getToilet() {
        return toilet!=null?toilet:0;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public int getActivity() {
        return activity!=null?activity:0;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getScore() {
        return score!=null?score:0;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 以上的总分
     * @return
     */
    public String getScoreTotal() {
        return String.valueOf(getEating() + getDrinking() + getNoonbreak() + getToilet() + getActivity());
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "babyId=" + babyId +
                ", babyName='" + babyName + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", eating=" + eating +
                ", drinking=" + drinking +
                ", noonbreak=" + noonbreak +
                ", toilet=" + toilet +
                ", activity=" + activity +
                ", remark='" + remark + '\'' +
                ", score=" + score +
                '}';
    }
}
