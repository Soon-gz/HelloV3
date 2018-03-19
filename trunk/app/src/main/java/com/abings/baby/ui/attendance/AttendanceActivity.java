package com.abings.baby.ui.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.babyCard.BabyCardActivity;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

public class AttendanceActivity extends BaseTitleActivity implements AttendanceMvpView {
    @Inject
    AttendancePresenter presenter;
    RecyclerView rv;
    TextView tv;
    protected List<TimeCardModel> mDatas=new ArrayList<TimeCardModel>();;
    protected RecyclerViewAdapterAttendanceIndexList adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendance;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        setBtnRightDrawableRes(R.drawable.select_date_black);
        if(null==ZSApp.getInstance().getBabyModel().getRelation()||null==ZSApp.getInstance().getLoginUser().getUserName()||ZSApp.getInstance().getLoginUser().getUserName().trim().equals("")||ZSApp.getInstance().getBabyModel().getRelation().trim().equals("")){
            startActivityForResult(new Intent(this,WriteNessaryActivity.class),Const.NORMAL_ACTIVITY_RESULT);
        }
        rv = (RecyclerView) findViewById(R.id.activity_attendance_rv);
        tv = (TextView) findViewById(R.id.activity_attendance_tv);
        iniAdapter();
        presenter.selectTodayAttendance();
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterAttendanceIndexList(this, mDatas, false);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListeners<TimeCardModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, TimeCardModel data, int position) {
                    Intent intent = new Intent(bContext, AttendanceDetailActivity.class);
                    intent.putExtra("TimeCardModel", data);
                    startActivity(intent);
                }
            });
        }
    }

    @OnClick({R.id.iv_dai, R.id.iv_jia, R.id.iv_ka})
    public void onClick(View v) {
        if (v.getId() == R.id.iv_dai) {
            startActivityForResult(new Intent(this, ReplaceActivity.class), Const.NORMAL_ACTIVITY_RESULT);
        } else if (v.getId() == R.id.iv_jia) {
            startActivityForResult(new Intent(this, LeaveActivity.class), Const.NORMAL_ACTIVITY_RESULT);
        } else if (v.getId() == R.id.iv_ka) {
            startActivity(new Intent(this, BabyCardActivity.class));
        }
    }

    @Override
    public void showData(Object jsonObject) {

    }

    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        startActivityForResult(new Intent(this, AttendanceHistoryListActivity.class), Const.NORMAL_ACTIVITY_RESULT);
    }


    @Override
    public void showJsonObject(JSONObject jsonObject) {
        if (jsonObject.getString("dataType").equals("0.0")) {
            rv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            tv.setText("今天还没有考勤记录");
        }else if(jsonObject.getString("dataType").equals("1.0")){
            rv.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            tv.setText("今天已请假");
        }else if(jsonObject.getString("dataType").equals("2.0")){
            rv.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            mDatas=JSONArray.parseArray(jsonObject.getJSONArray("timeCards").toJSONString(),TimeCardModel.class);
            adapter.setNewData(mDatas);
        }
    }

    @Override
    public void showJsonArray(JSONArray jsonArr) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Const.NORMAL_ACTIVITY_RESULT){
            if(null==ZSApp.getInstance().getBabyModel().getRelation()||null==ZSApp.getInstance().getLoginUser().getUserName()){
                finish();
            }
        }
        if(requestCode==Const.NORMAL_ACTIVITY_RESULT){
            presenter.selectTodayAttendance();
        }
    }
}
