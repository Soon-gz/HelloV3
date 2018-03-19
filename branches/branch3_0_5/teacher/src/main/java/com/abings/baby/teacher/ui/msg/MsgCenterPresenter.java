package com.abings.baby.teacher.ui.msg;

import com.abings.baby.teacher.ZSApp;
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

public class MsgCenterPresenter extends BasePresenter<MsgCenterMvp> {
    private final DataManager mDataManager;

    @Inject
    public MsgCenterPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    //    2.18接口功能：家长端，获取收件箱列表(tMessage/getReceiveMessageListByUserId)
    public void getReceiveMessageListByTeacherId() {
        mDataManager.getReceiveMessageListByTeacherId(ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }

    public void getSendMessageListByTeacherId() {
        mDataManager.getSendMessageListByTeacherId(ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }
    public void sendMessagetoClass(String classIds,String title,String content) {
        mDataManager.sendMessagetoClass(ZSApp.getInstance().getTeacherId(),classIds,title,content).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsgSendSuccess();
                    }
                });
    }
    public void setMessageReadWithTeacherId(String messageId) {
        mDataManager.setMessageReadWithTeacherId(messageId,ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsgSendSuccess();
                    }
                });
    }
    //    2.18接口功能：家长端，获取收件箱列表(tMessage/getReceiveMessageListByUserId)
    public void getSendUnReadMessageByTeacherId(String messageId) {
        mDataManager.getSendUnReadMessageByTeacherId(ZSApp.getInstance().getTeacherId(),messageId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }
}
