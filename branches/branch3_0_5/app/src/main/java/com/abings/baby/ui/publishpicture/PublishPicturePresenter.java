package com.abings.baby.ui.publishpicture;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;


public class PublishPicturePresenter extends BasePresenter<PublishPictureMvpView> {
    @Inject
    DataManager dataManager;

    private String currentAlbumId;
    private int uploadImageCount = 0;

    @Inject
    public PublishPicturePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void createAlbum(AlbumModel albumModel, final List<String> imageList) {
        uploadImageCount = 0;
        bMvpView.showProgress(true);
        resetSubscription();
        String userId = ZSApp.getInstance().getUserId();
        String babyId = ZSApp.getInstance().getBabyId();
        dataManager.createAlbum(userId, babyId, albumModel.getTitle(), albumModel.getContent())
                .flatMap(new Func1Class<AlbumModel, String>(bMvpView) {
                    @Override
                    protected Observable<String> callSuccess(AlbumModel albumModel) {
                        currentAlbumId = albumModel.getCommonId();
                        return Observable.from(imageList);
                    }
                })
                .flatMap(new Func1<String, Observable<BaseModel<AlbumModel>>>() {
                    @Override
                    public Observable<BaseModel<AlbumModel>> call(String s) {
                        return dataManager.uploadAlbumImg(currentAlbumId, s);
                    }
                })
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        uploadImageCount++;
                        if (imageList.size() == uploadImageCount) {
                            bMvpView.showMsg("相册创建完成");
                            bMvpView.uploadFinish();
                        }
                    }
                });
    }
}
