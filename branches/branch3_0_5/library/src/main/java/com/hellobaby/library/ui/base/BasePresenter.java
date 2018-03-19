package com.hellobaby.library.ui.base;

import rx.Subscription;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    protected T bMvpView;
    protected Subscription bSubscription;


    @Override
    public void attachView(T mvpView) {
        bMvpView = mvpView;
    }

    @Override
    public void detachView() {
        bMvpView = null;
        if (bSubscription != null) {
            bSubscription.unsubscribe();
        }
    }


    /**
     * 重置Subscription
     */
    public void resetSubscription() {
//        bMvpView.showProgress(true);
        if (bSubscription != null) {
            bSubscription.unsubscribe();
        }
    }

    /**
     * @return 获取MvpView
     */
    public T getMvpView() {
        return bMvpView;
    }

    //没用到
    public boolean isViewAttached() {
        return bMvpView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
