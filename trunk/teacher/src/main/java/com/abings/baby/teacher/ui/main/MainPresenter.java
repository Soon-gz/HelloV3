package com.abings.baby.teacher.ui.main;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.data.model.TeacherAlertModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.StringUtils;

import javax.inject.Inject;

import rx.Subscriber;


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

    public void logout(){
        resetSubscription();
        dataManager.teacherLogout()
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.logoutSuccess();
                    }
                });
    }
    //15.4  更新TeacherAlert  系统消息、收发件箱、校园
    public void updateTeacherAlert(TeacherAlertModel alertModel){
        resetSubscription();
        alertModel.setTeacherId(Integer.parseInt(ZSApp.getInstance().getTeacherId()));
        String alertStr=new Gson().toJson(alertModel);
        dataManager.updateTeacherAlert(alertStr)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject s) {
                        return;
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
    //15.5 教师端获取提醒信息
    public void selectTeacherAlert(){
        resetSubscription();
        String teacherId=ZSApp.getInstance().getTeacherId();
        String schoolId=ZSApp.getInstance().getSchoolId();
        dataManager.selectTeacherAlert(teacherId,schoolId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject s) {
                     ZSApp.getInstance().setTeacherAlertBooleanModel(JSONObject.toJavaObject(s,TeacherAlertBooleanModel.class));
                     bMvpView.changedot(JSONObject.toJavaObject(s,TeacherAlertBooleanModel.class));
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                    }
                });
    }

    public void insertEquipmentTeacher(String appVersion) {
        String userId = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String phoneNum = ZSApp.getInstance().getTeacherModel().getPhoneNum();
        String equipmentVersion = AppUtils.getPhoneInfo();
        dataManager.insertEquipmentTeacher(userId, phoneNum, appVersion, equipmentVersion)
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

    /**
     * 作者：ShuWen
     * 日期：2017/12/11  16:27
     * 描述：下载修复文件
     *
     * @return [返回类型说明]
     */
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
