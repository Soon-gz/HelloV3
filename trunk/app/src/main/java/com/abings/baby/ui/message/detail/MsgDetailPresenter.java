package com.abings.baby.ui.message.detail;

import com.abings.baby.ZSApp;
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

    public void deleteSendMessageOfUser(String messageId){
        mDataManager.deleteSendMessageOfUser(messageId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }

    public void deleteReceiveMessageOfUser(String messageId){
        String subscriberId=ZSApp.getInstance().getUserId();
        mDataManager.deleteReceiveMessageOfUser(messageId,subscriberId).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }
}
