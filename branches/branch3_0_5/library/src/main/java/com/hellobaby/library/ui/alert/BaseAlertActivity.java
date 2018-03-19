package com.hellobaby.library.ui.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.MessageItem;
import com.hellobaby.library.data.model.SystemMessageModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知界面
 */
public class BaseAlertActivity extends BaseLibTitleActivity {

    public RecyclerView recyclerView;

    public List<SystemMessageModel> mDatas;

    public RecyclerViewAdapterAlertList adapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_alert;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        recyclerView=(RecyclerView)findViewById(R.id.alert_activity_recyclerView) ;
        setTitleText("系统消息");
        setBtnLeftClickFinish();
        iniAdapter();
    }

    @Override
    protected void btnRightOnClick(View v) {

    }

    @Override
    public void showData(Object o) {
    }

    public void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterAlertList(this,mDatas,false);
            adapter.setOnItemClickListener(new OnItemClickListeners<SystemMessageModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, SystemMessageModel data, int position) {
                    Intent intent=new Intent(BaseAlertActivity.this,AlertDetailActivity.class);
                    intent.putExtra("SystemMessageModel",data);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
