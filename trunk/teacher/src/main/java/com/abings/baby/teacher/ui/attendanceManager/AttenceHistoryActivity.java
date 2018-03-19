package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.AttendenceHistoryAllModel;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.ui.publish.video.ProgressHandler;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AttenceHistoryActivity extends BaseTitleActivity<AttendenceLeaveHistoryModel> {

    @Inject
    AttendencePresenter presenter;

    @BindView(R.id.techer_attendence_swipeRefresh_expand_lv)
    ExpandableListView expandableListView;
    @BindView(R.id.text_null_ts)
    TextView textViewNull;

    private AttendenceHistoryAdapterNew adapter;
    private HashMap<String,List<AttendenceHistoryAllModel>> parentMaps;
    private List<String> parentList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attence_history;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        bTitleBaseRL.setVisibility(View.GONE);
        adapter = new AttendenceHistoryAdapterNew(this);
        parentMaps = new HashMap<>();
        parentList = new ArrayList<>();
        adapter = new AttendenceHistoryAdapterNew(this);
        adapter.setParentsMaps(parentMaps).setParentLists(parentList);
        expandableListView.setAdapter(adapter);
        presenter.getAttendenceHistory();
        showProgress(true);
    }


    @OnClick({R.id.libTitle_iv_left_leave,R.id.ask_leave_history})
    public void attendenceClick(View view){
        switch (view.getId()){
            case R.id.libTitle_iv_left_leave:
                finish();
                break;
            case R.id.ask_leave_history:
                startActivity(new Intent(this,AskForLeaveHistoryActivity.class));
                break;
        }
    }

    @Override
    public void showData(AttendenceLeaveHistoryModel model) {
        initHistoryModelData(model);
    }

    private void initHistoryModelData(AttendenceLeaveHistoryModel model) {
        if (model != null && model.getAttendanceList().size() > 0 || model!= null && model.getLeaveList().size() > 0){
            textViewNull.setVisibility(View.GONE);
            List<AttendenceLeaveHistoryModel.AttendanceListBean> attendanceListBeens = model.getAttendanceList();
            List<AttendenceLeaveHistoryModel.LeaveListBean> leaveListBeens = model.getLeaveList();
            if (attendanceListBeens != null && attendanceListBeens.size() > 0){
                for (int i = 0; i < attendanceListBeens.size(); i++) {
                    AttendenceLeaveHistoryModel.AttendanceListBean attendanceListBean = attendanceListBeens.get(i);
                    String dateAndWeek = getDateTimeWeek(attendanceListBean.getCreateTime());
                    AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                    allModel.setAttendanceListBean(attendanceListBean);
                    if (!parentList.contains(dateAndWeek)){
                        parentList.add(dateAndWeek);
                        List<AttendenceHistoryAllModel> allModels = new ArrayList<>();
                        allModels.add(allModel);
                        parentMaps.put(dateAndWeek,allModels);
                    }else {
                        parentMaps.get(dateAndWeek).add(allModel);
                    }
                }
            }

            if (leaveListBeens != null &&  leaveListBeens.size() > 0){
                for (int i = 0; i < leaveListBeens.size(); i++) {
                    AttendenceLeaveHistoryModel.LeaveListBean leaveListBean = leaveListBeens.get(i);
                    List<Date> leaveDates = DateUtil.getDateFromTwoDate(new Date(leaveListBean.getStartTime()),new Date(leaveListBean.getEndTime()));
                    List<AttendenceLeaveHistoryModel.LeaveListBean> allLeavebeans = getAttendenceList(leaveDates,leaveListBean);
                    for (int j = 0; j < allLeavebeans.size(); j++) {
                        AttendenceLeaveHistoryModel.LeaveListBean bean = allLeavebeans.get(j);

                        String dateStrStart = getDateTimeWeek(bean.getCreateTime());
                        //开始日期显示请假
                        if (parentMaps.containsKey(dateStrStart)){
                            AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                            allModel.setLeaveListBean(leaveListBean);
                            parentMaps.get(dateStrStart).add(allModel);
                            //当日没有考勤，但是有请假，也是需要记录的
                        }else{
                            List<AttendenceHistoryAllModel> allModels  = new ArrayList<>();
                            AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                            parentList.add(dateStrStart);
                            allModel.setLeaveListBean(leaveListBean);
                            allModels.add(allModel);
                            parentMaps.put(dateStrStart,allModels);
                        }
                    }
                }
            }
            Collections.sort(parentList);
            Collections.reverse(parentList);
            showProgress(false);
            adapter.notifyDataSetChanged();
            expandableListView.expandGroup(0);
        }else {
            textViewNull.setVisibility(View.VISIBLE);
        }
    }

    private List<AttendenceLeaveHistoryModel.LeaveListBean> getAttendenceList(List<Date> leaveDates, AttendenceLeaveHistoryModel.LeaveListBean leaveListBean) {
        List<AttendenceLeaveHistoryModel.LeaveListBean> listBeanList = new ArrayList<>();
        for (int i = 0; i < leaveDates.size(); i++) {
            AttendenceLeaveHistoryModel.LeaveListBean bean = new AttendenceLeaveHistoryModel.LeaveListBean();
            bean.setCreateTime(leaveDates.get(i).getTime());
            bean.setStartTime(leaveListBean.getStartTime());
            bean.setEndTime(leaveListBean.getEndTime());
            bean.setLeaveReason(leaveListBean.getLeaveReason());
            bean.setLeaveId(leaveListBean.getLeaveId());
            bean.setState(leaveListBean.getState());
            bean.setTeacherId(leaveListBean.getTeacherId());
            bean.setType(leaveListBean.getType());
            bean.setLeaveTime(leaveListBean.getLeaveTime());
            listBeanList.add(bean);
        }
        return listBeanList;
    }

    private String getDateTimeWeek(long createTime) {
        String dateStrStart = DateUtil.getFormatTimeFromTimestamp(createTime,"yyyy.MM.dd");
        String weekDay = DateUtil.getWeekDay(dateStrStart,"yyyy.MM.dd");
        String dateAndWeek = dateStrStart+" ("+weekDay+")";
        return dateAndWeek;
    }
}
