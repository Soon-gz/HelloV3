package com.abings.baby.teacher.ui.publishpicture;

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


}
