package com.hellobaby.timecard.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.data.model.AttendenceTeacherModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;
import com.hellobaby.library.utils.CustomAlertDialog;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.utils.NetworkUtil;
import com.hellobaby.timecard.ZSApp;
import com.hellobaby.timecard.data.model.TCUserModel;
import com.hellobaby.timecard.uiPortrait.DialogUUIDActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * 2016/01/01.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * @param IMEI 设备的串号
     */
    public void SchoolByMachineCode(String IMEI, final Context context) {
//        bMvpView.showProgress(true);
        resetSubscription();
        if (NetworkUtil.isNetworkConnected(context)){
            bSubscription = dataManager.SchoolByMachineCode(IMEI)
                    .compose(RxThread.<BaseModel<SchoolModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<SchoolModel>(bMvpView) {
                        @Override
                        protected void callSuccess(SchoolModel schoolModel) {
                            ZSApp.getInstance().setSchoolModel(schoolModel);
                            bMvpView.showSchoolModel(schoolModel);
                        }

                        @Override
                        protected void callError(BaseModel baseModel) {
                            dialogInputUUID();
                            bMvpView.showError("获取幼儿园信息失败，请输入机器码");
                        }

                        @Override
                        protected void callServerError(BaseModel baseModel) {
                            dialogInputUUID();
                            bMvpView.showError("获取幼儿园信息失败，请输入机器码");
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialogInputUUID();
                            bMvpView.showError("获取幼儿园信息失败，请输入机器码");
                        }

                    });
        }else {
            dialog(context);
        }

    }

    /**
     * 绑定机器码
     * @param uuid
     */
    public void updateState(String uuid){
        dataManager.updateState(uuid)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.updateStateSuccess();
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {

                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void dialogInputUUID(){
        bMvpView.inputUUIDClick("");
    }

    public void dialog(final Context mContext){
        CustomAlertDialog.dialogNetSet(mContext,"网络异常，请检查并设置网络状态。",new CustomAlertDialog.OnDialogClickListener() {
            @Override
            public void doSomeThings() {
                Intent intent = new Intent("/");
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(
                            android.provider.Settings.ACTION_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.Settings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                mContext.startActivity(intent);
            }

            @Override
            public void cancle() {

            }
        });
    }

    /**
     */
    public void babyAndPersonInfoByQrcode(String babyId, String userId, String qrcode,Context context) {
//        bMvpView.showProgress(true);
        if (NetworkUtil.isNetworkConnected(context)){
            babyId = (babyId == null ? "" : babyId);
            userId = (userId == null ? "" : userId);
            qrcode = (qrcode == null ? "" : qrcode);
            String schoolId = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
            resetSubscription();
            final String finalQrcode = qrcode;
            final String finalBabyId = babyId;
            int eventType = Integer.valueOf(ZSApp.getInstance().getEventType());
            final String finalUserId = userId;
            bSubscription = dataManager.babyAndPersonInfoByQrcode(schoolId, babyId, userId, qrcode, eventType)
                    .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                        @Override
                        protected void callSuccess(JSONObject jsonObject) {
                            BabyModel baby = jsonObject.getObject("baby", BabyModel.class);
                            TCUserModel user = jsonObject.getObject("user", TCUserModel.class);
                            user.setQrcode(finalQrcode);
                            bMvpView.showInfo(baby, user, finalBabyId, finalUserId);
                        }

                        @Override
                        protected void callError(BaseModel baseModel) {
                            super.callError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        protected void callServerError(BaseModel baseModel) {
                            super.callServerError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            bMvpView.onError("");
                        }
                    });
        }else {
            dialog(context);
        }

    }

    /**
     * 作者：ShuWen
     * 日期：2017/3/1  14:54
     * 描述：家长上传图片
     *
     * @return [返回类型说明]
     */
    public void uploadTimeCardImg(final String babyName, String babyId, String userId, String qrcode, final String path, Context context) {
//        bMvpView.showProgress(true);
        if (babyId == null && qrcode == null) {
            bMvpView.showError("没有选中内容");
            return;
        }
        if (NetworkUtil.isNetworkConnected(context)){
            babyId = (babyId == null ? "" : babyId);
            userId = (userId == null ? "" : userId);
            qrcode = (qrcode == null ? "" : qrcode);
            final String eventType = ZSApp.getInstance().getEventType();
            final String schoolId = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
            resetSubscription();
            final String finalBabyId = babyId;
            final String finalUserId = userId;
            final String finalQrcode = qrcode;
            Observable.just(path).flatMap(new Func1<String, Observable<BaseModel<String>>>() {
                @Override
                public Observable<BaseModel<String>> call(String s) {
                    try {
                        String imageName = UploadPicUtils.upLoadTimeCardImg(ZSApp.getInstance(), path);
                        return dataManager.uploadNewTimeCardImg(schoolId, finalBabyId, finalUserId, finalQrcode, eventType, imageName);
                    } catch (Exception e) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                bMvpView.showError("上传图片异常");
                                bMvpView.onError("");
                            }
                        });
                    }
                    return null;
                }
            }).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<String>(bMvpView) {
                        @Override
                        protected void callSuccess(String s) {
                            bMvpView.uploadHeadImgResult(false,babyName);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            bMvpView.onError("");
                        }

                        @Override
                        protected void callError(BaseModel baseModel) {
                            super.callError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        protected void callServerError(BaseModel baseModel) {
                            super.callServerError(baseModel);
                            bMvpView.onError("");
                        }
                    });
        }else {
            dialog(context);
        }


    }

    /**
     * 教师拍照 上传
     * @param teacherId
     * @param schoolId
     * @param qrcode
     * @param path
     */
    public void uploadTecherImage(final String name, String teacherId, String schoolId, String qrcode, final String path, Context context){
        if (schoolId == null && qrcode == null) {
            bMvpView.showError("没有选中内容");
            return;
        }
        if (NetworkUtil.isNetworkConnected(context)){
            teacherId = (teacherId == null ? "" : teacherId);
            schoolId = (schoolId == null ? "" : schoolId);
            qrcode = (qrcode == null ? "" : qrcode);
            final int eventType = Integer.valueOf(ZSApp.getInstance().getTeacherWorkAttendence());
            resetSubscription();
            final String finalSchoolId = schoolId;
            final String finalTeacherId = teacherId;
            final String finalQrcode = qrcode;
            Observable.just(path).flatMap(new Func1<String, Observable<BaseModel<String>>>() {
                @Override
                public Observable<BaseModel<String>> call(String s) {
                    try {
                        String imageName = UploadPicUtils.upLoadTimeCardImg(ZSApp.getInstance(), path);
                        Log.i("ZLog", "schoolId:"+finalSchoolId + " teacherId:"+finalTeacherId +" qrcode:"+finalQrcode +"  eventType:"+ eventType +" imageName:"+imageName);
//                        return dataManager.uploadTeacherCardImg(finalSchoolId, finalTeacherId, finalQrcode, imageName,eventType, System.currentTimeMillis());
                        return dataManager.uploadTeacherAttendance(finalSchoolId, finalTeacherId, finalQrcode, imageName,eventType, System.currentTimeMillis());
                    } catch (Exception e) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                bMvpView.showError("上传图片异常");
                            }
                        });
                    }
                    return null;
                }
            }).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<String>(bMvpView) {
                        @Override
                        protected void callSuccess(String s) {
                            bMvpView.uploadHeadImgResult(true,name);
                        }

                        @Override
                        protected void callError(BaseModel baseModel) {
                            super.callError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        protected void callServerError(BaseModel baseModel) {
                            super.callServerError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);

                        }
                    });
        }else {
            dialog(context);
        }

    }

    /**
     * 通过二维码扫描获取教师信息
     *
     * @param schoolId
     * @param teacherId
     * @param qrcode
     */
    public void teacherInfoByQrcode(String schoolId, String teacherId, String qrcode,Context context){
        if (NetworkUtil.isNetworkConnected(context)){
            schoolId = (schoolId == null ? "" : schoolId);
            teacherId = (teacherId == null ? "" : teacherId);
            qrcode = (qrcode == null ? "" : qrcode);
            resetSubscription();
            int eventType = Integer.valueOf(ZSApp.getInstance().getTeacherWorkAttendence());
            final String finalQrcode = qrcode;
            final String finalTeacherId = teacherId;
            final String finalSchoolId = schoolId;
//            Log.i("ZLog", "schoolId:"+schoolId + " teacherId:"+teacherId +" qrcode:"+qrcode +"  eventType:"+ eventType);
            dataManager.teacherInfoByQrcode(schoolId, teacherId, qrcode, eventType)
                    .compose(RxThread.<BaseModel<AttendenceTeacherModel>>subscribe_Io_Observe_On())
                    .subscribe(new SubscriberClass<AttendenceTeacherModel>(bMvpView) {
                        @Override
                        protected void callSuccess(AttendenceTeacherModel teacherModel) {
                            bMvpView.showTeacherInfo(teacherModel, finalQrcode, finalTeacherId, finalSchoolId);
                        }

                        @Override
                        protected void callError(BaseModel baseModel) {
                            super.callError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        protected void callServerError(BaseModel baseModel) {
                            super.callServerError(baseModel);
                            bMvpView.onError("");
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            bMvpView.onError("");
                        }
                    });
        }else {
            dialog(context);
        }

    }

    /**
     * 作者：ShuWen
     * 日期：2017/5/1  14:58
     * 描述：版本查询
     *
     * @return [返回类型说明]
     */
    public void appVersionGet() {
        dataManager.appVersionGetkaoqin()
                .compose(RxThread.<BaseModel<AppVersionModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AppVersionModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AppVersionModel model) {
                        ZSApp.getInstance().setAppVersionModel(model);
                        bMvpView.toUpdate(model);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showError("检测版本更新异常");
                        bMvpView.updateError();
                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {
                        bMvpView.showError("检测版本更新异常");
                        bMvpView.updateError();
                    }

                    @Override
                    public void onError(Throwable e) {
                        bMvpView.showError("检测版本更新异常");
                        bMvpView.updateError();
                    }
                });
    }

}
