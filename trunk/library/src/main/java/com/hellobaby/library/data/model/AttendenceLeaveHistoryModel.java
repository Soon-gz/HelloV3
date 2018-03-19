package com.hellobaby.library.data.model;

import java.util.List;

/**
 * Created by ShuWen on 2017/11/24.
 */

public class AttendenceLeaveHistoryModel {

    /**
     * leaveId : 28
     * teacherId : 4
     * createTime : 1511366400000
     * startTime : 1511488800000
     * endTime : 1511496000000
     * type : 2
     * leaveReason : 我要请假！！
     * state : 0
     * leaveTime : null
     */

    private List<LeaveListBean> leaveList;
    /**
     * teacherattendanceId : 1080
     * teacherId : 4
     * amstate : 1
     * createTime : 1511323461000
     * timeCardImgUrl : ca4d598fd9004a2689baed144fb34c65.jpg
     * qrcode : {"teacherId":"4","schoolId":"1","teacherState":"1"}
     * createDate : 1511280000000
     * minuteTime : 14661000
     */

    private List<AttendanceListBean> attendanceList;

    public List<LeaveListBean> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<LeaveListBean> leaveList) {
        this.leaveList = leaveList;
    }

    public List<AttendanceListBean> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceListBean> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public static class LeaveListBean {
        private int leaveId;
        private int teacherId;
        private long createTime;
        private long startTime;
        private long endTime;
        private int type;
        private String leaveReason;
        private int state;
        private long leaveTime;

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

        @Override
        public String toString() {
            return "LeaveListBean{" +
                    "leaveId=" + leaveId +
                    ", teacherId=" + teacherId +
                    ", createTime=" + createTime +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", type=" + type +
                    ", leaveReason='" + leaveReason + '\'' +
                    ", state=" + state +
                    ", leaveTime=" + leaveTime +
                    '}';
        }
    }

    public static class AttendanceListBean {
        private int teacherattendanceId;
        private int teacherId;
        private int amstate;
        private long createTime;
        private String timeCardImgUrl;
        private String qrcode;
        private long createDate;
        private int minuteTime;

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
    }
}
