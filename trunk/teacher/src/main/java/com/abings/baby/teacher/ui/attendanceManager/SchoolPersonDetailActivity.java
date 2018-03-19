package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AttendenceLeaveHistoryModel;
import com.hellobaby.library.data.model.SchoolAllModel;
import com.hellobaby.library.data.model.SchoolMasterAllChildModel;
import com.hellobaby.library.data.model.SchoolMasterModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SchoolPersonDetailActivity extends BaseTitleActivity {

    public static final String TEACHER_MODEL = "teacherModel";

    @BindView(R.id.recyclerView_person_detail)
    RecyclerView recyclerView;
    @BindView(R.id.text_null_ts)
    TextView textView;
    @BindView(R.id.person_date)
    TextView person_date;


    private BaseAdapter<SchoolAllModel> baseAdapter;
    private List<SchoolAllModel> schoolAllModels;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_school_person_detail;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        SchoolMasterAllChildModel childModel = (SchoolMasterAllChildModel) getIntent().getSerializableExtra(TEACHER_MODEL);
        if (childModel != null){
            schoolAllModels = new ArrayList<>();
            person_date.setText(childModel.getCreateTime());
            setTitleText(childModel.getTeacherName());
            LogZS.i("schoolAllModels ："+schoolAllModels.size());
            baseAdapter = new BaseAdapter<SchoolAllModel>(bContext,schoolAllModels,false) {
                @Override
                protected void convert(ViewHolder holder, final SchoolAllModel data) {
                    FrameLayout frameLayout = holder.getView(R.id.school_person_frame);
                    if (data.getAttendanceListBean() != null){
                        showFrame(frameLayout,0);
                        String stateStr = data.getAttendanceListBean().getAmstate() == 1 ?"到校":"离校";
                        ((TextView)holder.getView(R.id.item_school_state)).setText(stateStr);
                        String timeStr = DateUtil.getFormatTimeFromTimestamp(data.getAttendanceListBean().getCreateTime(),"HH:mm");
                        ((TextView)holder.getView(R.id.item_school_time)).setText(timeStr);
                        ImageView imageView = (ImageView)holder.getView(R.id.item_school_img);
                        String url = Const.URL_timeCardImg + data.getAttendanceListBean().getTimeCardImgUrl();
                        ImageLoader.loadHeadTarget(bContext,url,imageView);
                        holder.getView(R.id.school_person_rv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(bContext,AttendenceDetailActivity.class);
                                String finalPmState = data.getAttendanceListBean().getAmstate() == 1?"到校":"离校";
                                intent.putExtra(AttendenceDetailActivity.TEACHER_NAME,data.getAttendanceListBean().getTeacherName());
                                intent.putExtra("state", finalPmState);
                                intent.putExtra("headImg", Const.URL_timeCardImg + data.getAttendanceListBean().getTimeCardImgUrl());
                                bContext.startActivity(intent);
                            }
                        });
                    }else{
                        showFrame(frameLayout,1);
                        ((TextView)holder.getView(R.id.item_school_start_time)).setText(DateUtil.getFormatTimeFromTimestamp(data.getApprovalListBean().getStartTime(),"yyyy.MM.dd HH:mm"));
                        ((TextView)holder.getView(R.id.item_school_end_time)).setText(DateUtil.getFormatTimeFromTimestamp(data.getApprovalListBean().getEndTime(),"yyyy.MM.dd HH:mm"));
                        holder.getView(R.id.school_person_ll).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(bContext,AskForLeaveDetailActivity.class);
                                intent.putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,2);
                                AskForLeaveHistoryModel historyModel = initAskForLeaveModel(data.getApprovalListBean());
                                intent.putExtra(AskForLeaveDetailActivity.HISTORY_MODEL,historyModel);
                                bContext.startActivity(intent);
                            }
                        });
                    }
                }
                @Override
                protected int getItemLayoutId() {
                    return R.layout.item_school_person_detail_child;
                }
            };
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(bContext));
            recyclerView.setAdapter(baseAdapter);
            schoolAllModels.addAll(childModel.getAllModels());
            baseAdapter.notifyDataSetChanged();
            LogZS.i("schoolAllModels ："+schoolAllModels.size());
        }else{
            textView.setVisibility(View.VISIBLE);
        }

    }

    private AskForLeaveHistoryModel initAskForLeaveModel(SchoolMasterModel.ApprovalListBean leaveListBean) {
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

    private void showFrame(FrameLayout frameLayout, int childId) {
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            if (i == childId){
                frameLayout.getChildAt(childId).setVisibility(View.VISIBLE);
            }else{
                frameLayout.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showData(Object o) {

    }
}
