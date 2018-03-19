package com.abings.baby.teacher.ui.attendanceManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.AskForLeaveHistoryModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AskForLeaveHistoryActivity extends BaseLibTitleActivity<List<AskForLeaveHistoryModel>>{

    @Inject
    AskForLeavePresenter presenter;

    @BindView(R.id.ask_for_leave_history_swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ask_for_leave_history_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.text_null_ts)
    TextView textViewNull;

    private List<AskForLeaveHistoryModel> models;
    private BaseAdapter<AskForLeaveHistoryModel> baseAdapter;
    private boolean isNeedClear = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ask_for_leave_history;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        setTitleText("请假历史");
        bTitleBaseRL.setBackgroundResource(R.color.white);
        models = new ArrayList<>();

        baseAdapter = new BaseAdapter<AskForLeaveHistoryModel>(bContext,models,false) {
            @Override
            protected void convert(ViewHolder holder, AskForLeaveHistoryModel data) {
                ((TextView)holder.getView(R.id.ask_for_leave_create_time)).setText(DateUtil.getFormatTimeFromTimestamp(data.getCreateTime(),"yyyy.MM.dd"));
                String leaveTime = data.getLeaveTime() > 0 ? DateUtil.getFormatTimeFromTimestamp(data.getLeaveTime(),"yyyy.MM.dd"):"";
                ((TextView)holder.getView(R.id.ask_for_leave_result_time)).setText(leaveTime);
                ((TextView)holder.getView(R.id.ask_for_holiday_start)).setText(" ("+DateUtil.getFormatTimeFromTimestamp(data.getStartTime(),"yyyy.MM.dd HH:mm")+" - ");
                ((TextView)holder.getView(R.id.ask_for_holiday_end)).setText(DateUtil.getFormatTimeFromTimestamp(data.getEndTime(),"yyyy.MM.dd HH:mm")+")");
                String type = data.getType() == 1 ? "病假": "事假";
                ((TextView)holder.getView(R.id.ask_for_leave_type)).setText(type);
                TextView resultState = holder.getView(R.id.ask_for_his_result);
                int textColor = data.getState() == 0 ? getResources().getColor(R.color.black) : data.getState() == 1 ? getResources().getColor(R.color.video_green) : getResources().getColor(R.color.text_red);
                String state = data.getState() == 0 ? "待处理" : data.getState() == 1 ? "已批准" : "已拒绝";
                LinearLayout ask_for_history =  holder.getView(R.id.ask_for_history);
                int bgColor = data.getState() == 0 ? R.color.white : R.color.normal_background;
                ask_for_history.setBackgroundResource(bgColor);
                resultState.setText(state);
                resultState.setTextColor(textColor);
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_ask_for_leave_history;
            }
        };
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<AskForLeaveHistoryModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, AskForLeaveHistoryModel data, int position) {
                Intent intent = new Intent(bContext,AskForLeaveDetailActivity.class);
                intent.putExtra(AskForLeaveDetailActivity.IS_WAIT_DETAIL,2);
                intent.putExtra(AskForLeaveDetailActivity.HISTORY_MODEL,data);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(baseAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNeedClear = true;
                presenter.selectLeave();
            }
        });

        showProgress(true);
        presenter.selectLeave();
    }

    @Override
    public void showData(List<AskForLeaveHistoryModel> o) {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (isNeedClear){
            isNeedClear = false;
            models.clear();
        }
        showProgress(false);
        if (o != null && o.size() > 0){
            models.addAll(o);
            textViewNull.setVisibility(View.GONE);
        }else{
            textViewNull.setVisibility(View.VISIBLE);
        }
        baseAdapter.notifyDataSetChanged();
    }
}
