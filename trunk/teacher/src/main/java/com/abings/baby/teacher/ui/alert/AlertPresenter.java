package com.abings.baby.teacher.ui.alert;

import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.msg.MsgCenterMvp;
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

    public void getSysmsgByTeacherId() {
        mDataManager.getSysmsgByTeacherId(ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }
    public void setSysmsgReadWithTeacherId(String messageId) {
        mDataManager.setSysmsgReadWithTeacherId(messageId,ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }
}
