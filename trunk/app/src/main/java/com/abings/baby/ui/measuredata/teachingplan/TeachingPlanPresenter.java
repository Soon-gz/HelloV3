package com.abings.baby.ui.measuredata.teachingplan;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.ArrayList;
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

    public void selectTeachingplan3FromUserId() {
        bMvpView.showProgress(true);
        resetSubscription();
        mDataManager.selectTeachingplan3FromUserId(ZSApp.getInstance().getClassId())
                .compose(RxThread.<BaseModel<List<TeachingPlanModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeachingPlanModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeachingPlanModel> list) {
                        List<TeachingPlanModel> showList = getList();
                        for (int i = 0; i < list.size(); i++) {
                            TeachingPlanModel model = list.get(i);
                            if (model.isTypeNext()) {
                                //下周
                                showList.set(2, model);
                            } else if (model.isTypeThis()) {
                                //这周
                                showList.set(1, model);
                            } else if (model.isTypeLast()) {
                                //本周
                                showList.set(0, model);
                            }
                        }
                        bMvpView.showTeachingPlanList(showList);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                        List<TeachingPlanModel> showList =getList();
                        bMvpView.showTeachingPlanList(showList);
                    }
                    private List<TeachingPlanModel> getList(){
                        List<TeachingPlanModel> showList = new ArrayList<>();
                        for (int i = 3; i > 0; i--) {
                            //排序用
                            TeachingPlanModel model = new TeachingPlanModel();
                            model.setType(i);
                            model.setImageurl("21ad4eb243e140abbc36e65204c83f59.jpeg");
                            showList.add(model);
                        }
                        return showList;
                    }
                });
    }

}
