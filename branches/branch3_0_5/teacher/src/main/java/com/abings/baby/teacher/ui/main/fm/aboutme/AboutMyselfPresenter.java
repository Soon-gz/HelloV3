package com.abings.baby.teacher.ui.main.fm.aboutme;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * 2016/01/01.
 */

public class AboutMyselfPresenter extends BasePresenter<AboutMyselfMvpView> {

    private final DataManager dataManager;

    @Inject
    public AboutMyselfPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void tChangeHeadImgById(String path) {
        String teacherId = ZSApp.getInstance().getTeacherModel().getTeacherId();
        dataManager.tChangeHeadImgById(teacherId, path)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String headName) {
                        ZSApp.getInstance().getTeacherModel().setHeadImageurl(headName);
                    }
                });
    }

    //updateTeacherInfo
    public void updateTeacherInfo(TeacherModel tTeacher) {
        dataManager.updateTeacherInfo(new Gson().toJson(tTeacher))
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }

    //是否公开个人信息
    public void teacherChangePublic(String isPublic) {
        String teacherId = ZSApp.getInstance().getTeacherModel().getTeacherId();
        dataManager.teacherChangePublic(teacherId, isPublic)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
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
}
