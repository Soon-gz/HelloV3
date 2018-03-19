package com.abings.baby.teacher.ui.login;

import android.util.Log;

import com.abings.baby.teacher.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void selectSchoolByTeacherId() {
        bMvpView.showProgress(true);
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
        bMvpView.showProgress(true);
        mDataManager.checkTeacherLogin(phone, pwd)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        DataManager.token = s;
                        resetSubscription();
                        bMvpView.showProgress(true);
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
                                        for (ClassModel clazz : list) {
                                            LogZS.d("selectClassesByTeacherId->" + clazz.toString());
                                        }
                                        ZSApp.getInstance().setClassModelList(list);//class
                                        bMvpView.toMain();
                                    }
                                });
                    }
                });

    }

    public void emptyLogin() {
        ClassModel classModel = new ClassModel();
        List<ClassModel> list = new ArrayList<>();
        list.add(classModel);
        ZSApp.getInstance().setClassModelList(list);
        ZSApp.getInstance().setSchoolModel(new SchoolModel());
        ZSApp.getInstance().setTeacherModel(new TeacherModel());
        bMvpView.toMain();
    }
}
