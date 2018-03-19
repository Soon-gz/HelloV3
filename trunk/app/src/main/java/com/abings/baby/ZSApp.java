package com.abings.baby;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.abings.baby.data.injection.component.ApplicationComponent;
import com.abings.baby.data.injection.component.DaggerApplicationComponent;
import com.abings.baby.data.injection.module.ApplicationModule;
import com.abings.baby.ui.main.MainActivity;
import com.alipay.euler.andfix.patch.PatchManager;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.BusEvent;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.JPushModel;
import com.hellobaby.library.data.model.ServerCarebabyCache;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.LogZS;
import com.squareup.otto.Subscribe;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class ZSApp extends Application {
    private static ZSApp instance;
    ApplicationComponent mApplicationComponent;
    private static BabyModel babyModel;
    private PatchManager patchManager;

    public TAlertBooleanModel gettAlertBooleanModel() {
        if(tAlertBooleanModel!=null){
            return tAlertBooleanModel;
        }else {
            TAlertBooleanModel t=new TAlertBooleanModel();
            t.setMsg(1);
            t.setSchool(1);
            t.setSysmsg(1);
            t.setEvaluation(1);
            t.setAttendance(1);
            t.setTeaching(1);
            return t;
        }
    }

    public PatchManager getPatchManager() {
        return patchManager;
    }

    public void settAlertBooleanModel(TAlertBooleanModel tAlertBooleanModel) {
        ZSApp.tAlertBooleanModel = tAlertBooleanModel;
    }

    private static TAlertBooleanModel tAlertBooleanModel;
    private List<BabyModel> listBaby;
    private static UserModel loginUser;
    private static String userId = null;
    private boolean isDebug;
    private List<ServerCarebabyCache> serverCarebabyCaches;
    private static boolean isCarebaby = false;

    public static boolean isAtMainActivity = false;

    public static boolean isAgreeClicked = false;
    private static AppVersionModel appVersionModel;

    public boolean isCarebaby() {
        return isCarebaby;
    }

    public void setCarebaby(boolean carebaby) {
        isCarebaby = carebaby;
    }

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
        //推送
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        //调试开启打印
        if (BuildConfig.DEBUG){
            LogZS.i("--------------------开启调试功能---------------");
            LogZS.isLog = true;
        }else{
            LogZS.isLog = false;
        }
        //初始化 Andfix
        patchManager = new PatchManager(this);
        patchManager.init(BuildConfig.VERSION_NAME);
        patchManager.loadPatch();

        //初始化友盟在线参数
        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
        OnlineConfigAgent.getInstance().setDebugMode(true);

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
        if (babyModel != null) {
            babyModel.getBirthday();
        }
        return babyModel;
    }

    public void setBabyModel(BabyModel babyModel) {
        this.babyModel = babyModel;
    }

    /**
     * 所有宝宝列表
     *
     * @return
     */
    public List<BabyModel> getListBaby() {
        return listBaby;
    }

    /**
     * 其他宝宝列表，剔除了当前登录的宝宝
     *
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
        if (loginUser == null) {
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
        if (loginUser == null) {
            userId = null;
        }
        this.loginUser = loginUser;
    }


    public void initUmeng() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx0cad0007408825e5", "b25335558c8f6fb614b4f88a91f42ffe");
        //新浪微博
        PlatformConfig.setSinaWeibo("3194081559", "597896b2ff61d772aced5b0215bfffad", "http://sns.whalecloud.com/sina2/callback");
        PlatformConfig.setQQZone("1105390578", "2zyvkzmHAOOffri5");
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, "581bef5475ca35494c00079f","Umeng"));
//        PlatformConfig.setSinaWeibo("1955662730", "c6fc43671134e690c977d7ca66bbe574");
//        PlatformConfig.setQQZone("1105394095", "L8NzDrSHZg83QTxw");
//        PlatformConfig.setWeixin("wx77a4a7dbcd04602e", "ae2d7d5d75d6ade3ac7d02ac31c76137");
    }

    public List<ServerCarebabyCache> getServerCarebabyCaches() {
        return serverCarebabyCaches;
    }

    public void setServerCarebabyCaches(List<ServerCarebabyCache> serverCarebabyCaches) {
        this.serverCarebabyCaches = serverCarebabyCaches;
    }

    private JPushModel jPushModel;

    public JPushModel getjPushModel() {
        return jPushModel;
    }

    public void setjPushModel(JPushModel jPushModel) {
        this.jPushModel = jPushModel;
    }

    public AppVersionModel getAppVersionModel() {
        return appVersionModel;
    }

    public void setAppVersionModel(AppVersionModel appVersionModel) {
        ZSApp.appVersionModel = appVersionModel;
    }
}
