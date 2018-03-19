package com.abings.baby.teacher.ui.reviews;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.Contact;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class ReviewsPresenter extends BasePresenter<ReviewsMvpView> {
    @Inject
    DataManager dataManager;

    private List<Contact> contacts = new ArrayList<>();


    @Inject
    public ReviewsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    //    6.9接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单  /Tevaluation/selectTevaluationScore  ---
    public void selectTevaluationScore(String searchtime,String classId,String type) {
        dataManager.selectTevaluationScore(searchtime,classId,type).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showJSONArray(jsonObject);
                    }
                });
    }
    //    6.11接口功能：教师端-宝宝评语-获取某个班级当天的各项“评语”名单 /Tevaluation/selectTevaluationComments
    public void selectTevaluationComments(String searchtime,String classId) {
        dataManager.selectTevaluationComments(searchtime,classId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showJSONArray(jsonObject);
                    }
                });
    }
    //    6.10接口功能：教师端-宝宝评语-获取某个班级当天的各项评分名单 上传  /Tevaluation/insertTevaluationScore
    public void insertTevaluationScore(String searchtime,String type,String scores,String babyIds) {
        String createId= ZSApp.getInstance().getTeacherId();
        dataManager.insertTevaluationScore(searchtime,type,scores,babyIds,createId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("上传成功");
                        bMvpView.updateFinish();
                    }
                });
    }
    //    6.12接口功能：教师端-宝宝评语-获取某个班级当天的各项”评语”名单 上传
    public void insertTevaluationComments(String searchtime,String Comments) {
        dataManager.insertTevaluationComments(searchtime,Comments).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("上传成功");
                        bMvpView.updateFinish();
                    }
                });
    }
    //    6.8接口功能：教师端-宝宝评语-获取某个班级当天的评价完成度（服务器认为班级内某个宝宝有值就认为该项已经完成） ok！！！！！
    public void SelectTevaluationComplete(String searchtime,String classIds) {
        dataManager.SelectTevaluationComplete(searchtime,classIds).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showJSONObject(jsonObject);
                        bMvpView.updateFinish();
                    }
                });
    }

}
