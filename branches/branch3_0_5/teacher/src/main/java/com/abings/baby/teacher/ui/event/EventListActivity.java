package com.abings.baby.teacher.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


/**
 * 通知界面
 */
public class EventListActivity extends BaseTitleActivity implements EventListMvpView{
    @Inject
    EventListPresenter presenter;
    RecyclerView recyclerView;

    protected List<EventModel> mDatas;

    protected RecyclerViewAdapterEventList adapter;
    @Override
    protected int getContentViewLayoutID() {
        return com.hellobaby.library.R.layout.libactivity_eventlist;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        recyclerView=(RecyclerView)findViewById(com.hellobaby.library.R.id.alert_activity_recyclerView) ;
        setTitleText("活动中心");
        setBtnLeftClickFinish();
        mDatas=new ArrayList<EventModel>();
        iniAdapter();
        presenter.selecteventsByteacher();
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterEventList(this,mDatas,false);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showEventData(List<EventModel> list) {
//        mDatas=list;
        Collections.reverse(list);
        adapter.setNewData(list);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.selecteventsByteacher();
    }
}
