package com.abings.baby.ui.publishvideo;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * 视频播放
 */
public class VideoPlayPresenter extends BasePresenter<VideoPlayMvpView> {

    private final DataManager dataManager;



    @Inject
    public VideoPlayPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void videoDel(AlbumModel albumModel) {
        resetSubscription();
        dataManager.videoDel(albumModel.getCommonId())
                .compose(RxThread.<BaseModel<AlbumModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<AlbumModel>(bMvpView) {
                    @Override
                    protected void callSuccess(AlbumModel albumModel) {
                        bMvpView.showMsg("删除成功");
                        bMvpView.delSuccess();
                    }
                });
    }
}
