package com.abings.baby.ui.main;

import android.util.Log;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ServerCarebabyCache;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void logout() {
        resetSubscription();
        dataManager.logout()
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.logoutSuccess();
                    }
                });
    }

    public void sendNotificationWithout(String userId) {
        dataManager.sendCareNoteWithUnNotified(userId)
                .compose(RxThread.<BaseModel<List<ServerCarebabyCache>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<ServerCarebabyCache>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<ServerCarebabyCache> list) {
                        bMvpView.showBottomDialog(list);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {

                    }
                });
    }

    public void getLoginInfo() {
        final String userId = String.valueOf(ZSApp.getInstance().getLoginUser().getUserId());
        dataManager.selectTUserbabyById(userId)
                .flatMap(new Func1Class<List<BabyModel>, BaseModel<UserModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<UserModel>> callSuccess(List<BabyModel> listBaseModel) {
                        if (listBaseModel.size() <= 0) {
//                            bMvpView.showMsg("对不起，您还没有宝宝");
                            return null;
                        } else {
//                            ZSApp.getInstance().setBabyModel(listBaseModel.get(0));
                            ZSApp.getInstance().setListBaby(listBaseModel);
                            return dataManager.selectUserExtendByUBId(userId, ZSApp.getInstance().getBabyId());
                        }
                    }
                })
                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                    @Override
                    protected void callSuccess(UserModel userModel) {

                        userModel.setSelected(true);//设置为选中
                        ZSApp.getInstance().setLoginUser(userModel);

                    }
                });
    }

    //   15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void updateAlert(TAlertModel tAlert) {
        bMvpView.showProgress(true);
        String userId = ZSApp.getInstance().getUserId();
        resetSubscription();
        tAlert.setUserId(Integer.valueOf(userId));
        tAlert.setBabyId(Integer.valueOf(ZSApp.getInstance().getBabyId()));
        String tAlertStr = new Gson().toJson(tAlert);
        dataManager.updateAlert(tAlertStr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
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

    //  15.3 获取Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void selectAlert() {
        bMvpView.showProgress(true);
        String userId = ZSApp.getInstance().getUserId();
        String classId = ZSApp.getInstance().getClassId();
        String schoolId = ZSApp.getInstance().getSchoolId();
        String babyId = ZSApp.getInstance().getBabyId();
//        Observable<TAlertBooleanModel> observable=Observable.zip();
        dataManager.selectAlert(userId, classId, schoolId, babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        final TAlertBooleanModel tAlertBooleanModel=JSONObject.parseObject(jsonObject.toJSONString(), TAlertBooleanModel.class);
                        dataManager.selectInfoIsUpdate().compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On()).subscribe(new SubscriberClass<String>(bMvpView) {
                            @Override
                            protected void callSuccess(String str) {
                                tAlertBooleanModel.setInfomsg(Integer.valueOf(str)>0?0:1);
                                ZSApp.getInstance().settAlertBooleanModel(tAlertBooleanModel);
                                bMvpView.showBadgeView(tAlertBooleanModel);
                            }
                        });
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

    public void insertEquipmentUser(String appVersion) {
        String userId = String.valueOf(ZSApp.getInstance().getLoginUser().getUserId());
        String phoneNum = ZSApp.getInstance().getLoginUser().getPhoneNum();
        String equipmentVersion = AppUtils.getPhoneInfo();
        dataManager.insertEquipmentUser(userId, phoneNum, appVersion, equipmentVersion)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i("提交设备信息成功");
                    }
                    @Override
                    protected void callError(BaseModel baseModel) {
                        LogZS.e("提交设备信息错误("+baseModel.getMsg()+")");
                    }
                });
    }


    public void downloadPatch(String url,String fileName){
        dataManager.downloadFile(url, fileName)//
                .compose(RxThread.<String>subscribe_Io_Observe_On())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //关闭当前的Dialog
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        LogZS.e("onError 热更新修复包，下载异常！");
                    }

                    @Override
                    public void onNext(String s) {
                        if (!StringUtils.isEmpty(s)){
                            bMvpView.download(s);
                        }else {
                            LogZS.e("onNext 热更新修复包，下载异常！");
                        }
                    }
                });
    }
}
