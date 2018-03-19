package com.abings.baby.ui.main.fm.school;

import android.util.Log;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.alert.AlertMvp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

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
}
