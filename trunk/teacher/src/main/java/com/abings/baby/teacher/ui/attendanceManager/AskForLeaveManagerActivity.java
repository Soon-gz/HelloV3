package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.data.model.AskForLeaveModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AskForLeaveManagerActivity extends BaseLibTitleActivity implements AskForLeaveMvpView{

    public static final int REQUEST_MANAGER_CODE = 11;
    public static final int RESULT_MANAGER_CODE = 12;

    @Inject
    AskForLeavePresenter presenter;

    @BindView(R.id.ask_for_leave_swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ask_for_leave_RecyclerView)
    RecyclerView recyclerViewForLeave;
    @BindView(R.id.text_null_ts)
    TextView textViewNull;

    private List<AskForLeaveModel> modelList;
    private BaseAdapter<AskForLeaveModel> baseAdapter;
    private boolean isNeedClear = false;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ask_for_leave;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);

        setBtnLeftClickFinish();
        setTitleText("请假审批");

        modelList = new ArrayList<>();
        baseAdapter = new BaseAdapter<AskForLeaveModel>(bContext,modelList,false) {
            @Override
            protected void convert(ViewHolder holder, final AskForLeaveModel data) {
                ((TextView)holder.getView(R.id.ask_for_leave_name)).setText(data.getTeacherName());
                ((TextView)holder.getView(R.id.ask_for_leave_time)).setText(DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"yyyy.MM.dd"));
                ((TextView)holder.getView(R.id.ask_for_leave_start_time)).setText("("+DateUtil.getFormatTimeFromTimestamp(data.getStartTime(),"yyyy.MM.dd HH:mm")+" - ");
                ((TextView)holder.getView(R.id.ask_for_leave_end_time)).setText(DateUtil.getFormatTimeFromTimestamp(data.getEndTime(),"yyyy.MM.dd HH:mm")+")");
                int child = data.getState() == 0 ? 0:1;
                LinearLayout ask_for_leave_manager = holder.getView(R.id.ask_for_leave_manager);
                int bgColor = data.getState() == 0?R.color.white:R.color.normal_background;
                ask_for_leave_manager.setBackgroundResource(bgColor);
                FrameLayout frameLayout = (FrameLayout) holder.getView(R.id.right_bottom_fm);
                showFrame(frameLayout,child);
                switch (child){
                    case 0:
                        holder.getView(R.id.ask_for_leave_agree).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                presenter.updateLeaveState(data.getLeaveId(),1);
                            }
                        });
                        holder.getView(R.id.ask_for_leave_disagree).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                presenter.updateLeaveState(data.getLeaveId(),2);
                            }
                        });
                        break;
                    case 1:
                        String stateStr = data.getState() == 1?"已批准":"已拒绝";
                        int textColor = data.getState() == 1?getResources().getColor(R.color.video_green):getResources().getColor(R.color.text_red);
                        ((TextView)holder.getView(R.id.ask_for_result)).setText(stateStr);
                        ((TextView)holder.getView(R.id.ask_for_result)).setTextColor(textColor);
                        break;
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_ask_for_leave;
            }
        };
        recyclerViewForLeave.setItemAnimator(new DefaultItemAnimator());
        recyclerViewForLeave.setHasFixedSize(true);
        recyclerViewForLeave.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewForLeave.setAdapter(baseAdapter);
        recyclerViewForLeave.setNestedScrollingEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNeedClear = true;
                presenter.selectApprovalList();
            }
        });
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<AskForLeaveModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, AskForLeaveModel data, int position) {
                Intent intent = new Intent(bContext,AskForLeaveDetailActivity.class);
                if (data.getState() == 0){
                    intent.putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,3);
                    intent.putExtra(AskForLeaveDetailActivity.HISTORY_MODEL,data);
                    startActivityForResult(intent,REQUEST_MANAGER_CODE);
                }else{
                    intent.putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,2);
                    AskForLeaveHistoryModel historyModel = getAskForHisModel(data);
                    intent.putExtra(AskForLeaveDetailActivity.HISTORY_MODEL,historyModel);
                    startActivity(intent);
                }

            }
        });
        presenter.selectApprovalList();
    }

    private AskForLeaveHistoryModel getAskForHisModel(AskForLeaveModel data) {
        AskForLeaveHistoryModel historyModel = new AskForLeaveHistoryModel();
        historyModel.setLeaveTime(data.getLeaveTime());
        historyModel.setCreateTime(data.getCreateTime());
        historyModel.setType(data.getType());
        historyModel.setEndTime(data.getEndTime());
        historyModel.setLeaveId(data.getLeaveId());
        historyModel.setLeaveReason(data.getLeaveReason());
        historyModel.setStartTime(data.getStartTime());
        historyModel.setTeacherId(data.getTeacherId());
        historyModel.setState(data.getState());
        return historyModel;
    }


    public void showFrame(FrameLayout frameLayout,int child){
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            if (i == child){
                frameLayout.getChildAt(child).setVisibility(View.VISIBLE);
            }else{
                frameLayout.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MANAGER_CODE && resultCode == RESULT_MANAGER_CODE){
            isNeedClear = true;
            presenter.selectApprovalList();
        }
    }

    @Override
    public void showData(Object o) {
        ToastUtils.showNormalToast(bContext,"操作成功。");
        isNeedClear = true;
        presenter.selectApprovalList();
    }

    @Override
    public void showListData(List list) {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isNeedClear){
            isNeedClear = false;
            modelList.clear();
        }
        if (list != null && list.size() > 0){
            textViewNull.setVisibility(View.GONE);
            modelList.addAll(list);
        }else {
            textViewNull.setVisibility(View.VISIBLE);
        }
        baseAdapter.notifyDataSetChanged();
    }
}
