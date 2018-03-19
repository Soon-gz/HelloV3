package com.abings.baby.teacher.ui.event;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.text.ParseException;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class EventListPresenter extends BasePresenter<EventListMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public EventListPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void selecteventsByteacher(){
        String teacherid = ZSApp.getInstance().getTeacherModel().getTeacherId();
        dataManager.selecteventsByteacher(teacherid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(),EventModel.class));
                    }
                });
    }
    public void selectEventOnTeacher(String type){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacher(teacherid,type,schoolid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showEventData(JSONArray.parseArray(jsonObject.toJSONString(),EventModel.class));
                    }
                });
    }
    public void selectSchoolOnTeacher(String type){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        dataManager.selectEventOnTeacher(teacherid,type,schoolid)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject){
                        try {
                            bMvpView.showListData(jsonObject);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
