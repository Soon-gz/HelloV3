package com.abings.baby.teacher.ui.attendanceManager;

import com.hellobaby.library.ui.base.MvpView;

/**
 * 作者：ShuWen
 * 时间： 2017/12/12.
 * 描述：接口，将Activity和presenter解耦
 */

public interface AttendenceMvpView<T> extends MvpView<T>{
    void showRedPoint(Integer integer);
}
