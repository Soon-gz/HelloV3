package com.abings.baby.ui.measuredata.remark;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class ReMarkPresenter extends BasePresenter<ReMarkMvpView> {

    private final DataManager mDataManager;

    @Inject
    public ReMarkPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
    public void selectTevaluationAllBybabyid(String time){
        bMvpView.showProgress(true);
        String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        mDataManager.selectTevaluationAllBybabyid(babyId,time)
                .compose(RxThread.<BaseModel<ReviewModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<ReviewModel>(bMvpView) {
                    @Override
                    protected void callSuccess(ReviewModel reviewModel) {
                        bMvpView.setReMark(reviewModel);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        ReviewModel reviewModel = new ReviewModel();
                        reviewModel.setActivity(0);
                        reviewModel.setDrinking(0);
                        reviewModel.setEating(0);
                        reviewModel.setNoonbreak(0);
                        reviewModel.setToilet(0);
                        bMvpView.setReMark(reviewModel);
                    }
                });
    }

}
