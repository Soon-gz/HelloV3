package com.hellobaby.library.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hellobaby.library.Const;

/**
 * Created by zwj on 2016/12/28.
 * description :
 */

public class BabyModel implements Parcelable {

    private Integer babyId;//宝宝id
    private Integer classId;//班级id
    private String className;//班级名称
    private Integer schoolId;//校园id
    private String schoolName;//校园名字
    private Integer masterId;//宝宝创建者id
    private String babyName;//宝宝姓名
    private String babyGender;//宝宝性别
    private String birthDate;//2.1 出身日期
    private String birthday;//5.2 出身日期
    private String address;//出生地
    private String headImageurl;//头像Url
    private String kindergarten;
    private String relation; //跟登录用户的关系
    private String isCreator;//0 登录用户创建
    private String gradeId;// 年级id
    private String gradeName;//年级名称

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

    public String getBirthDate() {
        return birthDate;
    }

    public String getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(String kindergarten) {
        this.kindergarten = kindergarten;
    }

    public void setBabyId(Integer babyId) {
        this.babyId = babyId;
    }

    public Integer getBabyId() {
        return this.babyId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        if (classId == null) {
            return 0;
        }
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
        return "1".equals(this.babyGender) ? "男孩" : "女孩";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImageurl = headImgUrl;
    }

    public String getHeadImgUrl() {
        return this.headImageurl;
    }

    /**
     * @return 头像是否为空
     */
    public boolean isEmptyHeadImgUrl() {
        return (headImageurl == null || headImageurl.isEmpty());
    }

    public String getHeadImgUrlAbs() {
        return Const.URL_BabyHead + headImageurl;
    }

    public String getIsCreator() {
        return isCreator;
    }

    /**
     * @return 是否为master
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public String toString() {
        return "BabyModel{" +
                "babyId=" + babyId +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", masterId=" + masterId +
                ", babyName='" + babyName + '\'' +
                ", babyGender='" + babyGender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", kindergarten='" + kindergarten + '\'' +
                ", relation='" + relation + '\'' +
                ", isCreator='" + isCreator + '\'' +
                ", gradeId='" + gradeId + '\'' +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.babyId);
        dest.writeValue(this.classId);
        dest.writeString(this.className);
        dest.writeValue(this.schoolId);
        dest.writeString(this.schoolName);
        dest.writeValue(this.masterId);
        dest.writeString(this.babyName);
        dest.writeString(this.babyGender);
        dest.writeString(this.birthDate);
        dest.writeString(this.birthday);
        dest.writeString(this.address);
        dest.writeString(this.headImageurl);
        dest.writeString(this.kindergarten);
        dest.writeString(this.relation);
        dest.writeString(this.isCreator);
        dest.writeString(this.gradeId);
        dest.writeString(this.gradeName);
    }

    public BabyModel() {
    }

    protected BabyModel(Parcel in) {
        this.babyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.classId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.className = in.readString();
        this.schoolId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.schoolName = in.readString();
        this.masterId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.babyName = in.readString();
        this.babyGender = in.readString();
        this.birthDate = in.readString();
        this.birthday = in.readString();
        this.address = in.readString();
        this.headImageurl = in.readString();
        this.kindergarten = in.readString();
        this.relation = in.readString();
        this.isCreator = in.readString();
        this.gradeId = in.readString();
        this.gradeName = in.readString();
    }

    public static final Creator<BabyModel> CREATOR = new Creator<BabyModel>() {
        @Override
        public BabyModel createFromParcel(Parcel source) {
            return new BabyModel(source);
        }

        @Override
        public BabyModel[] newArray(int size) {
            return new BabyModel[size];
        }
    };
}
