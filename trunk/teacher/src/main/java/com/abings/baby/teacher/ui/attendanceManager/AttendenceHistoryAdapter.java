package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AttendenceHistoryModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

/**
 * Created by ShuWen on 2017/7/4.
 */

public class AttendenceHistoryAdapter extends BaseAdapter<AttendenceHistoryModel.ListBean> {

    public AttendenceHistoryAdapter(Context context, List<AttendenceHistoryModel.ListBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final AttendenceHistoryModel.ListBean data) {
        LogZS.i("考勤记录每一项数据："+data.toString());

        TextView teacher_timecard_time = holder.getView(R.id.teacher_timecard_time);
        TextView teacher_state_am = holder.getView(R.id.teacher_state_am);
        TextView teacher_state_pm = holder.getView(R.id.teacher_state_pm);
        TextView teacher_attendance_time_am = holder.getView(R.id.teacher_attendance_time_am);
        TextView teacher_attendance_time_pm = holder.getView(R.id.teacher_attendance_time_pm);
        TextView teacher_name_am = holder.getView(R.id.teacher_name_am);
        TextView teacher_name_pm = holder.getView(R.id.teacher_name_pm);
        LinearLayout teacher_attendance_am = holder.getView(R.id.teacher_attendance_am);
        LinearLayout teacher_attendance_pm = holder.getView(R.id.teacher_attendance_pm);

        String time = DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"yyyy-MM-dd");
        String week = DateUtil.getWeekDay(time,"yyyy-MM-dd");
        teacher_timecard_time.setText(time+"("+week+")");
        String amState = null;
        String pmState = null;
        if (data.getAmTime() == 0){
            teacher_state_am.setText("未考勤");
            amState = "未考勤";
            teacher_attendance_time_am.setText("0:00");
        }else {
            switch (data.getAmstate()){
                case 1:
                    teacher_state_am.setText("上班正常");
                    amState = "上班正常";
                    teacher_attendance_time_am.setText(DateUtil.getFormatTimeFromTimestamp(data.getAmTime(),"HH:mm"));
                    break;
                case 2:
                    teacher_state_am.setText("上班迟到");
                    amState = "上班迟到";
                    teacher_attendance_time_am.setText(DateUtil.getFormatTimeFromTimestamp(data.getAmTime(),"HH:mm"));
                    break;
                default:
                    teacher_state_am.setText("未考勤");
                    amState = "未考勤";
                    teacher_attendance_time_am.setText("0:00");
                    break;
            }
        }

        if (data.getPmstate() == 0){
            teacher_state_pm.setText("未考勤");
            pmState = "未考勤";
            teacher_attendance_time_pm.setText("0:00");
        }else {
            switch (data.getPmstate()){
                case 3:
                    pmState = "下班早退";
                    teacher_state_pm.setText("下班早退");
                    teacher_attendance_time_pm.setText(DateUtil.getFormatTimeFromTimestamp(data.getPmTime(),"HH:mm"));
                    break;
                case 4:
                    pmState = "下班正常";
                    teacher_state_pm.setText("下班正常");
                    teacher_attendance_time_pm.setText(DateUtil.getFormatTimeFromTimestamp(data.getPmTime(),"HH:mm"));
                    break;
                default:
                    teacher_state_pm.setText("未考勤");
                    pmState = "未考勤";
                    teacher_attendance_time_pm.setText("0:00");
                    break;
            }
        }
        teacher_name_am.setText(data.getTeacherName());
        teacher_name_pm.setText(data.getTeacherName());

        final String finalAmState = amState;
        teacher_attendance_am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,AttendenceDetailActivity.class);
                intent.putExtra(AttendenceDetailActivity.TEACHER_NAME,data.getTeacherName());
                intent.putExtra("state", finalAmState);
                intent.putExtra("headImg", Const.URL_timeCardImg + data.getTimeCardImgUrl());
                mContext.startActivity(intent);
            }
        });
        final String finalPmState = pmState;
        teacher_attendance_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,AttendenceDetailActivity.class);
                intent.putExtra(AttendenceDetailActivity.TEACHER_NAME,data.getTeacherName());
                intent.putExtra("state", finalPmState);
                intent.putExtra("headImg", Const.URL_timeCardImg + data.getTimeCardImgUrlPm());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_teacher_attend_history;
    }

}
