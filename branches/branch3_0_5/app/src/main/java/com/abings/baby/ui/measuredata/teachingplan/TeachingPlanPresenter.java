package com.abings.baby.ui.measuredata.teachingplan;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.measuredata.remark.ReMarkMvpView;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 */

public class TeachingPlanPresenter extends BasePresenter<TeachingPlanMvpView> {

    private final DataManager mDataManager;

    @Inject
    public TeachingPlanPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void selectTeachingplanFromUserId(String planningTime){
        bMvpView.showProgress(true);
        resetSubscription();
        mDataManager.selectTeachingplanFromUserId(ZSApp.getInstance().getClassId(),planningTime)
                .compose(RxThread.<BaseModel<List<TeachingPlanModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeachingPlanModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeachingPlanModel> jsonArray) {
                        bMvpView.showTeachingPlanList(jsonArray);
                    }
                });
    }

}
