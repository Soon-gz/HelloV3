package com.abings.baby.ui.event;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class EventListPresenter extends BasePresenter<EventListMvpView> {
    private final DataManager dataManager;

    private List<Contact> contacts = new ArrayList<>();


    @Inject
    public EventListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    //    //-------------------------------------1.3-------------------------
//    //  5.15获得家长端 宝宝参加的活动列表   /tevet/selectEventIsJoin
//    public Observable<BaseModel<JSONArray>> selectEventIsJoin(String babyid) {
//        return mAPIService.selectEventIsJoin(token,babyid);
//    }
//
//    //   3.2家长端获取活动详情     /tevet/selectEventObject
//    public Observable<BaseModel<JSONArray>> selectEventObject(String babyid,String eventid) {
//        return mAPIService.selectEventObject(token, babyid,eventid);
//    }
//    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
//    public Observable<BaseModel<JSONArray>> isNotJoinEvent(String babyid,String eventid) {
//        return mAPIService.isNotJoinEvent(token, babyid,eventid,"40");
//    }
//    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
//    public Observable<BaseModel<JSONArray>> selectEventByClassToOneBaby(String classid,String babyid,String schoolid) {
//        return mAPIService.selectEventByClassToOneBaby(token,classid,babyid,schoolid,"40");
//    }
//  5.15获得家长端 宝宝参加的活动列表
    public void selectEventIsJoin() {
        String babyId=ZSApp.getInstance().getBabyId();
        dataManager.selectEventIsJoin(babyId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(), EventModel.class));
                    }
                });
    }

    //   3.2家长端获取活动详情/tevet/selectEventObject
    public void selectEventObject(String eventid) {
        bMvpView.showProgress(true);
        String babyId=ZSApp.getInstance().getBabyId();
        dataManager.selectEventObject(babyId, eventid).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        EventModel eventModel = jsonObject.getObject("tevent", EventModel.class);
                        bMvpView.showEventDetail(eventModel, jsonObject.getString("isjoin"));
//                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(),EventModel.class));
                    }
                });
    }

    // 3.3家长端 参加\取消参加  /tevet/isNotJoinEvent
    public void isNotJoinEvent( String eventid, String type) {
        String babyId=ZSApp.getInstance().getBabyId();
        dataManager.isNotJoinEvent(babyId, eventid, type).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(null);
                    }
                });
    }

    // 3.1获取baby所在班级的所有活动   /tevet/selectEventByClassToOneBaby
    public void selectEventByClassToOneBaby(String type) {
        String babyId=ZSApp.getInstance().getBabyId();
        String classId=ZSApp.getInstance().getClassId();
        String SchoolId=ZSApp.getInstance().getSchoolId();
        if(SchoolId.equals("0"))
            return ;
        dataManager.selectEventByClassToOneBaby(classId, babyId, SchoolId, type)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(), EventModel.class));
                    }
                });
    }

    // 3.1获取baby所在班级的校园信息 通过type区分   /tevet/selectEventByClassToOneBaby
    public void selectSchooltByClassToOneBaby(String type) {
        String classid = String.valueOf(ZSApp.getInstance().getBabyModel().getClassId());
        String babyid = String.valueOf(ZSApp.getInstance().getBabyModel().getBabyId());
        String SchoolId = String.valueOf(ZSApp.getInstance().getBabyModel().getSchoolId());
        if(SchoolId.equals("0"))
            return ;
        dataManager.selectEventByClassToOneBaby(classid, babyid, SchoolId, type)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showListData(jsonObject);
                    }
                });
    }
}
