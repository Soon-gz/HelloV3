package com.abings.baby.teacher.ui.event;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.EventJoinModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;


public class EventDetailPresenter extends BasePresenter<EventDetailMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public EventDetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    //查看活动报名
    public void selectEventNumList(String eventId){
        dataManager.selectEventNumList(eventId)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showJoinDetail(JSONArray.parseArray(jsonObject.toJSONString(), EventJoinModel.class));
                    }
                });
    }
    //  8.2接口功能：教师端，获取活动详情
    public void selectEventObjectOnTeacher(String eventid) {
        bMvpView.showProgress(true);
        String teacherId=ZSApp.getInstance().getTeacherId();
        dataManager.selectEventObjectOnTeacher(teacherId, eventid).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        EventModel eventModel = JSONObject.parseObject(jsonObject.toJSONString(),EventModel.class);
                        bMvpView.showEvent(eventModel);
                    }
                });
    }
    //删除活动
    public void deleteEventsByEventId(String eventId){
        dataManager.deleteEventsByEventId(eventId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }
}
