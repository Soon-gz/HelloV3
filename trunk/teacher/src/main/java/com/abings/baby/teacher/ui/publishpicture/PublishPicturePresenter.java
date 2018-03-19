package com.abings.baby.teacher.ui.publishpicture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ClassModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ProgressDialogHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;


public class PublishPicturePresenter extends BasePresenter<PublishPictureMvpView> {

    private final DataManager dataManager;
    private int uploadImageCount = 0;
    ProgressDialog progressDialog;
    String videoName;

    @Inject
    public PublishPicturePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 创建动态-图片
     *
     * @param description
     * @param listClass
     * @param listImg
     */
    public void dynamicUploadImgs(final String description, List<ClassModel> listClass, final List<String> listImg, final Context bContent) {
        progressDialog = ProgressDialogHelper.getInstance().showUploadProgressDialog((Activity) bContent, "准备上传");
        progressDialog.show();
        final String teacherId = ZSApp.getInstance().getTeacherId();
        List<ClassModel> listSelectClass = new ArrayList<>();
        for (ClassModel classM : listClass) {
            if (classM.isSelected()) {
                listSelectClass.add(classM);
            }
        }
        int listSelectClassSize = listSelectClass.size();
        final String[] classIds = new String[listSelectClassSize];
        final List<String> imgNames = new ArrayList<String>();
        final int listImgSize = listImg.size();
        final String[] paths = new String[listImgSize];
        for (int i = 0; i < listSelectClassSize; i++) {
            classIds[i] = listSelectClass.get(i).getClassId();
        }
        for (int i = 0; i < listImgSize; i++) {
            paths[i] = listImg.get(i);
        }
//        dataManager.dynamicUploadFiles(teacherId, description, classIds, paths)
//                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberBase<BaseModel<String>>(bMvpView) {
//                    @Override
//                    public void onNext(BaseModel<String> stringBaseModel) {
//                        bMvpView.showMsg("发布成功");
//                        bMvpView.publishSuccess();
//                    }
//                });
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                for (String imagepath : paths) {
                    uploadImageCount++;
                    bMvpView.uploadProgress(uploadImageCount + "/" + listImgSize);
                    try {
                        imgNames.add(UploadPicUtils.upLoadPicDymic(bContent, imagepath));
                    } catch (ClientException e) {
                        bMvpView.showError("网络异常");
                        uploadImageCount--;
                        return null;
                    } catch (ServiceException e) {
                        bMvpView.showError("服务异常");
                        uploadImageCount--;
                        return null;
                    }
                }
                if (uploadImageCount >= listImgSize) {
                    dataManager.uploadDynamicFiles(teacherId, description, new Gson().toJson(classIds), imgNames.toString()).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                            .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                                @Override
                                protected void callSuccess(JSONObject s) {
                                    bMvpView.showMsg("发布成功");
                                    bMvpView.publishSuccess();
                                }
                            });
                }
                return null;
            }
        };
        asyncTask.execute();
    }

    public void insertRecord() {
        dataManager.insertIRecord("", Const.SCHOOL_PRAMSE)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i("发布动态，添加积分成功！");
                        bMvpView.insertRecord();
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.insertRecord();
                    }
                });
    }

    /**
     * 创建动态-小视频
     *
     * @param description
     * @param listClass
     * @param firstFrameImgPath
     * @param videoPath
     */
    public void dynamicUploadVideo(final String description, List<ClassModel> listClass, final String firstFrameImgPath, final String videoPath) {
        bMvpView.showProgress(true);
        final String teacherId = ZSApp.getInstance().getTeacherId();
        List<ClassModel> listSelectClass = new ArrayList<>();
        for (ClassModel classM : listClass) {
            if (classM.isSelected()) {
                listSelectClass.add(classM);
            }
        }
        int listSelectClassSize = listSelectClass.size();
        final String[] classIds = new String[listSelectClassSize];
        for (int i = 0; i < listSelectClassSize; i++) {
            classIds[i] = listSelectClass.get(i).getClassId();
        }
//        String[] paths = {firstFrameImgPath};

        Observable
                .just(firstFrameImgPath)
                .flatMap(new Func1<String, Observable<BaseModel<JSONObject>>>() {
                    @Override
                    public Observable<BaseModel<JSONObject>> call(String s) {
                        try {
                            String imageName = UploadPicUtils.upLoadDynamicFirstFrameImg(ZSApp.getInstance(), firstFrameImgPath);
                            videoName = UploadPicUtils.upLoadDynamicSmallVideo(ZSApp.getInstance(), videoPath);
//                    return dataManager.uploadNewTimeCardImg(schoolId, finalBabyId, finalUserId, finalQrcode, eventType, imageName);
                            String firtFrameImgUrl = Const.URL_VideoFirstFrame;
                            String videoUrl = Const.URL_Video;
                            return dataManager.insertDynamicFirstFrameAndVideo(teacherId, description, new Gson().toJson(classIds), imageName, videoName);
                        } catch (Exception e) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    bMvpView.showError("上传小视频异常");
                                }
                            });
                        }
                        return null;
                    }
                })
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<JSONObject>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<JSONObject> jsonObjectBaseModel) {
                        SystemClock.sleep(100);
                        dataManager.downloadDynamicSmallVideo(videoName).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On()).subscribe(new Subscriber<BaseModel<JSONObject>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
e.printStackTrace();
                            }

                            @Override
                            public void onNext(BaseModel<JSONObject> jsonObjectBaseModel) {

                            }
                        });
                        bMvpView.showMsg("上传完成");
                        bMvpView.publishSuccess();
                    }
                });
//
//        //第一帧
//        dataManager
//                .dynamicUploadFirstFrame(teacherId, description, classIds, paths)
//                .flatMap(new Func1Class<JSONObject, BaseModel<JSONObject>>(bMvpView) {
//                    @Override
//                    protected Observable<BaseModel<JSONObject>> callSuccess(JSONObject jsonObject) {
//                        String videoName = jsonObject.getString("videoNames");
//                        String videoId = jsonObject.getString("videoIds");
//                        String[] videoPaths = {videoPath};
//                        //小视频
//                        return dataManager.dynamicUploadSmallVideo(videoId, videoName, videoPaths);
//                    }
//                })
//                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberBase<BaseModel<JSONObject>>(bMvpView) {
//                    @Override
//                    public void onNext(BaseModel<JSONObject> jsonObjectBaseModel) {
//                        bMvpView.showMsg("发布成功");
//                        bMvpView.publishSuccess();
//                    }
//                });

    }
}
