package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AttendenceHistoryAllModel;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ShuWen on 2017/11/24.
 */

public class AttendenceHistoryAdapterNew extends BaseExpandableListAdapter{

    private HashMap<String,List<AttendenceHistoryAllModel>> parentsMaps;
    private List<String> parentLists;
    private Context context;

    public AttendenceHistoryAdapterNew(Context context) {
        this.context = context;
    }

    public AttendenceHistoryAdapterNew setParentsMaps(HashMap<String, List<AttendenceHistoryAllModel>> parentsMaps) {
        this.parentsMaps = parentsMaps;
        return this;
    }

    public AttendenceHistoryAdapterNew setParentLists(List<String> parentLists) {
        this.parentLists = parentLists;
        return this;
    }

    @Override
    public int getGroupCount() {
        return parentsMaps.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return parentsMaps.get(parentLists.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return parentsMaps.get(parentLists.get(i));
    }

    @Override
    public Object getChild(int i, int i1) {
        return parentsMaps.get(parentLists.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_attend_histerory_group, null);
        }
        view.setTag(R.layout.item_attend_histerory_group, i);
        view.setTag(R.layout.item_history_attendence_child, -1);
        TextView textView = (TextView) view.findViewById(R.id.item_group_date);
        textView.setText(parentLists.get(i));
        return view;
    }

    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        AttendenceHistoryAllModel  child_item = parentsMaps.get(parentLists.get(parentPos)).get(childPos);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int tag = -1;
        if (child_item.getAttendanceListBean() != null) {
            view = inflater.inflate(R.layout.item_history_attendence_child, null);
            tag = R.layout.item_history_attendence_child;
            initAttendenceChild(view,child_item.getAttendanceListBean());
        }else {
            view = inflater.inflate(R.layout.item_history_leave_child, null);
            tag = R.layout.item_history_leave_child;
            initLeaveChild(view,child_item.getLeaveListBean());
        }
        view.setTag(R.layout.item_attend_histerory_group, parentPos);
        view.setTag(tag, childPos);
        return view;
    }

    private void initLeaveChild(View view, final AttendenceLeaveHistoryModel.LeaveListBean leaveListBean) {
        TextView leaveStart = (TextView) view.findViewById(R.id.teacher_leave_time_start_child);
        TextView leaveEnd = (TextView) view.findViewById(R.id.teacher_leave_time_end_child);
        leaveStart.setText(DateUtil.getFormatTimeFromTimestamp(leaveListBean.getStartTime(),"yyyy.MM.dd  HH:mm"));
        leaveEnd.setText(DateUtil.getFormatTimeFromTimestamp(leaveListBean.getEndTime(),"yyyy.MM.dd  HH:mm"));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.leave_child_ll);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AskForLeaveDetailActivity.class);
                intent.putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,2);
                AskForLeaveHistoryModel historyModel = initAskForLeaveModel(leaveListBean);
                intent.putExtra(AskForLeaveDetailActivity.HISTORY_MODEL,historyModel);
                context.startActivity(intent);
            }
        });
    }

    private AskForLeaveHistoryModel initAskForLeaveModel(AttendenceLeaveHistoryModel.LeaveListBean leaveListBean) {
        AskForLeaveHistoryModel  historyModel = new AskForLeaveHistoryModel();
        historyModel.setCreateTime(leaveListBean.getCreateTime());
        historyModel.setStartTime(leaveListBean.getStartTime());
        historyModel.setEndTime(leaveListBean.getEndTime());
        historyModel.setLeaveReason(leaveListBean.getLeaveReason());
        historyModel.setLeaveId(leaveListBean.getLeaveId());
        historyModel.setState(leaveListBean.getState());
        historyModel.setTeacherId(leaveListBean.getTeacherId());
        historyModel.setLeaveTime(leaveListBean.getLeaveTime());
        historyModel.setType(leaveListBean.getType());
        return historyModel;
    }

    private void initAttendenceChild(View view, final AttendenceLeaveHistoryModel.AttendanceListBean attendanceListBean) {
        TextView teacher_state = (TextView) view.findViewById(R.id.teacher_state_child);
        TextView teacher_attendance_time = (TextView) view.findViewById(R.id.teacher_attendance_time_child);
        CircleImageView attendance_iv = (CircleImageView) view.findViewById(R.id.teacher_attendance_iv_child);
        teacher_attendance_time.setText(DateUtil.getFormatTimeFromTimestamp(attendanceListBean.getCreateTime(),"HH:mm"));
        String timeState = attendanceListBean.getAmstate() == 1?"到校" : "离校";
        teacher_state.setText(timeState);
        String imgUrl = Const.URL_timeCardImg + attendanceListBean.getTimeCardImgUrl();
        ImageLoader.loadHeadTarget(context,imgUrl,attendance_iv);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.teacher_attendance_child);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AttendenceDetailActivity.class);
                String finalPmState = attendanceListBean.getAmstate() == 1?"到校":"离校";
                intent.putExtra("state", finalPmState);
                intent.putExtra("headImg", Const.URL_timeCardImg + attendanceListBean.getTimeCardImgUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
