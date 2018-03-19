package com.abings.baby.teacher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.abings.baby.teacher.data.injection.component.ApplicationComponent;
import com.abings.baby.teacher.data.injection.component.DaggerApplicationComponent;
import com.abings.baby.teacher.data.injection.module.ApplicationModule;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.BusEvent;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.utils.AppUtils;
import com.squareup.otto.Subscribe;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;

import java.util.List;


/**
 * Created by zwj on 2016/9/27.
 * description :
 */

public class ZSApp extends Application {
    private static ZSApp instance;
    ApplicationComponent mApplicationComponent;
    private SchoolModel schoolModel;//校园模型
    private TeacherModel teacherModel;//教师的模型
    private List<ClassModel> classModelList;//班级列表
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
//        schoolModel = new SchoolModel();
//        teacherModel = new TeacherModel();
//        classModelList = new ArrayList<>();
//        classModelList.add(new ClassModel());
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
//        PlatformConfig.setWeixin("wx0cad0007408825e5", "b25335558c8f6fb614b4f88a91f42ffe");
//        //新浪微博
//        PlatformConfig.setSinaWeibo("3194081559", "597896b2ff61d772aced5b0215bfffad");
//        PlatformConfig.setQQZone("1105390578", "2zyvkzmHAOOffri5");
        PlatformConfig.setSinaWeibo("1955662730", "c6fc43671134e690c977d7ca66bbe574");
        PlatformConfig.setQQZone("1105394095", "L8NzDrSHZg83QTxw");
        PlatformConfig.setWeixin("wx77a4a7dbcd04602e", "ae2d7d5d75d6ade3ac7d02ac31c76137");
    }
}
