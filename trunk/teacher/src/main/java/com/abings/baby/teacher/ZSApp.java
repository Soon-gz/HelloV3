package com.abings.baby.teacher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.abings.baby.teacher.data.injection.component.ApplicationComponent;
import com.abings.baby.teacher.data.injection.component.DaggerApplicationComponent;
import com.abings.baby.teacher.data.injection.module.ApplicationModule;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.alipay.euler.andfix.patch.PatchManager;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.BusEvent;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.LogZS;
import com.squareup.otto.Subscribe;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class ZSApp extends Application {
    private static ZSApp instance;
    ApplicationComponent mApplicationComponent;
    private SchoolModel schoolModel;//校园模型
    private TeacherAlertBooleanModel teacherAlertBooleanModel;//消息未读的模型
    private TeacherModel teacherModel;//教师的模型
    private List<ClassModel> classModelList;//班级列表
    private boolean isDebug;
    private static AppVersionModel appVersionModel;
    private int role;//用于缓存教师端用户角色  1：公司    2：学校   3：老师

    private PatchManager patchManager;


    public TeacherAlertBooleanModel getTeacherAlertBooleanModel() {
        if(teacherAlertBooleanModel!=null){
            return teacherAlertBooleanModel;
        }else {
            TeacherAlertBooleanModel t=new TeacherAlertBooleanModel();
            t.setMsg(1);
            t.setSchool(1);
            t.setSysmsg(1);
            return t;
        }

    }

    public void setTeacherAlertBooleanModel(TeacherAlertBooleanModel teacherAlertBooleanModel) {
        this.teacherAlertBooleanModel = teacherAlertBooleanModel;
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
//        schoolModel = new SchoolModel();
//        teacherModel = new TeacherModel();
//        classModelList = new ArrayList<>();
//        classModelList.add(new ClassModel());
        //推送
        JPushInterface.setDebugMode(isDebug); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        //调试开启打印
        LogZS.isLog = isDebug;
        //初始化 Andfix
        patchManager = new PatchManager(this);
        patchManager.init(BuildConfig.VERSION_NAME);
        patchManager.loadPatch();

        //初始化友盟在线参数
        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
        OnlineConfigAgent.getInstance().setDebugMode(isDebug);


    }

    private void initinitBugly() {
        /**
         * 上下文，注册时申请的APPID，是否为调试模式
         */
        CrashReport.initCrashReport(getApplicationContext(), Const.BUGLY_APPID_TEACHER, isDebug);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    是否是校长
    public boolean isSchoolMaster() {
        return getRole() == 2;
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

    public SchoolModel getSchoolModel() {
        return schoolModel;
    }

    public void setSchoolModel(SchoolModel schoolModel) {
        this.schoolModel = schoolModel;
    }

    public List<ClassModel> getClassModelList() {
        return classModelList;
    }

    public void setClassModelList(List<ClassModel> classModelList) {
        this.classModelList = classModelList;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public TeacherModel getTeacherModel() {
        return teacherModel;
    }

    public void setTeacherModel(TeacherModel teacherModel) {
        this.teacherModel = teacherModel;
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
    public String getTeacherId() {
        return teacherModel.getTeacherId();
    }

    public String getSchoolId() {
        return String.valueOf(schoolModel.getSchoolId());
    }

    public void initUmeng() {
        UMShareAPI.get(this);
//        PlatformConfig.setWeixin("wx0cad0007408825e5", "b25335558c8f6fb614b4f88a91f42ffe");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("3194081559", "597896b2ff61d772aced5b0215bfffad");
//        PlatformConfig.setQQZone("1105390578", "2zyvkzmHAOOffri5");
        PlatformConfig.setSinaWeibo("1955662730", "c6fc43671134e690c977d7ca66bbe574","http://sns.whalecloud.com/sina2/callback");
//        Config.RE = "http://sns.whalecloud.com/sina2/callback";
        PlatformConfig.setQQZone("1105394095", "L8NzDrSHZg83QTxw");
        PlatformConfig.setWeixin("wx77a4a7dbcd04602e", "ae2d7d5d75d6ade3ac7d02ac31c76137");
    }

    public AppVersionModel getAppVersionModel() {
        return appVersionModel;
    }

    public void setAppVersionModel(AppVersionModel appVersionModel) {
        ZSApp.appVersionModel = appVersionModel;
    }

    public PatchManager getPatchManager() {
        return patchManager;
    }
}
