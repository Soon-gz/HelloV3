package com.abings.baby;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.abings.baby.data.injection.component.ApplicationComponent;
import com.abings.baby.data.injection.component.DaggerApplicationComponent;
import com.abings.baby.data.injection.module.ApplicationModule;
import com.abings.baby.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.BusEvent;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.utils.AppUtils;
import com.squareup.otto.Subscribe;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class ZSApp extends Application {
    private static ZSApp instance;
    ApplicationComponent mApplicationComponent;
    private BabyModel babyModel;
    private List<BabyModel> listBaby;
    private UserModel loginUser;
    private String userId = null;
    private boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
        instance = this;
        isDebug = AppUtils.isApkInDebug(this);
        initOther();
        //TengXun Bugly
        initinitBugly();
    }

    private void initinitBugly() {
        /**
         * 上下文，注册时申请的APPID，是否为调试模式
         */
        CrashReport.initCrashReport(getApplicationContext(), Const.BUGLY_APPID_BABY, isDebug);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 扩展其它的
     */
    protected void initOther() {
        initUmeng();
    }

    public static ZSApp getInstance() {
        return instance;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Subscribe
    public void serviceError(BusEvent.ServiceError serviceError) {
        //可以接收来自拦截器中的错误信息
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public BabyModel getBabyModel() {
        return babyModel;
    }

    public void setBabyModel(BabyModel babyModel) {
        this.babyModel = babyModel;
    }

    /**
     * 所有宝宝列表
     * @return
     */
    public List<BabyModel> getListBaby() {
        return listBaby;
    }

    /**
     * 其他宝宝列表，剔除了当前登录的宝宝
     * @return
     */
    public List<BabyModel> getOtherListBaby() {
        List<BabyModel> list = new ArrayList<>();
        list.addAll(listBaby);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBabyId() == babyModel.getBabyId()) {
                //去掉重复的
                list.remove(i);
            }
        }
        return list;
    }

    public void setListBaby(List<BabyModel> listBaby) {
        this.listBaby = listBaby;
    }

    public String getUserId() {
        if(loginUser==null){
            //看登录
            return userId;
        }
        return String.valueOf(loginUser.getUserId());
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBabyId() {
//        return String.valueOf(1);
        return String.valueOf(babyModel.getBabyId());
    }

    public String getClassId() {
//        return "1";
        return String.valueOf(babyModel.getClassId());
    }

    public String getSchoolId() {
//        return "1";
        return String.valueOf(babyModel.getSchoolId());
    }

    public String getBirthday() {
//        return "2015-11-11";
        return babyModel.getBirthday();
    }

    public UserModel getLoginUser() {
//        UserModel userModel=new UserModel();
//        userModel.setUserId(1);
        return loginUser;
    }
//
//    /**
//     * 登录用户的主联系人
//     * @return
//     */
//    public boolean isBabyMaster(){
////        return loginUser.getUserId()==babyModel.getMasterId();
//        return babyModel.isCreator();
//    }

    public void setLoginUser(UserModel loginUser) {
        this.loginUser = loginUser;
    }



    public void initUmeng() {
        PlatformConfig.setWeixin("wx0cad0007408825e5", "b25335558c8f6fb614b4f88a91f42ffe");
        //新浪微博
        PlatformConfig.setSinaWeibo("3194081559", "597896b2ff61d772aced5b0215bfffad");
        PlatformConfig.setQQZone("1105390578", "2zyvkzmHAOOffri5");
//        PlatformConfig.setSinaWeibo("1955662730", "c6fc43671134e690c977d7ca66bbe574");
//        PlatformConfig.setQQZone("1105394095", "L8NzDrSHZg83QTxw");
//        PlatformConfig.setWeixin("wx77a4a7dbcd04602e", "ae2d7d5d75d6ade3ac7d02ac31c76137");
    }
}
