package com.hellobaby.library.data.model;

/**
 * Created by Administrator on 2017/4/27.
 */

public class TeacherAlertModel {
    /**
     * teacherId : 1
     * schoolLastMaxTime :
     * msgLastMaxTime :
     * sysmsgLastMaxTime :
     */

    private int teacherId;
    private Long schoolLastMaxTime;
    private Long msgLastMaxTime;
    private Long sysmsgLastMaxTime;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSchoolLastMaxTime() {
        return schoolLastMaxTime;
    }

    public void setSchoolLastMaxTime(Long schoolLastMaxTime) {
        this.schoolLastMaxTime = schoolLastMaxTime;
    }

    public Long getMsgLastMaxTime() {
        return msgLastMaxTime;
    }

    public void setMsgLastMaxTime(Long msgLastMaxTime) {
        this.msgLastMaxTime = msgLastMaxTime;
    }

    public Long getSysmsgLastMaxTime() {
        return sysmsgLastMaxTime;
    }

    public void setSysmsgLastMaxTime(Long sysmsgLastMaxTime) {
        this.sysmsgLastMaxTime = sysmsgLastMaxTime;
    }
}
