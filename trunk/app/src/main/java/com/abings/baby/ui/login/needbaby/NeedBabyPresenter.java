package com.abings.baby.ui.login.needbaby;

import android.content.Context;
import android.graphics.Bitmap;

import com.abings.baby.ZSApp;
import com.abings.baby.util.SharedPreferencesUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zwj on 2017-01-21.
 * description:
 */

public class NeedBabyPresenter extends BasePresenter<NeedBabyMvpView> {


    private final DataManager dataManager;

    @Inject
    public NeedBabyPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 关注与他人的宝宝
     *
     * @param babyId
     */
    public void insertCareBaby(final String babyId) {
        resetSubscription();
        final String userId = String.valueOf(ZSApp.getInstance().getUserId());
        dataManager.insertCareBaby(userId, babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.careBabySuccess(babyId);
                    }
                });
    }

    /**
     * 通知该宝宝家长，是否同意关注
     * @param babyId
     */
    public void notificationBabyParent(final String babyId, Context context){
        resetSubscription();
        String userId = String.valueOf(ZSApp.getInstance().getUserId());
        String userName;
        if (ZSApp.getInstance().getLoginUser() != null && ZSApp.getInstance().getLoginUser().getUserName() != null){
            userName = ZSApp.getInstance().getLoginUser().getUserName();
        }else {
            userName = (String) SharedPreferencesUtils.getParam(context, Const.keyPhoneNum,"");
        }
        dataManager.noficationBabyParent(userId,babyId,userName)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {

                    @Override
                    public void onError(Throwable e) {
                        bMvpView.showMsg("二维码无法识别！");
                    }

                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.careBabySuccess(babyId);
                    }

                });
    }
    public void insertInitAlert(String babyId){
        bMvpView.showProgress(true);
        resetSubscription();
        String userId = ZSApp.getInstance().getUserId();
        TAlertModel tAlertModel=new TAlertModel();
        tAlertModel.setUserId(Integer.valueOf(userId));
        tAlertModel.setBabyId(Integer.valueOf(babyId));
        String tAlertStr=new Gson().toJson(tAlertModel);
        dataManager.insertInitAlert(tAlertStr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject s) {
                        bMvpView.showMsg("请求关注成功，请等待对方同意。");
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showMsg("请求关注成功，请等待对方同意。");
                    }
                });
    }

}
