package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

/**
 * Created by zwj on 2016/12/28.
 * description :
 */

public class BabyModel {

    private Integer babyId;//宝宝id
    private Integer classId;//班级id
    private Integer schoolId;//校园id
    private String schoolName;//校园名字
    private Integer masterId;//宝宝创建者id
    private String babyName;//宝宝姓名
    private String babyGender;//宝宝性别
    private String birthDate;//2.1 出身日期
    private String birthday;//5.2 出身日期
    private String address;//出生地
    private String headImageurl;//头像Url
    private String headImgUrlAbs;//头像绝对的Url
    private String kindergarten;
    private String relation; //跟登录用户的关系
    private String isCreator;//0 登录用户创建

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getBirthday() {
        return birthday == null ? birthDate : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthDate = birthday;
        this.birthday = birthday;
    }


    public String getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(String kindergarten) {
        this.kindergarten = kindergarten;
    }

    public void setBabyId(int babyId) {
        this.babyId = babyId;
    }

    public int getBabyId() {
        return this.babyId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        return this.classId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
    public String getSchoolName() {
        return schoolName;
    }

    public int getSchoolId() {
        return this.schoolId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getMasterId() {
        return this.masterId;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public String getBabyName() {
        return this.babyName;
    }

    public void setBabyGender(String babyGender) {
        this.babyGender = babyGender;
    }

    public String getBabyGender() {
        return this.babyGender;
    }
    public String getBabyGenderName() {
        return "1".equals(this.babyGender)?"男孩":"女孩";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImageurl = headImgUrl;
    }

    public String getHeadImgUrl() {
        return this.headImageurl;
    }

    public String getHeadImgUrlAbs() {
        headImgUrlAbs = Const.URL_BabyHead + headImageurl;
        return headImgUrlAbs;
    }

    public String getIsCreator() {
        return isCreator;
    }

    /**
     * 是否为master
     * @return
     */
    public boolean isCreator() {
        return "0".equals(isCreator);
    }

    public void setIsCreator(String isCreator) {
        this.isCreator = isCreator;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "BabyModel{" +
                "babyId=" + babyId +
                ", classId=" + classId +
                ", schoolId=" + schoolId +
                ", masterId=" + masterId +
                ", babyName='" + babyName + '\'' +
                ", babyGender='" + babyGender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", headImgUrl='" + headImageurl + '\'' +
                '}';
    }
}
