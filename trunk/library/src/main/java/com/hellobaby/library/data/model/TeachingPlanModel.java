package com.hellobaby.library.data.model;

import com.hellobaby.library.Const;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/1.
 */

public class TeachingPlanModel implements Serializable {

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
    private long createTime;
    private long planningTime;
    /**
     * className : 大班1
     */

    private String className;
    /**
     * teacherName : 小郑老师
     */

    private String teacherName;
    /**
     * createTime : 1488505556000
     * planningTime : 1489939200000
     * planningEndtime : 1490457600000
     * headImageurl : b1d237c64cad491b941aba60996a17c0.bmp
     */

//    @SerializedName("createTime")
//    private long createTimeX;
//    @SerializedName("planningTime")
//    private long planningTimeX;
    private long planningEndtime;
    private String headImageurl;
    /**
     * Type  1   下周   2本周  3 上周
     */
    private int type;

    public int getType() {
        return type;
    }

    /**
     * 下周
     *
     * @return
     */
    public boolean isTypeNext() {
        return type == 1;
    }

    /**
     * 本周
     *
     * @return
     */
    public boolean isTypeThis() {
        return type == 2;
    }

    /**
     * 上周
     *
     * @return
     */
    public boolean isTypeLast() {
        return type == 3;
    }

    public void setType(int type) {
        this.type = type;
    }

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
        return Const.URL_teachingPlan + imageurl;
    }

    public String getHeadImageurlAbs() {
        return Const.URL_TeacherHead + headImageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getPlanningTime() {
        return planningTime;
    }

    public void setPlanningTime(long planningTime) {
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

//    public long getCreateTimeX() {
//        return createTimeX;
//    }
//
//    public void setCreateTimeX(long createTimeX) {
//        this.createTimeX = createTimeX;
//    }
//
//    public long getPlanningTimeX() {
//        return planningTimeX;
//    }
//
//    public void setPlanningTimeX(long planningTimeX) {
//        this.planningTimeX = planningTimeX;
//    }

    public long getPlanningEndtime() {
        return planningEndtime;
    }

    public void setPlanningEndtime(long planningEndtime) {
        this.planningEndtime = planningEndtime;
    }

    public String getHeadImageurl() {
        return headImageurl;
    }

    public void setHeadImageurl(String headImageurl) {
        this.headImageurl = headImageurl;
    }
}
