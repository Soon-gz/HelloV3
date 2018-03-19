package com.hellobaby.library.data.model;

/**
 * Created by zwj on 2017/1/3.
 * description : 班级信息
 */

public class ClassModel {

    /**
     * classId : 3
     * gradeId : 6
     * schoolId : 2
     * className : 中班1
     */

    private String classId;
    private String gradeId;
    private String isAll;
    /**
     * classId : 3
     * morningArrivalNum : 1
     * morningNonArrivalNum : 0
     * morningLeaveNum : 0
     * afternoonArrivalNum : 1
     * afternoonNonArrivalNum : 0
     * afternoonLeaveNum : 0
     */

    private int morningArrivalNum;
    private int morningNonArrivalNum;
    private int morningLeaveNum;
    private int afternoonArrivalNum;
    private int afternoonNonArrivalNum;
    private int afternoonLeaveNum;

    public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    private String schoolId;
    private String className;
    private boolean isSelected;
    /**
     * classId : 1
     * arrivalNum : 1
     * nonArrivalNum : 1
     * leaveNum : 1
     */

    private int arrivalNum;
    private int nonArrivalNum;
    private int leaveNum;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "classId='" + classId + '\'' +
                ", gradeId='" + gradeId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", className='" + className + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    public int getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(int arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public int getNonArrivalNum() {
        return nonArrivalNum;
    }

    public void setNonArrivalNum(int nonArrivalNum) {
        this.nonArrivalNum = nonArrivalNum;
    }

    public int getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(int leaveNum) {
        this.leaveNum = leaveNum;
    }

    public int getMorningArrivalNum() {
        return morningArrivalNum;
    }

    public void setMorningArrivalNum(int morningArrivalNum) {
        this.morningArrivalNum = morningArrivalNum;
    }

    public int getMorningNonArrivalNum() {
        return morningNonArrivalNum;
    }

    public void setMorningNonArrivalNum(int morningNonArrivalNum) {
        this.morningNonArrivalNum = morningNonArrivalNum;
    }

    public int getMorningLeaveNum() {
        return morningLeaveNum;
    }

    public void setMorningLeaveNum(int morningLeaveNum) {
        this.morningLeaveNum = morningLeaveNum;
    }

    public int getAfternoonArrivalNum() {
        return afternoonArrivalNum;
    }

    public void setAfternoonArrivalNum(int afternoonArrivalNum) {
        this.afternoonArrivalNum = afternoonArrivalNum;
    }

    public int getAfternoonNonArrivalNum() {
        return afternoonNonArrivalNum;
    }

    public void setAfternoonNonArrivalNum(int afternoonNonArrivalNum) {
        this.afternoonNonArrivalNum = afternoonNonArrivalNum;
    }

    public int getAfternoonLeaveNum() {
        return afternoonLeaveNum;
    }

    public void setAfternoonLeaveNum(int afternoonLeaveNum) {
        this.afternoonLeaveNum = afternoonLeaveNum;
    }
}
