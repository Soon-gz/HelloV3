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
    public void getReceiveMessageListByTeacherId() {
        bMvpView.showProgress(true);
        try {
            Thread.sleep(THREAD_SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataManager.getReceiveMessageListByTeacherId(ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
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

    public void getSendMessageListByTeacherId() {
        bMvpView.showProgress(true);
        try {
            Thread.sleep(THREAD_SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataManager.getSendMessageListByTeacherId(ZSApp.getInstance().getTeacherId()).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
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
    public void sendMessagetoClass(String classIds,String title,String content) {
        bMvpView.showProgress(true);
        mDataManager.sendMessagetoClass(ZSApp.getInstance().getTeacherId(),classIds,title,content).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("发布成功");
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
        bMvpView.showProgress(true);
        try {
            Thread.sleep(THREAD_SLEEPTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDataManager.getSendUnReadMessageByTeacherId(ZSApp.getInstance().getTeacherId(),messageId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showMsgList(jsonObject);
                    }
                });
    }

    //删除老师单条收件箱记录
    public void deleteReceiveMessageOfTeacher(String messageId){
        String subscriberId= ZSApp.getInstance().getTeacherId();
        mDataManager.deleteReceiveMessageOfTeacher(messageId,subscriberId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getReceiveMessageListByTeacherId();
                    }
                });
    }

    //删除老师单条发件箱记录
    public void deleteSendMessageOfTeacher(String messageId){
        mDataManager.deleteSendMessageOfTeacher(messageId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getSendMessageListByTeacherId();
                    }
                });
    }

    //批量删除教师收件箱
    public void deleteAllReceiveMessageOfTeacher(String messageIds){
        mDataManager.deleteBatchReceiveMessageOfTeacher(messageIds,ZSApp.getInstance().getTeacherId())
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getReceiveMessageListByTeacherId();
                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    //批量删除教师发件箱
    public void deleteAllSendMessageOfTeacher(String messageIds){
        mDataManager.deleteBatchSendMessageOfTeacher(messageIds)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                        getSendMessageListByTeacherId();
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
