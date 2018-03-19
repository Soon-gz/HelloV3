package com.hellobaby.timecard;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.timecard.data.injection.component.ApplicationComponent;
import com.hellobaby.timecard.data.injection.component.DaggerApplicationComponent;
import com.hellobaby.timecard.data.injection.module.ApplicationModule;
import com.tencent.bugly.crashreport.CrashReport;


/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class ZSApp extends Application {
    private static ZSApp instance;
    ApplicationComponent mApplicationComponent;
    private SchoolModel schoolModel;//校园模型
    private boolean isDebug;
    private boolean isChangeUUID = false;

    public boolean isChangeUUID() {
        return isChangeUUID;
    }

    public void setChangeUUID(boolean changeUUID) {
        isChangeUUID = changeUUID;
    }

    /**
     *   0到校  ，1离校
     */
    private String eventType = "0";
    /**
     * 1 正常 2 迟到  3 早退  (遗弃，限制一天只有两次)
     * 1：到校  2：离校  （修改之后，一天可多次打卡）
     *
     */
    private String teacherWorkAttendence = "1";

    private static AppVersionModel appVersionModel;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
        instance = this;
        isDebug = AppUtils.isApkInDebug(this);
//        initOther();
        //TengXun Bugly
        initinitBugly();
//        schoolModel = new SchoolModel();
//        teacherModel = new TeacherModel();
//        classModelList = new ArrayList<>();
//        classModelList.add(new ClassModel());
        //推送
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush
    }


    public static AppVersionModel getAppVersionModel() {
        return appVersionModel;
    }

    public static void setAppVersionModel(AppVersionModel appVersionModel) {
        ZSApp.appVersionModel = appVersionModel;
    }

    private void initinitBugly() {
        /**
         * 上下文，注册时申请的APPID，是否为调试模式
         */
        CrashReport.initCrashReport(getApplicationContext(), Const.BUGLY_APPID_TIMECARD, isDebug);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    /**
//     * 扩展其它的
//     */
//    protected void initOther() {
//        initUmeng();
//    }

    public static ZSApp getInstance() {
        return instance;
    }

    public SchoolModel getSchoolModel() {
        if(schoolModel==null){
            SchoolModel schoolModel = new SchoolModel();
            schoolModel.setSchoolId(-1);
            return schoolModel;
        }
        return schoolModel;
    }

    public void setSchoolModel(SchoolModel schoolModel) {
        this.schoolModel = schoolModel;
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

//    @Subscribe
//    public void serviceError(BusEvent.ServiceError serviceError) {
//        //可以接收来自拦截器中的错误信息
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public void setEventTypeJie() {
        this.eventType = "1";
    }
    public void setEventTypeSong() {
        this.eventType = "0";
    }

    public String getTeacherWorkAttendence() {
        return teacherWorkAttendence;
    }

    public void setTeacherWorkAttendence(String teacherWorkAttendence) {
        this.teacherWorkAttendence = teacherWorkAttendence;
    }
}
