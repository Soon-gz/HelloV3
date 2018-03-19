package com.abings.baby.teacher.ui.login;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.jpush.ExampleUtil;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.model.TeacherTokenModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.injection.ActivityContext;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Observable;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;
    private static final String TAG = "tag00";
    private Context context;


    @Inject
    public LoginPresenter(DataManager dataManager, @ActivityContext Context context) {
        this.context = context;
        mDataManager = dataManager;
    }

    public void selectSchoolByTeacherId() {
//        bMvpView.showProgress(true);
        mDataManager.selectTeacherByToken()
                .flatMap(new Func1Class<TeacherModel, BaseModel<SchoolModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<SchoolModel>> callSuccess(TeacherModel teacherModel) {
                        ZSApp.getInstance().setTeacherModel(teacherModel);
                        return mDataManager.selectSchoolByTeacherId(teacherModel.getTeacherId());
                    }
                })
                .flatMap(new Func1Class<SchoolModel, BaseModel<List<ClassModel>>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<List<ClassModel>>> callSuccess(SchoolModel schoolModel) {
                        ZSApp.getInstance().setSchoolModel(schoolModel);
                        Log.i("ZLog", "selectSchoolByTeacherId-->" + schoolModel.toString());
                        return mDataManager.selectClassesByTeacherId(ZSApp.getInstance().getTeacherId());
                    }
                })
                .compose(RxThread.<BaseModel<List<ClassModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<ClassModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<ClassModel> list) {
                        ZSApp.getInstance().setClassModelList(list);
                        bMvpView.toMain();
                    }
                });
    }

    public void loginTeacher(String phone, String pwd) {
        resetSubscription();
//        bMvpView.showProgress(true);
        mDataManager.checkTeacherLoginRole(phone, pwd)
                .compose(RxThread.<BaseModel<TeacherTokenModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<TeacherTokenModel>(bMvpView) {
                    @Override
                    protected void callSuccess(TeacherTokenModel model) {
//                        缓存全局token
                        DataManager.token = model.getToken();
//                        DataManager.token = model;

//                        缓存教师端角色
                        ZSApp.getInstance().setRole(model.getRole());
                        resetSubscription();
//                        bMvpView.showProgress(true);
                        mDataManager.checkTeacherToken()
                                .flatMap(new Func1Class<String, BaseModel<TeacherModel>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<TeacherModel>> callSuccess(String teacherId) {
                                        return mDataManager.selectTeacherById(teacherId);
                                    }
                                })
                                .flatMap(new Func1Class<TeacherModel, BaseModel<SchoolModel>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<SchoolModel>> callSuccess(TeacherModel teacherModel) {
                                        LogZS.d("selectTeacherById->" + teacherModel.toString());
                                        ZSApp.getInstance().setTeacherModel(teacherModel);//存储teacher
                                        //userId 作为bugly显示的id
                                        CrashReport.setUserId(teacherModel.getTeacherName());
                                        return mDataManager.selectSchoolByTeacherId(teacherModel.getTeacherId());
                                    }
                                })
                                .flatMap(new Func1Class<SchoolModel, BaseModel<List<ClassModel>>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<List<ClassModel>>> callSuccess(SchoolModel schoolModel) {
                                        LogZS.d("selectSchoolByTeacherId->" + schoolModel.toString());
                                        ZSApp.getInstance().setSchoolModel(schoolModel);//存储school
                                        return mDataManager.selectClassesByTeacherId(ZSApp.getInstance().getTeacherId());
                                    }
                                })
                                .compose(RxThread.<BaseModel<List<ClassModel>>>subscribe_Io_Observe_On())
                                .subscribe(new SubscriberClass<List<ClassModel>>(bMvpView) {
                                    @Override
                                    protected void callSuccess(List<ClassModel> list) {
                                        ZSApp.getInstance().setClassModelList(list);//class
                                        bMvpView.toMain();
                                    }
                                });
                    }
                });

    }

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(context, (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    public void setAlias(){
        //拼接Alias
        String alias = "T_" + (String) SharedPreferencesUtils.getParam(context, Const.keyPhoneNum, "");
        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    /**
     * 设置Alias回调方法
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.isConnected(context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };

    public void emptyLogin() {
        ClassModel classModel = new ClassModel();
        List<ClassModel> list = new ArrayList<>();
        list.add(classModel);
        ZSApp.getInstance().setClassModelList(list);
        ZSApp.getInstance().setSchoolModel(new SchoolModel());
        ZSApp.getInstance().setTeacherModel(new TeacherModel());
        bMvpView.toMain();
    }

    public void appVersionGet() {
        mDataManager.appVersionGetTeacher()
                .compose(RxThread.<BaseModel<AppVersionModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AppVersionModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AppVersionModel model) {
                        ZSApp.getInstance().setAppVersionModel(model);
                        bMvpView.toUpdate(model);
                    }
                });
    }
}
