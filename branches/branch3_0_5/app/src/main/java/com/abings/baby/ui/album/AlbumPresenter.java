package com.abings.baby.ui.album;

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

/**
 * Created by zwj on 2016/12/29.
 * description : 相册
 */

public class AlbumPresenter extends BasePresenter<AlbumMvpView> {

    @Inject
    DataManager dataManager;

    @Inject
    public AlbumPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void ablumGetImgs(String commonId) {
        resetSubscription();
        dataManager.albumGetImgs(commonId)
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        bMvpView.initAlbumImgs(albumModel);
                    }
                });
    }

    /**
     * 删除
     *
     * @param imageIds
     * @param imageNames
     */
    public void ablumDelImgs(final String commonId, final String title, final String content, final String imageIds, final String imageNames) {

        resetSubscription();
        dataManager.albumDelImgs(imageIds, imageNames)
                .flatMap(new Func1Class<String, BaseModel<AlbumModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<AlbumModel>> callSuccess(String s) {
                        return dataManager.albumUpdateContent(commonId, title, content);
                    }
                })
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModelBaseModel) {
                        bMvpView.albumDelImgs(imageIds, imageNames);
                        bMvpView.albumOptFinish();
                    }
                });
    }

    private int uploadImgCount = 0;
    public void albumUpdateImgs(final AlbumModel albumModelFinal, final List<String> imageList) {
        uploadImgCount = 0;
        bMvpView.showProgress(true);
        resetSubscription();
        Observable.from(imageList)
                .flatMap(new Func1<String, Observable<BaseModel<AlbumModel>>>() {
                    @Override
                    public Observable<BaseModel<AlbumModel>> call(String s) {
                        return dataManager.uploadAlbumImg(albumModelFinal.getCommonId(), s);
                    }
                })
                .flatMap(new Func1Class<AlbumModel, BaseModel<AlbumModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<AlbumModel>> callSuccess(AlbumModel albumModel) {
                        return dataManager.albumUpdateContent(albumModelFinal.getCommonId(), albumModelFinal.getTitle(), albumModelFinal.getContent());
                    }
                })
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        uploadImgCount++;
                        if(imageList.size()==uploadImgCount){
                            bMvpView.showMsg("上传照片完成");
                            bMvpView.albumOptFinish();
                        }
                    }
                });
    }

    /**
     * 修改相册标题和内容
     *
     * @param albumModelFinal
     */
    public void albumUpdateTitleContent(AlbumModel albumModelFinal) {
        dataManager
                .albumUpdateContent(albumModelFinal.getCommonId(), albumModelFinal.getTitle(), albumModelFinal.getContent())
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        bMvpView.showMsg("修改相册完成");
                        bMvpView.albumOptFinish();
                    }
                });
    }

    public void deleteAlbumById(AlbumModel albumModel){
        resetSubscription();
        bMvpView.showProgress(true);
        dataManager.deleteAlbumById(albumModel.getCommonId())
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("删除相册成功");
                        bMvpView.albumDelFinish();
                    }
                });
    }
}
