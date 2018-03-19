package com.abings.baby.teacher.ui.publishteachingplan;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

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
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonArray) {
                        bMvpView.showTeachingPlanList(JSONArray.parseArray(jsonArray.toJSONString(),TeachingPlanModel.class));
                    }
                });
    }
    public void createTeacherPlan(String classstr,String time,String time2,String path) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.insertTeachingplan(ZSApp.getInstance().getTeacherId(),classstr,time,time2,path)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("发布成功");
                        bMvpView.publishPlanSuccess();
                    }
                });
    }
    public void deleteTeachingplanByTeacherId(String teachingplanId) {
        bMvpView.showProgress(true);
        resetSubscription();
        dataManager.deleteTeachingplanByTeacherId(teachingplanId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("删除成功");
                    }
                });
    }
}
