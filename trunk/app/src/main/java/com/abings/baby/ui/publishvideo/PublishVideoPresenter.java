package com.abings.baby.ui.publishvideo;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.uploadpic.UploadPicUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static android.R.attr.path;

/**
 * 小视频发布
 */
public class PublishVideoPresenter extends BasePresenter<PublishVideoMvpView> {

    private final DataManager mDataManager;
    String videoName;

    @Inject
    public PublishVideoPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }


    public void videoUpload(final String content, final String firstFrameImgPath, final String videoPath, final String isPublic) {
        final String userId = ZSApp.getInstance().getUserId();
        final String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        bMvpView.showProgress(true);
//        第一帧上传
        Observable.just(firstFrameImgPath)
                .flatMap(new Func1<String, Observable<BaseModel<JSONObject>>>() {
                    @Override
                    public Observable<BaseModel<JSONObject>> call(String s) {
                        try {
                            String imageName = UploadPicUtils.upLoadFirstFrameImg(ZSApp.getInstance(), firstFrameImgPath);
                            videoName = UploadPicUtils.upLoadSmallVideo(ZSApp.getInstance(), videoPath);
//                    return dataManager.uploadNewTimeCardImg(schoolId, finalBabyId, finalUserId, finalQrcode, eventType, imageName);
                            String firtFrameImgUrl = Const.URL_VideoFirstFrame;
                            String videoUrl = Const.URL_Video;
                            return mDataManager.insertVideoWithPublic(userId, babyId, content, imageName, videoName,isPublic);
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
                        mDataManager.downloadSmallVideo(videoName) .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On()).subscribe(new Subscriber<BaseModel<JSONObject>>() {
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
    }

    public void videoUploadBak(String content, String firtFrameImgPath, final String videoPath) {
        String userId = ZSApp.getInstance().getUserId();
        String babyId = ZSApp.getInstance().getBabyId();

        resetSubscription();
        bMvpView.showProgress(true);
//        第一帧上传
        mDataManager.videoFirstFrameImg(userId, babyId, content, firtFrameImgPath)
                .flatMap(new Func1Class<AlbumModel, BaseModel<AlbumModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<AlbumModel>> callSuccess(AlbumModel albumModel) {
                        //视频上传
                        return mDataManager.videoUpload(albumModel.getVideoId(), albumModel.getVideoName(), videoPath);
                    }
                })
                .flatMap(new Func1Class<AlbumModel, AlbumModel>(bMvpView) {
                    @Override
                    protected Observable<AlbumModel> callSuccess(AlbumModel albumModel) {
                        //这里返回的data为空，解析就是空
                        return Observable.just(new AlbumModel());
                    }
                })
                .compose(RxThread.<AlbumModel>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<AlbumModel>(bMvpView) {
                    @Override
                    public void onNext(AlbumModel albumModel) {
                        bMvpView.showMsg("上传完成");
                        bMvpView.publishSuccess();
                    }
                });
    }
}
