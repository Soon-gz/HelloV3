package com.abings.baby.ui.message;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

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
    public void getReceiveMessageListByUserId() {
        mDataManager.getReceiveMessageListByUserId(ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }

    public void getSendMessageListByUserId() {
        mDataManager.getSendMessageListByUserId(ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }
    public void userSendMessageToTeacher(String receiveTeacherId,String title,String content) {
        mDataManager.userSendMessageToTeacher(ZSApp.getInstance().getUserId(),receiveTeacherId,title,content).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsgSendSuccess();
                    }
                });
    }
    public void setMessageReadWithUserId(String messageId) {
        mDataManager.setMessageReadWithUserId(messageId,ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsgSendSuccess();
                    }
                });
    }
    public void getTeacher(){
        mDataManager.selectTeacherphonesfrombaby(ZSApp.getInstance().getBabyId()).compose(RxThread.<BaseModel<List<TeacherModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeacherModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeacherModel> TeacherModel) {
                        bMvpView.selectTeacher(TeacherModel);
                    }
                });
    }
}
