package com.abings.baby.teacher.ui.msg.fm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.event.EventListPresenter;
import com.abings.baby.teacher.ui.event.RecyclerViewAdapterEventList;
import com.abings.baby.teacher.ui.msg.MsgCenterMvp;
import com.abings.baby.teacher.ui.msg.MsgCenterPresenter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.UnreadModel;
import com.hellobaby.library.ui.contact.ActivityRecyclerIndexAdapter;
import com.hellobaby.library.widget.DividerItemDecoration;
import com.hellobaby.library.widget.IndexView;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/12.
 */

public class UnReadActivity extends BaseTitleActivity implements MsgCenterMvp{
    @Inject
    MsgCenterPresenter presenter;
    RecyclerView recyclerView;

    protected List<UnreadModel> mDatas;

    protected RecyclerViewAdapterUnreadList adapter;
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
        setTitleText("未读人列表");
        setBtnLeftClickFinish();
        mDatas=new ArrayList<UnreadModel>();
        iniAdapter();
        presenter.getSendUnReadMessageByTeacherId(getIntent().getStringExtra("messageId"));
    }

    private void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterUnreadList(this,mDatas,false);
            adapter.setOnItemClickListener(new OnItemClickListeners<UnreadModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, UnreadModel data, int position) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data1 = Uri.parse("tel:" +data.getPhoneNum());
                    intent.setData(data1);
                    bContext.startActivity(intent);
                    return;
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showMsgList(JSONArray jsonArray) {
        adapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(),UnreadModel.class));
    }

    @Override
    public void showMsgSendSuccess() {

    }
}
