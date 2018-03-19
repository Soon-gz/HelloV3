package com.abings.baby.teacher.ui.event;


import android.view.View;
import android.widget.TextView;

import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.event.BaseEventDetailActivity;
import com.hellobaby.library.utils.DateUtil;

/**
 * 活动
 */
public class EventDetailActivity extends BaseEventDetailActivity {
    @Override
    protected void initDaggerInject() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void initData() {
        EventModel eventModel = (EventModel) getIntent().getSerializableExtra("EventModel");
        activityed_tv_title.setText(eventModel.getEventTitle().toString());
        if (eventModel.getEventStartTime() == eventModel.getEventEndTime())
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日"));
        else
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日") + "-" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventEndTime(), "yyyy年MM月dd日"));
        activityed_tv_address.setText("活动地点: " + eventModel.getEventAddress().toString());
        activityed_tv_fee.setText("活动费用: " + eventModel.getEventFee() + "/人");
        activityed_tv_people.setText("活动人数: " + eventModel.getEventPeople() + "");
        activityed_tv_static.setVisibility(View.VISIBLE);
        activityed_tv_deadline.setText("报名截止日期:" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventDeadlineTime(), "yyyy年MM月dd日"));
        activityed_tv_conp.setText("联系人: " + eventModel.getTeacherName() + "");
        activityed_tv_detail.setText(eventModel.getEventDetails().toString());
    }

}
