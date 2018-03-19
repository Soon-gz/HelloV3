package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/1.
 */

public class TeachingPlanModel implements Serializable{

    /**
     * teachingplanId : 12
     * creatorId : 1
     * classId : 4
     * imageurl : b5e3489d62174b6d812c10f207b6a628.jpg
     * createTime : 2016-12-31 15-35-42
     * planningTime : 2016-12-03
     */

    private int teachingplanId;
    private int creatorId;
    private int classId;
    private String imageurl;
    private String createTime;
    private String planningTime;
    /**
     * className : 大班1
     */

    private String className;
    /**
     * teacherName : 小郑老师
     */

    private String teacherName;

    public int getTeachingplanId() {
        return teachingplanId;
    }

    public void setTeachingplanId(int teachingplanId) {
        this.teachingplanId = teachingplanId;
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

    public String getImageurl() {
        return imageurl;
    }
    public String getImageurlAbs() {
        return Const.URL_teachingPlan+imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlanningTime() {
        return planningTime;
    }

    public void setPlanningTime(String planningTime) {
        this.planningTime = planningTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
