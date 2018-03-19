package com.abings.baby.teacher.ui.calendar;

import com.abings.baby.teacher.ui.changephone.ChangePhoneMvpView;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;


public class CalendarPresenter extends BasePresenter<CalendarMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public CalendarPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void selectSchedule(String year,String month){
        resetSubscription();
        dataManager.selectSchedule(year,month).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On()).subscribe(new SubscriberClass<JSONArray>(bMvpView) {
            @Override
            protected void callSuccess(JSONArray jsonArray) {
                bMvpView.addEvent(jsonArray);
            }
        });
    }
    public void selectScheduleDetailsByDay(String time) {
        resetSubscription();
        dataManager.selectScheduleDetailsByDay(time).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On()).subscribe(new SubscriberClass<JSONArray>(bMvpView) {
            @Override
            protected void callSuccess(JSONArray jsonArray) {
                bMvpView.addEventItem(jsonArray);
            }
        });
    }
}
