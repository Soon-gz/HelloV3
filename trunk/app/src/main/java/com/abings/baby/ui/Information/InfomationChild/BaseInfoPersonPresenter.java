package com.abings.baby.ui.Information.InfomationChild;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InfoPersonMsgModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/6.
 */

public class BaseInfoPersonPresenter extends BasePresenter<BaseInfoPersonMvp> {
    private final DataManager mDataManager;

    @Inject
    public BaseInfoPersonPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void addCarePerson(String fromState,String toState,String toUserId){
        mDataManager.addCarePerson(fromState,toState,toUserId)
                .compose(RxThread.<BaseModel<Integer>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<Integer>(bMvpView) {
                    @Override
                    protected void callSuccess(Integer jsonObject) {
                        bMvpView.addCarePersonSuccess();
                    }
                });
    }

    public void getUserInfoMsg(String userId,int pageNum,String state,String creatorId){
        mDataManager.getUserInfoMsg(userId,pageNum,state,creatorId)
                .compose(RxThread.<BaseModel<InfoPersonMsgModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<InfoPersonMsgModel>(bMvpView) {

                    @Override
                    protected void callSuccess(InfoPersonMsgModel infomationModels) {
                        bMvpView.showData(infomationModels);
                    }
                });
    }

    public void deleteCarePerson(String relationId){
        mDataManager.deleteCarePerson(relationId)
                .compose(RxThread.<BaseModel<Integer>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<Integer>(bMvpView) {
                    @Override
                    protected void callSuccess(Integer jsonObject) {
                        bMvpView.deleteCarepersonSuccess();
                    }
                });
    }

    public void addLikeInfo(String topicType ,String topicId){
        mDataManager.addLikeInfo(topicType, topicId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }

}
