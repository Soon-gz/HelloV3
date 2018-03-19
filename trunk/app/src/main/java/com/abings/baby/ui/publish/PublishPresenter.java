package com.abings.baby.ui.publish;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;


public class PublishPresenter extends BasePresenter<PublishMvpView> {

    private final DataManager dataManager;

    @Inject
    public PublishPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void logCreate(String title, String content) {
        resetSubscription();
        String userId = ZSApp.getInstance().getUserId();
        String babyId = ZSApp.getInstance().getBabyId();
        dataManager.logCreate(userId, babyId, title, content)
                .flatMap(new Func1Class<AlbumModel, String>(bMvpView) {
                    @Override
                    protected Observable<String> callSuccess(AlbumModel albumModel) {
                        return Observable.just(albumModel.getCommonId());
                    }
                })
                .compose(RxThread.<String>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<String>(bMvpView) {
                    @Override
                    public void onNext(String commonId) {
                        bMvpView.showMsg("上传成功");
                        bMvpView.logCreateFinish();
                    }
                });
    }


}
