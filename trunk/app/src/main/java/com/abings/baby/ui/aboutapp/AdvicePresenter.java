package com.abings.baby.ui.aboutapp;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AdviceModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.ui.base.MvpView;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/2/14.
 */

public class AdvicePresenter extends BasePresenter<MvpView> {

    private final DataManager mDataManager;

    @Inject
    public AdvicePresenter(DataManager mDataManager){
        this.mDataManager = mDataManager;
    }

    public void postAdvices(String content,String phoneNum,String email,String device){
        if (content == null || phoneNum == null || email == null){
            return;
        }

        AdviceModel adviceModel = new AdviceModel(content, phoneNum, email, ZSApp.getInstance().getUserId(), Const.CLIENT_USER, device);

        mDataManager.postAdvice(adviceModel)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showData(s);
                    }
                });
    }

}
