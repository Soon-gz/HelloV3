package com.abings.baby.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.event.EventListMvpView;
import com.abings.baby.ui.event.EventListPresenter;
import com.abings.baby.ui.event.RecyclerViewAdapterEventList;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


/**
 * 活动界面
 */
public class EventListActivity extends BaseTitleActivity implements EventListMvpView {
    @Inject
    EventListPresenter presenter;
    RecyclerView recyclerView;

    protected List<EventModel> mDatas;
    private boolean isReturn = false;

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
        recyclerView = (RecyclerView) findViewById(com.hellobaby.library.R.id.alert_activity_recyclerView);
        setTitleText("活动中心");
        setBtnLeftClickFinish();
        mDatas = new ArrayList<EventModel>();
        iniAdapter();
        presenter.selectEventIsJoin();
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterEventList(this, mDatas, false);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showError(String err) {
        if ("宝宝没有参加活动".equals(err) && isReturn) {
            mDatas.clear();
            adapter.notifyDataSetChanged();
        } else {
            super.showError(err);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showEventData(List<EventModel> list) {
        Collections.reverse(list);
        adapter.setNewData(list);
    }

    @Override
    public void showEventDetail(EventModel eventModel, String isjoin) {

    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.selectEventIsJoin();
        isReturn = true;
    }
}
