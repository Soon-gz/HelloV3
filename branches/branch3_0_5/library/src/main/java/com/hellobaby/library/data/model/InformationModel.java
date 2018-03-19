package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/10.
 */

public class InformationModel implements Serializable{

    /**
     * newsinfoId : 3
     * creatorId : 2
     * classId : 2
     * title : 美女3号
     * schoolId : 1
     * createTime : 1483943531000
     * detailsUrl : www.baidu.com
     * type : 2
     * newInfoImageurls : https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=011fe800b08f8c54fcd3c22f0a282dee/c2cec3fdfc03924578c6cfe18394a4c27c1e25e8.jpg
     * content : null
     * teacherName : 老师2
     * headImageurl : b1d237c64cad491b941aba60996a17c0.bmp
     * phoneNum : 13088637695
     */

    private int newsinfoId;
    private int creatorId;
    private int classId;
    private String title;
    private int schoolId;
    private long createTime;
    private String detailsUrl;
    private int type;
    private String newInfoImageurls;
    private String content;
    private String teacherName;
    private String headImageurl;
    private String phoneNum;
    private String favoriteId;

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getNewsinfoId() {
        return newsinfoId;
    }

    public void setNewsinfoId(int newsinfoId) {
        this.newsinfoId = newsinfoId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNewInfoImageurls() {
        return newInfoImageurls;
    }

    public void setNewInfoImageurls(String newInfoImageurls) {
        this.newInfoImageurls = newInfoImageurls;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
