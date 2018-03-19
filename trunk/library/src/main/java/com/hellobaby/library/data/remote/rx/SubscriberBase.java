package com.hellobaby.library.data.remote.rx;


import com.hellobaby.library.ui.base.MvpView;

import rx.Subscriber;


/**
 * Created by zwj on 2016/9/29.
 * description:基类，上一级不论任何数据
 */

public abstract class SubscriberBase<T> extends Subscriber<T> {
    public MvpView mvpView;

    public SubscriberBase(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if(null!=mvpView) {
            mvpView.showError("未知异常onError");
            mvpView.showProgress(false);
        }
    }

    @Override
    public void onCompleted() {
        if(null!=mvpView)
        mvpView.showProgress(false);
    }
}
