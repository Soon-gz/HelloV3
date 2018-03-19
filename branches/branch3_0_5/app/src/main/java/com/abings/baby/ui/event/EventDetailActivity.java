package com.abings.baby.ui.event;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.event.BaseEventDetailActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 活动
 */
public class EventDetailActivity extends BaseEventDetailActivity implements EventListMvpView {
    @Inject
    EventListPresenter presenter;
    public String isjoin_flag = "";

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        Button button = (Button) findViewById(R.id.eventdetail_bt_cancel);
        button.setVisibility(View.VISIBLE);
        final EventModel eventModel = (EventModel) getIntent().getSerializableExtra("EventModel");
        presenter.selectEventObject(eventModel.getEventId() + "");
        eventdetail_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isjoin_flag.equals("1")) {
                    String[] items = {"请确定是否取消活动", "是", "否"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 1) {
                                presenter.isNotJoinEvent(eventModel.getEventId() + "", isjoin_flag.equals("1") ? "0" : "1");
                            }
                        }
                    });
                } else {
                    presenter.isNotJoinEvent(eventModel.getEventId() + "", isjoin_flag.equals("1") ? "0" : "1");
                }
            }
        });
    }

    @Override
    public void showData(Object o) {
    }

    public void initData() {
    }

    @Override
    public void showEventData(List<EventModel> list) {
//        if (isjoin_flag.equals("1")) {
//            eventdetail_bt_cancel.setVisibility(View.VISIBLE);
//            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_default);
//            eventdetail_bt_cancel.setText("取消参加");
//        } else if(isjoin_flag.equals("2")){
//            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_join);
//            eventdetail_bt_cancel.setText("立即参加");
//        } else {
//            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_unable);
//            eventdetail_bt_cancel.setText("活动过期");
//        }
        activityed_tv_static.setVisibility(View.VISIBLE);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showEventDetail(EventModel eventModel, String isjoin) {
        activityed_tv_title.setText(eventModel.getEventTitle().toString());
        if (eventModel.getEventStartTime() == eventModel.getEventEndTime())
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日"));
        else
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日") + "-" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventEndTime(), "yyyy年MM月dd日"));
        activityed_tv_address.setText("活动地点: " + eventModel.getEventAddress().toString());
        activityed_tv_fee.setText("活动费用: " + eventModel.getEventFee() + "/人");
        activityed_tv_people.setText("活动人数: " + eventModel.getEventPeople() + "");
        activityed_tv_deadline.setText("报名截止日期: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventDeadlineTime(), "yyyy年MM月dd日"));
        activityed_tv_conp.setText("联系人: " + eventModel.getTeacherName() + "");
        activityed_tv_detail.setText(eventModel.getEventDetails().toString());
        activityed_tv_static.setVisibility(View.VISIBLE);
        isjoin_flag = isjoin;
        eventdetail_bt_cancel.setVisibility(View.VISIBLE);
        if (isjoin_flag.equals("1")) {
//            eventdetail_bt_cancel.setVisibility(View.VISIBLE);
            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_default);
            eventdetail_bt_cancel.setText("取消参加");
        }else if(isjoin_flag.equals("0")){
            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_join);
            eventdetail_bt_cancel.setText("立即参加");
        } else if(isjoin_flag.equals("3")){
            eventdetail_bt_cancel.setBackgroundResource(R.drawable.btn_bg_unable);
            eventdetail_bt_cancel.setText("活动过期");
            eventdetail_bt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMsg("活动已经过期");
                }
            });
        }
    }

    @Override
    public void showListData(JSONArray jsonArray) {

    }
}
