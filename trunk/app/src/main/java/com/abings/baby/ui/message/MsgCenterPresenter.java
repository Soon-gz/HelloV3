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

import static com.hellobaby.library.Const.THREAD_SLEEPTIME;

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
        bMvpView.showProgress(true);
        try {
            Thread.sleep(THREAD_SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataManager.getReceiveMessageListByUserId(ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        bMvpView.showMsgList(null);
                    }
                });
    }

    public void getSendMessageListByUserId(){
        bMvpView.showProgress(true);
        try {
            Thread.sleep(THREAD_SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataManager.getSendMessageListByUserId(ZSApp.getInstance().getUserId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        bMvpView.showMsgList(null);
                    }
                });
    }
    public void userSendMessageToTeacher(String receiveTeacherId,String title,String content) {
        bMvpView.showProgress(true);
        mDataManager.userSendMessageToTeacher(ZSApp.getInstance().getUserId(),receiveTeacherId,title,content).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("发布成功");
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

    //删除家长单条收件箱信息
    public void deleteReceiveMessageOfUser(String messageId){
        String subscriberId=ZSApp.getInstance().getUserId();
        mDataManager.deleteAllReceiveMessageOfUser(messageId,subscriberId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getReceiveMessageListByUserId();
                    }
                });
    }

    //删除家长单条发件箱信息
    public void deleteSendMessageOfUser(String messageId){
        mDataManager.deleteSendMessageOfUser(messageId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getSendMessageListByUserId();
                    }
                });
    }

    //批量删除家长收件箱
    public void deleteAllReceiveMessageOfUser(String messageIds){
        mDataManager.deleteAllReceiveMessageOfUser(messageIds,ZSApp.getInstance().getUserId())
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getReceiveMessageListByUserId();
                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                });
    }


    //批量删除家长发件箱
    public void deleteAllSendMessageOfUser(String messageIds){
        mDataManager.deleteAllSendMessageOfUser(messageIds)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getSendMessageListByUserId();
                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }



}
