package com.hellobaby.library.data.model;

import java.io.Serializable;

/**
 * Created by ShuWen on 2017/10/30.
 */

public class SchoolAllModel implements Serializable{
    private SchoolMasterModel.AttendanceListBean attendanceListBean;
    private SchoolMasterModel.ApprovalListBean approvalListBean;

    public SchoolMasterModel.AttendanceListBean getAttendanceListBean() {
        return attendanceListBean;
    }

    public void setAttendanceListBean(SchoolMasterModel.AttendanceListBean attendanceListBean) {
        this.attendanceListBean = attendanceListBean;
    }

    public SchoolMasterModel.ApprovalListBean getApprovalListBean() {
        return approvalListBean;
    }

    public void setApprovalListBean(SchoolMasterModel.ApprovalListBean approvalListBean) {
        this.approvalListBean = approvalListBean;
    }
}
