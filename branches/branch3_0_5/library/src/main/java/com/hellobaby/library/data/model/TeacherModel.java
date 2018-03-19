package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

/**
 * Created by zwj on 2016/12/30.
 */

public class TeacherModel {
    /**
     * teacherId : 1
     * schoolId : 1
     * teacherName : 老师1
     * teacherPassword : 123
     * phoneNum : 18989619202
     * headImageurl : /asd
     * createTime : 1483077866000
     * smsCode : sf8qw
     * token : toke
     * tokenExpiredate : 1483077865000
     * position : 班主任
     * teacherEmail : 1216@qq
     * isPublic : 0
     * lastLoginTime : 1481189912000
     * status : 1
     */

    private String teacherId;
    private String schoolId;
    private String teacherName;
    private String teacherPassword;
    private String phoneNum;
    private String headImageurl;
    private String createTime;
    private String smsCode;
    private String token;
    private String tokenExpiredate;
    private String position;
    private String teacherEmail;
    private String isPublic;
    private String lastLoginTime;
    private String status;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    /**
     * 头像全路径
     *
     * @return
     */
    public String getHeadImageurlAbs() {
        return Const.URL_TeacherHead + headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpiredate() {
        return tokenExpiredate;
    }

    public void setTokenExpiredate(String tokenExpiredate) {
        this.tokenExpiredate = tokenExpiredate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }


    public boolean isPublic() {
        return "1".equals(isPublic);
    }

    /**
     * 传入一个是否公开 ，设置isPublic
     * @param isP true:1;false：0
     */
    public void setIsPublic(boolean isP) {
        this.isPublic = isP ? "1" : "0";
    }
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TeacherModel{" +
                "teacherId='" + teacherId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherPassword='" + teacherPassword + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", token='" + token + '\'' +
                ", tokenExpiredate='" + tokenExpiredate + '\'' +
                ", position='" + position + '\'' +
                ", teacherEmail='" + teacherEmail + '\'' +
                ", isPublic='" + isPublic + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
