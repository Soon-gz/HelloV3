package com.abings.baby.teacher.ui.attendanceManager;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.AttendenceHistoryAllModel;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.data.model.SchoolAllModel;
import com.hellobaby.library.data.model.SchoolMasterAllChildModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SchoolMasterFragment extends BaseLibFragment implements SchoolMasterMvpview{
    private static final String ARG_PARAM1 = "param1";
    public static final String MIME_ATTENDANCE = "mineAttendance";//校长自己的考勤记录
    public static final String ALL_ATTENDANCE = "allAttendance";//全校的考勤记录

    private String mParam1;

    @Inject
    AttendencePresenter presenter;

    @BindView(R.id.school_master_recyclerView)
    ExpandableListView expandableListView;
    @BindView(R.id.school_master_swipeRefresh)
    SwipeRefreshLayout swipeRefreshMaster;
    @BindView(R.id.null_text)
    TextView textViewNull;

    private AttendenceHistoryAdapterNew mineAdapter;//校长自己的考勤
    private List<String> parentList;//校长自己考勤的父集合
    private HashMap<String,List<AttendenceHistoryAllModel>> parentMaps;//校长自己考勤的父集合
    private boolean isNeedClear = false;

    private SchoolAllAttendenceAdapter schoolAllAttendenceAdapter;//学校所有老师考勤记录
    private List<String> schoolParentList;//学校考勤日期
    private HashMap<String,List<String>> childrenNames;//教师的用户名集合
    private HashMap<String,HashMap<String,SchoolMasterAllChildModel>> schoolParentMaps;//第一个String是日期，第二个参数String，老师名字

    public SchoolMasterFragment() {

    }

    public static SchoolMasterFragment newInstance(String param1) {
        SchoolMasterFragment fragment = new SchoolMasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_school_master;
    }

    @Override
    protected void initViewsAndEvents() {
        presenter.attachView(this);
        switch (mParam1){
            //校长考勤
            case MIME_ATTENDANCE:
                initMineAttendence();
                break;
            //全校考勤
            case ALL_ATTENDANCE:
                initSchoolAllAttendence();
                break;
        }
    }

    /**
     * 初始化全校考勤
     */
    private void initSchoolAllAttendence() {
        schoolParentList = new ArrayList<>();
        schoolParentMaps = new HashMap<>();
        childrenNames = new HashMap<>();
        schoolAllAttendenceAdapter = new SchoolAllAttendenceAdapter(getActivity());
        schoolAllAttendenceAdapter.setParentLists(schoolParentList).setChildrenNames(childrenNames).setParentsMaps(schoolParentMaps);
        expandableListView.setAdapter(schoolAllAttendenceAdapter);
        showProgress(true);
        swipeRefreshMaster.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNeedClear = true;
                presenter.selectLeaveList(Integer.parseInt(ZSApp.getInstance().getSchoolId()));
            }
        });
        presenter.selectLeaveList(Integer.parseInt(ZSApp.getInstance().getSchoolId()));
    }

    /**
     * 初始化校长考勤
     */
    private void initMineAttendence() {
        parentList = new ArrayList<>();
        parentMaps = new HashMap<>();
        mineAdapter = new AttendenceHistoryAdapterNew(getActivity());
        mineAdapter.setParentLists(parentList).setParentsMaps(parentMaps);
        expandableListView.setAdapter(mineAdapter);
        showProgress(true);
        swipeRefreshMaster.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNeedClear = true;
                presenter.getAttendenceHistoryMaster();
            }
        });
        presenter.getAttendenceHistoryMaster();
    }

    private void initHistoryModelData(AttendenceLeaveHistoryModel model) {
        if (model != null && model.getAttendanceList().size() > 0 || model != null && model.getLeaveList().size() > 0 ) {
            expandableListView.setVisibility(View.VISIBLE);
            textViewNull.setVisibility(View.GONE);
            if (isNeedClear){
                isNeedClear = false;
                parentList.clear();
                parentMaps.clear();
            }
            List<AttendenceLeaveHistoryModel.AttendanceListBean> attendanceListBeens = model.getAttendanceList();
            List<AttendenceLeaveHistoryModel.LeaveListBean> leaveListBeens = model.getLeaveList();
            if (attendanceListBeens != null && attendanceListBeens.size() > 0) {
                for (int i = 0; i < attendanceListBeens.size(); i++) {
                    AttendenceLeaveHistoryModel.AttendanceListBean attendanceListBean = attendanceListBeens.get(i);
                    String dateAndWeek = getDateTimeWeek(attendanceListBean.getCreateTime());
                    AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                    allModel.setAttendanceListBean(attendanceListBean);
                    if (!parentList.contains(dateAndWeek)) {
                        parentList.add(dateAndWeek);
                        List<AttendenceHistoryAllModel> allModels = new ArrayList<>();
                        allModels.add(allModel);
                        parentMaps.put(dateAndWeek, allModels);
                    } else {
                        parentMaps.get(dateAndWeek).add(allModel);
                    }
                }
            }

            if (leaveListBeens != null && leaveListBeens.size() > 0) {
                for (int i = 0; i < leaveListBeens.size(); i++) {
                    AttendenceLeaveHistoryModel.LeaveListBean leaveListBean = leaveListBeens.get(i);
                    List<Date> leaveDates = DateUtil.getDateFromTwoDate(new Date(leaveListBean.getStartTime()), new Date(leaveListBean.getEndTime()));
                    List<AttendenceLeaveHistoryModel.LeaveListBean> allLeavebeans = getAttendenceList(leaveDates, leaveListBean);
                    for (int j = 0; j < allLeavebeans.size(); j++) {
                        AttendenceLeaveHistoryModel.LeaveListBean bean = allLeavebeans.get(j);

                        String dateStrStart = getDateTimeWeek(bean.getCreateTime());
                        boolean isHasAttence = false;
                        //开始日期显示请假
                        if (parentMaps.containsKey(dateStrStart)) {
                            AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                            allModel.setLeaveListBean(leaveListBean);
                            parentMaps.get(dateStrStart).add(allModel);
                            isHasAttence = true;
                        }
                        //当日没有考勤，但是有请假，也是需要记录的
                        if (!isHasAttence) {
                            List<AttendenceHistoryAllModel> allModels = new ArrayList<>();
                            AttendenceHistoryAllModel allModel = new AttendenceHistoryAllModel();
                            parentList.add(dateStrStart);
                            allModel.setLeaveListBean(leaveListBean);
                            allModels.add(allModel);
                            parentMaps.put(dateStrStart, allModels);
                        }
                    }
                }
            }
            Collections.sort(parentList);
            Collections.reverse(parentList);
            mineAdapter.notifyDataSetChanged();
            expandableListView.expandGroup(0);
        }else{
            expandableListView.setVisibility(View.GONE);
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

    @Override
    public void showData(Object o) {
    }

    /**
     * 校长自己的考勤
     * @param historyModel
     */
    @Override
    public void masterSelfData(AttendenceLeaveHistoryModel historyModel) {
        if (swipeRefreshMaster.isRefreshing()){
            swipeRefreshMaster.setRefreshing(false);
        }
        showProgress(false);
        initHistoryModelData(historyModel);
    }

    /**
     * 学校所有人的考勤记录
     * @param schoolMasterModel
     */
    @Override
    public void allSchoolData(SchoolMasterModel schoolMasterModel) {
        if (swipeRefreshMaster.isRefreshing()){
            swipeRefreshMaster.setRefreshing(false);
        }
        showProgress(false);
        initSchoolAllModelData(schoolMasterModel);
    }

    private void initSchoolAllModelData(SchoolMasterModel schoolMasterModel) {
        if (schoolMasterModel != null && schoolMasterModel.getAttendanceList().size() > 0 || schoolMasterModel != null && schoolMasterModel.getApprovalList().size() > 0){
            textViewNull.setVisibility(View.GONE);
            if (isNeedClear){
                isNeedClear = false;
                childrenNames.clear();
                schoolParentList.clear();
                schoolParentMaps.clear();
            }
            List<SchoolMasterModel.AttendanceListBean> attendanceListBeens = schoolMasterModel.getAttendanceList();
            List<SchoolMasterModel.ApprovalListBean> approvalList = schoolMasterModel.getApprovalList();
            if (attendanceListBeens!= null && attendanceListBeens.size() > 0){
                for (int i = 0; i < attendanceListBeens.size(); i++) {
                    SchoolMasterModel.AttendanceListBean attendanceListBean = attendanceListBeens.get(i);
                    String dateAndWeek = getDateTimeWeek(attendanceListBean.getCreateTime());
                    //当前数据没有这个日期  相应的子集合hashmap（用户名为键，值为当天那个用户考勤和请假的集合）没有，
                    if (!schoolParentList.contains(dateAndWeek)){
                        schoolParentList.add(dateAndWeek);
                        List<String> strings = new ArrayList<>();
                        strings.add(attendanceListBean.getTeacherName());
                        childrenNames.put(dateAndWeek,strings);

                        //包裹单个数据
                        SchoolAllModel allModel = new SchoolAllModel();
                        allModel.setAttendanceListBean(attendanceListBean);
                        //每一条数据对应的集合  即每个人的考勤记录和请假记录集合
                        List<SchoolAllModel> schoolAllModels = new ArrayList<>();
                        schoolAllModels.add(allModel);
                        //对单个人的数据进行整合  方便界面绘制
                        SchoolMasterAllChildModel childModel = new SchoolMasterAllChildModel();
                        childModel.setAllModels(schoolAllModels);

                        //使用HashMap将用户名和单个个人数据匹配
                        HashMap<String,SchoolMasterAllChildModel> childrens = new HashMap<>();
                        childrens.put(attendanceListBean.getTeacherName(),childModel);

                        schoolParentMaps.put(dateAndWeek,childrens);
                        //当前外层已有日期，那么就看是否有这个名字
                    }else {
                        HashMap<String,SchoolMasterAllChildModel> childrens2 = schoolParentMaps.get(dateAndWeek);
                        List<String> strings = childrenNames.get(dateAndWeek);
                        String userName = attendanceListBean.getTeacherName();
                        //如果没有这个名字
                        if (!childrens2.containsKey(userName)){

                            //包裹单个数据
                            SchoolAllModel allModel = new SchoolAllModel();
                            allModel.setAttendanceListBean(attendanceListBean);
                            //每一条数据对应的集合  即每个人的考勤记录和请假记录集合
                            List<SchoolAllModel> schoolAllModels = new ArrayList<>();
                            schoolAllModels.add(allModel);
                            //对单个人的数据进行整合  方便界面绘制
                            SchoolMasterAllChildModel childModel = new SchoolMasterAllChildModel();
                            childModel.setAllModels(schoolAllModels);

                            //使用HashMap将用户名和单个个人数据匹配
                            childrens2.put(attendanceListBean.getTeacherName(),childModel);
                            strings.add(userName);
                            //如果有这个老师的名字
                        }else {
                            SchoolMasterAllChildModel childModel2 = childrens2.get(userName);
                            List<SchoolAllModel> schoolAllModels2 = childModel2.getAllModels();
                            //包裹单个数据
                            SchoolAllModel allModel = new SchoolAllModel();
                            allModel.setAttendanceListBean(attendanceListBean);
                            schoolAllModels2.add(allModel);
                        }
                    }
                }
            }

            if (approvalList != null && approvalList.size() > 0){
                for (int i = 0; i < approvalList.size(); i++) {
                    SchoolMasterModel.ApprovalListBean approvalListBean = approvalList.get(i);
                    List<Date> leaveDates = DateUtil.getDateFromTwoDate(new Date(approvalListBean.getStartTime()),new Date(approvalListBean.getEndTime()));
                    List<SchoolMasterModel.ApprovalListBean> approvalListBeanList = getApprovalList(leaveDates,approvalListBean);
                    for (int j = 0; j < approvalListBeanList.size(); j++) {
                        SchoolMasterModel.ApprovalListBean listBean = approvalListBeanList.get(j);

                        String dateStrCreate = getDateTimeWeek(listBean.getCreateTime());
                        //已经有考勤了
                        if (schoolParentMaps.containsKey(dateStrCreate)){
                            String userName = listBean.getTeacherName();
                            HashMap<String,SchoolMasterAllChildModel> hashMap = schoolParentMaps.get(dateStrCreate);
                            List<String> strings = childrenNames.get(dateStrCreate);
                            //当天已经有这个人的考勤记录了
                            if (hashMap.containsKey(userName)){
                                SchoolMasterAllChildModel masterAllChildModel = hashMap.get(userName);
                                List<SchoolAllModel> schoolAllModels = masterAllChildModel.getAllModels();

                                //包裹单个数据
                                SchoolAllModel allModel = new SchoolAllModel();
                                allModel.setApprovalListBean(listBean);
                                //添加请假记录
                                schoolAllModels.add(allModel);
                                //当天没有这个人的考勤记录
                            }else{
                                strings.add(userName);
                                SchoolMasterAllChildModel masterAllChildModel = new SchoolMasterAllChildModel();
                                List<SchoolAllModel> schoolAllModels = new ArrayList<>();
                                //包裹单个数据
                                SchoolAllModel allModel = new SchoolAllModel();
                                allModel.setApprovalListBean(listBean);
                                schoolAllModels.add(allModel);

                                masterAllChildModel.setAllModels(schoolAllModels);
                                hashMap.put(userName,masterAllChildModel);
                            }
                            //当天没有考勤
                        }else{
                            HashMap<String,SchoolMasterAllChildModel> hashMap = new HashMap<>();
                            SchoolMasterAllChildModel masterAllChildModel = new SchoolMasterAllChildModel();
                            List<SchoolAllModel> schoolAllModels = new ArrayList<>();
                            List<String> strings = new ArrayList<>();

                            //包裹单个数据
                            SchoolAllModel allModel = new SchoolAllModel();
                            allModel.setApprovalListBean(listBean);

                            schoolAllModels.add(allModel);
                            masterAllChildModel.setAllModels(schoolAllModels);
                            String userName = listBean.getTeacherName();
                            hashMap.put(userName,masterAllChildModel);

                            schoolParentList.add(dateStrCreate);
                            strings.add(userName);
                            childrenNames.put(dateStrCreate,strings);
                            schoolParentMaps.put(dateStrCreate,hashMap);
                        }
                    }
                }
            }
            Collections.sort(schoolParentList);
            Collections.reverse(schoolParentList);
            schoolAllAttendenceAdapter.notifyDataSetChanged();
            expandableListView.expandGroup(0);
        }else {
            textViewNull.setVisibility(View.VISIBLE);
        }
    }

    private List<SchoolMasterModel.ApprovalListBean> getApprovalList(List<Date> leaveDates, SchoolMasterModel.ApprovalListBean approvalListBean) {
        List<SchoolMasterModel.ApprovalListBean> listBeanList = new ArrayList<>();
        for (int i = 0; i < leaveDates.size(); i++) {
            SchoolMasterModel.ApprovalListBean bean = new SchoolMasterModel.ApprovalListBean();
            bean.setCreateTime(leaveDates.get(i).getTime());
            bean.setStartTime(approvalListBean.getStartTime());
            bean.setEndTime(approvalListBean.getEndTime());
            bean.setLeaveReason(approvalListBean.getLeaveReason());
            bean.setLeaveId(approvalListBean.getLeaveId());
            bean.setState(approvalListBean.getState());
            bean.setTeacherId(approvalListBean.getTeacherId());
            bean.setType(approvalListBean.getType());
            bean.setLeaveTime(approvalListBean.getLeaveTime());
            bean.setTeacherName(approvalListBean.getTeacherName());
            listBeanList.add(bean);
        }
        return listBeanList;
    }
}
