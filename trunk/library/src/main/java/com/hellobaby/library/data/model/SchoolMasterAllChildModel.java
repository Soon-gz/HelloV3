package com.hellobaby.library.data.model;

import com.hellobaby.library.utils.DateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ShuWen on 2017/11/30.
 */

public class SchoolMasterAllChildModel implements Serializable{

    private List<SchoolAllModel> allModels;

    public List<SchoolAllModel> getAllModels() {
        return allModels;
    }

    public void setAllModels(List<SchoolAllModel> allModels) {
        this.allModels = allModels;
    }

    /**
     * 获取最早打卡时间
     * @return
     */
    public String getArriveTime(){
        long minArriveTime = Long.MAX_VALUE;
        if (allModels != null && allModels.size() > 0){
            boolean hasArrive = false;
            for (int i = 0; i < allModels.size(); i++) {
                if (allModels.get(i).getAttendanceListBean() != null && allModels.get(i).getAttendanceListBean().getAmstate() == 1){
                    if (allModels.get(i).getAttendanceListBean().getCreateTime() < minArriveTime){
                        hasArrive = true;
                        minArriveTime = allModels.get(i).getAttendanceListBean().getCreateTime();
                    }
                }
            }
            if (hasArrive){
                String arriveTime = DateUtil.getFormatTimeFromTimestamp(minArriveTime,"HH:mm");
                return arriveTime;
            }else{
                return "未考勤";
            }
        }
        return "未考勤";
    }

    public String getCreateTime(){
        String dateTime = "";
        if (allModels != null && allModels.size() > 0){
            if (allModels.get(0).getAttendanceListBean() != null){
                String dateTimeStr = DateUtil.getFormatTimeFromTimestamp(allModels.get(0).getAttendanceListBean().getCreateTime(),"yyyy.MM.dd");
                String weekDay = DateUtil.getWeekDay(dateTimeStr,"yyyy.MM.dd");
                dateTime = dateTimeStr+"("+weekDay+")";
            }else if (allModels.get(0).getApprovalListBean() != null){
                String dateTimeStr = DateUtil.getFormatTimeFromTimestamp(allModels.get(0).getApprovalListBean().getCreateTime(),"yyyy.MM.dd");
                String weekDay = DateUtil.getWeekDay(dateTimeStr,"yyyy.MM.dd");
                dateTime = dateTimeStr+"("+weekDay+")";
            }
        }
        return dateTime;
    }

    public String getTeacherName(){
        String teacherName = "";
        if (allModels != null && allModels.size() > 0){
            if (allModels.get(0).getAttendanceListBean() != null){
                teacherName = allModels.get(0).getAttendanceListBean().getTeacherName();
            }else if (allModels.get(0).getApprovalListBean() != null){
                teacherName = allModels.get(0).getApprovalListBean().getTeacherName();
            }
        }
        return teacherName;
    }

    /**
     * 获得最晚打卡时间
     * @return
     */
    public String getLeaveTime(){
        long maxArriveTime = Long.MIN_VALUE;
        if (allModels != null && allModels.size() > 0){
            for (int i = 0; i < allModels.size(); i++) {
                if (allModels.get(i).getAttendanceListBean() != null && allModels.get(i).getAttendanceListBean().getAmstate() == 2){
                    if (allModels.get(i).getAttendanceListBean().getCreateTime() > maxArriveTime){
                        maxArriveTime = allModels.get(i).getAttendanceListBean().getCreateTime();
                    }
                }
            }
            if (maxArriveTime > 0){
                String leaveTime = DateUtil.getFormatTimeFromTimestamp(maxArriveTime,"HH:mm");
                return leaveTime;
            }else{
                return "未考勤";
            }
        }
        return "未考勤";
    }

    /**
     * 用于判断是否展示假的图标
     * @return
     */
    public boolean hasLeaveRequest(){
        boolean isAskForLeave = false;
        if (allModels!= null && allModels.size() > 0){
            for (int i = 0; i < allModels.size(); i++) {
                if (allModels.get(i).getApprovalListBean() != null ){
                    isAskForLeave = true;
                    break;
                }
            }
        }
        return isAskForLeave;
    }
}
