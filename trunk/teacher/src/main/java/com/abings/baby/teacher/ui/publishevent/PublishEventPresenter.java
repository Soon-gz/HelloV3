package com.abings.baby.teacher.ui.publishevent;

import com.alibaba.fastjson.JSON;
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

public class PublishEventPresenter extends BasePresenter<PublishEventMvpView> {
    @Inject
    DataManager dataManager;

    private List<Contact> contacts = new ArrayList<>();


    @Inject
    public PublishEventPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void insertEvents(EventModel event,String classstr){
        dataManager.insertEvents(JSON.toJSONString(event),classstr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("发布完成");
                        bMvpView.publishSuccess();
                    }
                });
    }
}
