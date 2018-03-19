package com.abings.baby.teacher.ui.publishteachingplan;

import com.hellobaby.library.ui.base.MvpView;

import java.util.List;


public interface TeachingPlanMvpView<T> extends MvpView<T> {

    public void showTeachingPlanList(List list);
    public void publishPlanSuccess();
    public void publishDeleteSuccess(int posion);
}
