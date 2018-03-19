package com.hellobaby.library.ui.base;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public interface MvpView<T> {
    /**
     * 显示String的数据
     * @param msg
     */
    void showMsg(String msg);

    /**
     * 显示T对象
     * @param t 可以是list<T>或者是
     */
    void showData(T t);

    /**
     * 显示错误信息
     * @param err
     */
    void showError(String err);

    /**
     * 是否展示等待条
     * @param isShow true:展示；false：隐藏
     */
    void showProgress(boolean isShow);
}
