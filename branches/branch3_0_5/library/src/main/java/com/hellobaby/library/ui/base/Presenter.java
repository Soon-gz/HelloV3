package com.hellobaby.library.ui.base;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

 interface Presenter<V extends MvpView> {
    /**
     * 附上view
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * 分离view
     */
    void detachView();
}
