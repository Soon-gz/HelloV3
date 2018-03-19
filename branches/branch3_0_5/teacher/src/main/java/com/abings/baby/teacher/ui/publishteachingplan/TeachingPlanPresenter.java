package com.abings.baby.teacher.ui.publishteachingplan;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/29.
 * description :
 */

public class TeachingPlanPresenter extends BasePresenter<TeachingPlanMvpView> {

    @Inject
    DataManager dataManager;

    @Inject
    public TeachingPlanPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void selectTeachingplanByTeacherId(){
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.selectTeachingplanByTeacherId(ZSApp.getInstance().getTeacherId())
                .compose(RxThread.<BaseModel<List<TeachingPlanModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<TeachingPlanModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<TeachingPlanModel> jsonArray) {
                        bMvpView.showTeachingPlanList(jsonArray);
                    }
                });
    }
    public void createTeacherPlan(String classstr,String time,String path) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.insertTeachingplan(ZSApp.getInstance().getTeacherId(),classstr,time,path)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.publishPlanSuccess();
                    }
                });
    }
    public void deleteTeachingplanByTeacherId(String teachingplanId, final int posion) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.deleteTeachingplanByTeacherId(teachingplanId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.publishDeleteSuccess(posion);
                    }
                });
    }
}
