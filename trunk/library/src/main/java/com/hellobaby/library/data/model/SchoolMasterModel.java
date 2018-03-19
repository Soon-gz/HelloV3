package com.hellobaby.library.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShuWen on 2017/10/30.
 */

public class SchoolMasterModel {

    /**
     * leaveId : 15
     * teacherId : 1
     * createTime : 1511366400000
     * startTime : 1511485200000
     * endTime : 1511514000000
     * type : 2
     * leaveReason : 明天再玩吧
     * state : 0
     * leaveTime : null
     * teacherName : 老师14
     */

    private List<ApprovalListBean> ApprovalList;
    /**
     * teacherattendanceId : 1071
     * teacherId : 4
     * amstate : 1
     * createTime : 1511316264000
     * timeCardImgUrl : a505bf99636344b9a3cdc2f9c02013f8.jpg
     * qrcode : {"teacherId":"4","schoolId":"1","teacherState":"1"}
     * createDate : 1511280000000
     * minuteTime : 7464000
     * teacherName : 朱3
     */

    private List<AttendanceListBean> attendanceList;

    public List<ApprovalListBean> getApprovalList() {
        return ApprovalList;
    }

    public void setApprovalList(List<ApprovalListBean> ApprovalList) {
        this.ApprovalList = ApprovalList;
    }

    public List<AttendanceListBean> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceListBean> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public static class ApprovalListBean implements Serializable {
        private int leaveId;
        private int teacherId;
        private long createTime;
        private long startTime;
        private long endTime;
        private int type;
        private String leaveReason;
        private int state;
        private long leaveTime;
        private String teacherName;

        public int getLeaveId() {
            return leaveId;
        }

        public void setLeaveId(int leaveId) {
            this.leaveId = leaveId;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLeaveReason() {
            return leaveReason;
        }

        public void setLeaveReason(String leaveReason) {
            this.leaveReason = leaveReason;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(long leaveTime) {
            this.leaveTime = leaveTime;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }
    }

    public static class AttendanceListBean implements Serializable{
        private int teacherattendanceId;
        private int teacherId;
        private int amstate;
        private long createTime;
        private String timeCardImgUrl;
        private String qrcode;
        private long createDate;
        private int minuteTime;
        private String teacherName;

        public int getTeacherattendanceId() {
            return teacherattendanceId;
        }

        public void setTeacherattendanceId(int teacherattendanceId) {
            this.teacherattendanceId = teacherattendanceId;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public int getAmstate() {
            return amstate;
        }

        public void setAmstate(int amstate) {
            this.amstate = amstate;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getTimeCardImgUrl() {
            return timeCardImgUrl;
        }

        public void setTimeCardImgUrl(String timeCardImgUrl) {
            this.timeCardImgUrl = timeCardImgUrl;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getMinuteTime() {
            return minuteTime;
        }

        public void setMinuteTime(int minuteTime) {
            this.minuteTime = minuteTime;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }
    }
}
