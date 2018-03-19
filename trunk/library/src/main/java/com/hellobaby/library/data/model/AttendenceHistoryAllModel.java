package com.hellobaby.library.data.model;

/**
 * Created by ShuWen on 2017/11/27.
 */

public class AttendenceHistoryAllModel {
    private AttendenceLeaveHistoryModel.AttendanceListBean attendanceListBean;
    private AttendenceLeaveHistoryModel.LeaveListBean leaveListBean;

    public void setAttendanceListBean(AttendenceLeaveHistoryModel.AttendanceListBean attendanceListBean) {
        this.attendanceListBean = attendanceListBean;
    }

    public void setLeaveListBean(AttendenceLeaveHistoryModel.LeaveListBean leaveListBean) {
        this.leaveListBean = leaveListBean;
    }

    public AttendenceLeaveHistoryModel.AttendanceListBean getAttendanceListBean() {
        return attendanceListBean;
    }

    public AttendenceLeaveHistoryModel.LeaveListBean getLeaveListBean() {
        return leaveListBean;
    }
}
