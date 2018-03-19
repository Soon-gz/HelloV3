package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

import java.io.Serializable;

public class UserModel implements Serializable {


    /**
     * userId : 1
     * userName : 爸爸1
     * userPassword : 123
     * userEmail : 121607691@qq.com
     * phoneNum : 18989619202
     * headImageurl : null
     * nickname : 老大
     * createTime : 1483104975000
     * smsCode : null
     * token : null
     * tokenExpiredate : 1481549765000
     * isPublic : 1
     * lastLoginTime : 1483104973000
     * status : 1
     * relation : 爸爸
     */

    private int userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String phoneNum;
    private String headImageurl;
    private String nickname;
    private String createTime;
    private String smsCode;
    private String token;
    private String tokenExpiredate;
    private String isPublic;
    private String lastLoginTime;
    private int status;
    private String relation = "";
    private boolean isSelected = false;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getHeadImageurlAbs() {
        return Const.URL_userHead + headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Object getToken() {
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

    public String getIsPublic() {
        return isPublic;
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

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", headImageurl='" + headImageurl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime='" + createTime + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", token='" + token + '\'' +
                ", tokenExpiredate='" + tokenExpiredate + '\'' +
                ", isPublic='" + isPublic + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", status=" + status +
                ", relation='" + relation + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
