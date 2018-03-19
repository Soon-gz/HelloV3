package com.abings.baby.ui.Information.infomationNew;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.CareOrCaredModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/6.
 */

public class BaseCareOrCarePresenter extends BasePresenter<BaseCareOrCareMvp>{
    private final DataManager mDataManager;

    @Inject
    public BaseCareOrCarePresenter(DataManager mDataManager) {
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

    public void getMyCaredPersons(){
        mDataManager.getMyCaredPersons()
                .compose(RxThread.<BaseModel<List<CareOrCaredModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<CareOrCaredModel>>(bMvpView) {

                    @Override
                    protected void callSuccess(List<CareOrCaredModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }
                });
    }

    public void getCaredMePersons(){
        mDataManager.getCaredMePersons()
                .compose(RxThread.<BaseModel<List<CareOrCaredModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<CareOrCaredModel>>(bMvpView) {

                    @Override
                    protected void callSuccess(List<CareOrCaredModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }
                });
    }
}
