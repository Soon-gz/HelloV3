package com.abings.baby.teacher.ui.alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.SystemMessageModel;
import com.hellobaby.library.ui.alert.AlertDetailActivity;
import com.hellobaby.library.ui.alert.BaseAlertActivity;
import com.hellobaby.library.ui.alert.RecyclerViewAdapterAlertList;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/9.
 */

public class AlertActivity extends BaseAlertActivity implements AlertMvp{
    @Inject
    AlertPresenter presenter;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((getActivityComponent()),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        presenter.getSysmsgByTeacherId();
    }

    @Override
    public void showMsgList(JSONArray jsonArray) {
        adapter.setNewData(JSONArray.parseArray(jsonArray.toJSONString(),SystemMessageModel.class));
    }
    public void iniAdapter() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterAlertList(this,mDatas,false);
            adapter.setOnItemClickListener(new OnItemClickListeners<SystemMessageModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, SystemMessageModel data, int position) {
                    if(data.getIsRead()!=1)
                        presenter.setSysmsgReadWithTeacherId(data.getSysmsgId()+"");
                    Intent intent=new Intent(AlertActivity.this,AlertDetailActivity.class);
                    intent.putExtra("SystemMessageModel",data);
                    startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
