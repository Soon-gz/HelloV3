package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

/**
 * Created by zwj on 2017/1/3.
 * description : 学校的信息
 */

public class SchoolModel {

    /**
     * schoolId : 1
     * schoolName : 椒江幼儿园
     * schoolAddress : 椒江
     * headImageurl : jiaojiangschool.jpg
     * schoolPhone : 11111111
     * "state": 0  //0   未绑定  1 已绑定
     */

    private int schoolId;
    private String schoolName;
    private String schoolAddress;
    private String headImageurl;
    private String schoolPhone;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    /**
     * @return 头像的绝对路径
     */
    public String getHeadImageurlAbs() {
        return Const.URL_schoolHead + headImageurl;
    }


    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    @Override
    public String toString() {
        return "SchoolModel{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", schoolAddress='" + schoolAddress + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", schoolPhone='" + schoolPhone + '\'' +
                '}';
    }
}
