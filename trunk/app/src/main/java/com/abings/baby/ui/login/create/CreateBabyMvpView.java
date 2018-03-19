package com.abings.baby.ui.login.create;


import com.hellobaby.library.ui.base.MvpView;

public interface CreateBabyMvpView extends MvpView {

    /**
     * 创建宝宝
     * @param babyId
     */
    public void createBabyFinish(int babyId);

    /**
     * 创建对应的提醒
     */
    public void createFinish(String msg);
}
