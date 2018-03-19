package com.abings.baby.teacher.ui.event;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.EventJoinModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.ui.event.BaseEventDetailActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

/**
 * 活动
 */
public class EventDetailActivity extends BaseEventDetailActivity implements EventDetailMvpView{
    @Inject
    EventDetailPresenter presenter;
    EventModel event;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    public void initData() {
        event = (EventModel) getIntent().getSerializableExtra("EventModel");
        presenter.attachView(this);
        presenter.selectEventObjectOnTeacher(event.getEventId()+"");
//        activityed_tv_title.setText(eventModel.getEventTitle().toString());
//        if (eventModel.getEventStartTime() == eventModel.getEventEndTime())
//            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日"));
//        else
//            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日") + "-" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventEndTime(), "yyyy年MM月dd日"));
//        activityed_tv_address.setText("活动地点: " + eventModel.getEventAddress().toString());
//        activityed_tv_fee.setText("活动费用: " + eventModel.getEventFee() + "/人");
//        activityed_tv_people.setText("活动人数: " + eventModel.getEventPeople() + "");
//        activityed_tv_static.setVisibility(View.VISIBLE);
//        activityed_tv_deadline.setText("报名截止日期:" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventDeadlineTime(), "yyyy年MM月dd日"));
//        activityed_tv_conp.setText("联系人: " + eventModel.getTeacherName() + "");
//        activityed_tv_detail.setText(eventModel.getEventDetails().toString());
//        eventdetail_bt_joindetail.setVisibility(View.VISIBLE);
//        if(eventModel.getCreatorId()==Integer.valueOf(ZSApp.getInstance().getTeacherId())){
//            eventdetail_bt_del.setVisibility(View.VISIBLE);
//        }
//        eventdetail_bt_joindetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(bContext,EventJoinDetailActivity.class);
//                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.selectEventObjectOnTeacher(event.getEventId()+"");
    }

    @Override
    public void showEvent(final EventModel eventModel) {
        activityed_tv_title.setText(eventModel.getEventTitle().toString());
        if (eventModel.getEventStartTime() == eventModel.getEventEndTime()){
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日"));
        }
        else {
            activityed_tv_time.setText("活动时间: " + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventStartTime(), "yyyy年MM月dd日") + "-" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventEndTime(), "yyyy年MM月dd日"));
        }
        activityed_tv_address.setText("活动地点: " + eventModel.getEventAddress().toString());
        activityed_tv_fee.setText("活动费用: " + eventModel.getEventFee() + "元/人");
        activityed_tv_kid.setText("活动宝宝人数: " + eventModel.getBabyNumber()+"/"+eventModel.getEventBaby() + "");
        activityed_tv_people.setText("活动家长人数: " + eventModel.getParentsNumber()+"/"+eventModel.getEventPeople() + "");
        activityed_tv_static.setVisibility(View.VISIBLE);
        activityed_tv_deadline.setText("报名截止日期:" + DateUtil.getFormatTimeFromTimestamp(eventModel.getEventDeadlineTime(), "yyyy年MM月dd日"));
        activityed_tv_conp.setText("联系人: " + eventModel.getTeacherName() + "");
        activityed_tv_detail.setText(eventModel.getEventDetails().toString());
        eventdetail_bt_joindetail.setVisibility(View.VISIBLE);
        if(eventModel.getContactorId()==Integer.valueOf(ZSApp.getInstance().getTeacherId())){
            eventdetail_bt_del.setVisibility(View.VISIBLE);
            eventdetail_bt_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] items = {"请确定是否删除活动", "是", "否"};
                    BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                            if (position == 1) {
                                presenter.deleteEventsByEventId(eventModel.getEventId()+"");
                                setResult(888);
                                finish();
                            }
                        }
                    });
                }
            });
        }
        eventdetail_bt_joindetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EventDetailActivity.this,EventJoinDetailActivity.class);
                intent.putExtra("eventId",eventModel.getEventId()+"");
                intent.putExtra("totalperson",eventModel.getBabyNumber()+eventModel.getParentsNumber()+"");
                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
            }
        });
    }

    @Override
    public void showJoinDetail(List<EventJoinModel> list) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
        EventBus.getDefault().unregister(this);
    }
}
