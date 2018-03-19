package com.abings.baby.teacher.ui.msg.detail;

import com.abings.baby.teacher.ZSApp;
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

public class MsgDetailPresenter extends BasePresenter<MsgDetailMvp> {
    private final DataManager mDataManager;

    @Inject
    public MsgDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void deleteSendMessageOfTeacher(String messageId){
        mDataManager.deleteSendMessageOfTeacher(messageId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }

    public void deleteReceiveMessageOfTeacher(String messageId){
        String subscriberId= ZSApp.getInstance().getTeacherId();
        mDataManager.deleteReceiveMessageOfTeacher(messageId,subscriberId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }
}
