package com.abings.baby.ui.alert;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/6.
 */

public class AlertPresenter extends BasePresenter<AlertMvp> {
    private final DataManager mDataManager;

    @Inject
    public AlertPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getSysmsgByUserId() {
        mDataManager.getSysmsgByUserId(ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }
    public void setSysmsgReadWithUserId(String messageId) {
        mDataManager.setSysmsgReadWithUserId(messageId, ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }
}
