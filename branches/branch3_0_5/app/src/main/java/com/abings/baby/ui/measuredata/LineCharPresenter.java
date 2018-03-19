package com.abings.baby.ui.measuredata;

import com.abings.baby.ui.login.create.CreateBabyMvpView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.MeasureModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/12/30.
 */

public class LineCharPresenter extends BasePresenter<LineCharMvpView> {
    @Inject
    DataManager dataManager;
    @Inject
    public LineCharPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
//2.12接口功能：家长端，体重身高最新数据获取(height/selectLatestHeight)
    public void selectLatestHeight(String babyId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectLatestHeight(babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.setLastData(jsonObject);
                    }
                });
    }
    //2.13接口功能：家长端，身高初始化(height/initHeight)
    public void initHeight(String userId,String babyId,String birthHeight,String nowHeight,String birthDate,String inputDate) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.initHeight(userId,babyId,birthHeight,nowHeight,birthDate,inputDate).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("记录成功");
                    }
                });
    }
    //2.23接口功能：家长端，身高添加(weight/insertHeight)
    public void insertHeight(String userId,String babyId,String nowHeight,String inputDate) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.insertHeight(userId,babyId,nowHeight,inputDate).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("记录成功");
                    }
                });
    }
    //2.14接口功能：家长端，身高 历史记录(height/selectHisHeight)
    public void selectHisHeight(String babyId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectHisHeight(babyId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray models) {
                        bMvpView.selectHisHeight(JSONArray.parseArray(models.toJSONString(),MeasureModel.class));
                    }
                });
    }
    // 2.15接口功能：家长端，身高 修改(height/updateHeightById)
    public void updateHeightById(String heightId,String nowHeight) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.updateHeightById(heightId,nowHeight).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("修改成功");
                    }
                });
    }
    //  2.16接口功能：家长端，身高 删除(height/deleteHeightById)
    public void deleteHeightById(String heightId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.deleteHeightById(heightId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }
    //2.24接口功能：家长端，体重初始化(weight/initWeight)
    public void initWeight(String userId,String babyId,String birthWeight,String nowWeight,String birthDate,String inputDate) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.initWeight(userId,babyId,birthWeight,nowWeight,birthDate,inputDate).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("记录成功");
                    }
                });
    }
    //2.25接口功能：家长端，体重添加(weight/insertWeight)
    public void insertWeight(String userId,String babyId,String nowWeight,String inputDate) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.insertWeight(userId,babyId,nowWeight,inputDate).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("记录成功");
                    }
                });
    }
    //2.26接口功能：家长端，体重 历史记录(weight/selectHisWeight)
    public void selectHisWeight(String babyId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectHisWeight(babyId).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray models) {
                        bMvpView.selectHisHeight(JSONArray.parseArray(models.toJSONString(),MeasureModel.class));
                    }
                });
    }
    // 2.27接口功能：家长端，体重 修改(weight/updateWeightById)
    public void updateWeightById(String weightId,String nowWeight) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.updateWeightById(weightId,nowWeight).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("修改成功");
                    }
                });
    }
    //   2.28接口功能：家长端，体重 删除(weight/deleteWeightById)
    public void deleteWeightById(String weightId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.deleteWeightById(weightId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }

}
