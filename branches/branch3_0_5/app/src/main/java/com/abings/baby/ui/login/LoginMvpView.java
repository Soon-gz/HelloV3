package com.abings.baby.ui.login;

import com.hellobaby.library.ui.base.MvpView;

/**
 * Created by zwj on 2016/9/28.
 * description : 登录的按钮
 */

public interface LoginMvpView<T> extends MvpView<T> {
    /**
     * 登录成功
     */
    public void loginSuccess();
    /**
     * 去创建宝宝
     */
    public void toNeedBaby();

}
