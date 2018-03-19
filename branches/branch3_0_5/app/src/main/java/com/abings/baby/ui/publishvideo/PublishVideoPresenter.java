package com.abings.baby.ui.publishvideo;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;

/**
 * 小视频发布
 */
public class PublishVideoPresenter extends BasePresenter<PublishVideoMvpView> {

    private final DataManager mDataManager;

    @Inject
    public PublishVideoPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    private String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }


    public void videoUpload(String content, String firtFrameImgPath, final String videoPath) {
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
                        bMvpView.showMsg("上传小视频完成");
                        bMvpView.publishSuccess();
                    }
                });
    }
}
