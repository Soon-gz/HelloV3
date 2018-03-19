package com.abings.baby.ui.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.TimeCardHisModel;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AttendanceHistoryListActivity extends BaseTitleActivity implements AttendanceMvpView{
    @Inject
    AttendancePresenter presenter;
    RecyclerView rv;
    protected List<TimeCardHisModel> mDatas= new ArrayList<TimeCardHisModel>();;
    protected RecyclerViewAdapterAttendanceList adapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendance_history;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        rv=(RecyclerView) findViewById(R.id.hisactivity_rv);
        iniAdapter();
        presenter.selectHistoryAttendance();
    }
    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterAttendanceList(this, mDatas, false);
            rv.setAdapter(adapter);
//            adapter.setOnItemClickListener(new OnItemClickListeners<TimeCardHisModel>() {
//                @Override
//                public void onItemClick(ViewHolder viewHolder, TimeCardHisModel data, int position) {
//                    Intent intent=new Intent(bContext,AttendanceDetailActivity.class);
//                    intent.putExtra("TimeCardModel",data);
//                    startActivity(intent);
//                }
//            });
        }
    }
    @Override
    public void showData(Object o) {

    }

    @Override
    public void showJsonObject(JSONObject jsonObject) {

    }

    @Override
    public void showJsonArray(JSONArray jsonArr) {
        mDatas=JSONArray.parseArray(jsonArr.toJSONString(), TimeCardHisModel.class);
        for(int i=0;i<mDatas.size();i++){
            if(mDatas.get(i).getDataType()==2)
            mDatas.get(i).setTimeCardModels(JSONArray.parseArray(jsonArr.getJSONObject(i).getJSONArray("timeCards").toJSONString(), TimeCardModel.class));
        }
        adapter.setNewData(mDatas);
        adapter.notifyDataSetChanged();
    }
}
