package com.hellobaby.library.ui.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.MessageItem;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知界面
 */
public abstract class BaseEventListActivity extends BaseLibTitleActivity {

    RecyclerView recyclerView;

    protected List<EventModel> mDatas;

    protected RecyclerViewAdapterEventList adapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_eventlist;
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        recyclerView=(RecyclerView)findViewById(R.id.alert_activity_recyclerView) ;
        setTitleText("活动中心");
        setBtnLeftClickFinish();
        mDatas=new ArrayList<EventModel>();
        MessageItem data=new MessageItem();
        MessageItem data2=new MessageItem();
        iniAdapter();
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterEventList(this,mDatas,false);
            recyclerView.setAdapter(adapter);
        }
    }
}
