package com.abings.baby.ui.carebaby;

import android.util.Log;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/2/23.
 */

public class CareBabyPresenter extends BasePresenter<CareBabyMvpView> {
    private final DataManager dataManager;

    @Inject
    public CareBabyPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 同意关注自己的宝宝
     */
    public void insertCareBaby(String userId, final String babyId) {
        resetSubscription();
        Log.i("tag00", "同意" + userId + "关注" + babyId + "请求");
        dataManager.insertCareBaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showData(babyId);
                    }
                });
    }
    public void insertCareBabyWithList(String userId, final String babyId, final boolean isFinal) {
        resetSubscription();
        Log.i("tag00", "同意" + userId + "关注" + babyId + "请求");
        dataManager.insertCareBaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        if (!isFinal){
                            bMvpView.showData(babyId);
                        }else {
                            bMvpView.finalyRequest();
                        }
                    }
                });
    }

    /**
     * 拒绝他人关注自己的宝宝
     */
    public void disAgreeCareBaby(String userId, final String babyId) {
        resetSubscription();
        Log.i("tag00", "拒绝" + userId + "关注" + babyId + "请求");
        dataManager.disagreeCarebaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showData(babyId);
                    }
                });
    }
    public void disAgreeCareBabyWithList(String userId, final String babyId, final boolean isFinal) {
        resetSubscription();
        Log.i("tag00", "拒绝" + userId + "关注" + babyId + "请求");
        dataManager.disagreeCarebaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        if (!isFinal){
                            bMvpView.showData(babyId);
                        }else {
                            bMvpView.finalyRequest();
                        }
                    }
                });
    }
}
