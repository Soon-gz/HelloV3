package com.abings.baby.ui.onlytext;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2016/12/30.
 */

public class TextPresenter extends BasePresenter<TextMvpView> {
    @Inject
    DataManager dataManager;
    @Inject
    public TextPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 删除日志接口
     *
     * @param albumModel
     */
    public void logDel(AlbumModel albumModel) {
        resetSubscription();
        dataManager.logDel(albumModel.getCommonId())
                .flatMap(new Func1Class<AlbumModel, String>(bMvpView) {
                    @Override
                    protected Observable<String> callSuccess(AlbumModel albumModel) {
                        return Observable.just("");
                    }
                })
                .compose(RxThread.<String>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<String>(bMvpView) {
                    @Override
                    public void onNext(String commonId) {
                        bMvpView.showMsg("日志删除成功");
                        bMvpView.uploadSuccess();
                    }
                });
    }

    /**
     * 日志修改
     *
     * @param albumModel
     */
    public void logUpdate(AlbumModel albumModel) {
        resetSubscription();
        dataManager.albumUpdateContent(albumModel.getCommonId(), albumModel.getTitle(), albumModel.getContent())
                .flatMap(new Func1Class<AlbumModel, String>(bMvpView) {
                    @Override
                    protected Observable<String> callSuccess(AlbumModel albumModel) {
                        return Observable.just("日志修改成功");
                    }
                }).compose(RxThread.<String>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<String>(bMvpView) {
                    @Override
                    public void onNext(String s) {
                        bMvpView.showMsg("日志修改成功");
                        bMvpView.uploadSuccess();
                    }
                });
    }
}
