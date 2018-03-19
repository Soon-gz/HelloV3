package com.abings.baby.ui.main.fm.school;

import android.util.Log;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.alert.AlertMvp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/6.
 */

public class NoSchoolPresenter extends BasePresenter<NoSchoolMvp> {
    private final DataManager mDataManager;

    @Inject
    public NoSchoolPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void updateTBabyOnClassId() {
        resetSubscription();
        final String babyId = ZSApp.getInstance().getBabyId();
        String phoneNum = ZSApp.getInstance().getLoginUser().getPhoneNum();
        String babyName = ZSApp.getInstance().getBabyModel().getBabyName();
        mDataManager.updateTBabyOnClassId(babyId, phoneNum, babyName)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        final BabyModel babyModel=JSONObject.parseObject(jsonObject.toJSONString(),BabyModel.class);
                        mDataManager.setMessageTotalNum(babyModel.getClassId()+"")
                                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                                .subscribe(new SubscriberClass<String>(bMvpView) {
                                    @Override
                                    protected void callSuccess(String s) {
                                        BabyModel baby=ZSApp.getInstance().getBabyModel();
                                        baby.setSchoolId(babyModel.getSchoolId());
                                        baby.setClassId(babyModel.getClassId());
                                        baby.setSchoolName(babyModel.getSchoolName());
                                        baby.setKindergarten(babyModel.getSchoolName());
                                        ZSApp.getInstance().setBabyModel(baby);
                                        bMvpView.showMsg("关联幼儿园成功");
                                    }
                                });
                    }
                });
    }
    //   15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void updateAlert(TAlertModel tAlert) {
        bMvpView.showProgress(true);
        String userId= ZSApp.getInstance().getUserId();
        resetSubscription();
        tAlert.setUserId(Integer.valueOf(userId));
        tAlert.setBabyId(Integer.valueOf(ZSApp.getInstance().getBabyId()));
        String tAlertStr=new Gson().toJson(tAlert);
        mDataManager.updateAlert(tAlertStr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i("更新时间成功");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                    }
                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                    }
                });
    }
}
