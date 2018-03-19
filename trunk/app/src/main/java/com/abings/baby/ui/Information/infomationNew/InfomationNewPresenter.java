package com.abings.baby.ui.Information.infomationNew;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.Information.InfomationChild.InfomationNewMvp;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.CareOrCaredModel;
import com.hellobaby.library.data.model.CommentModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.ToastUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/5.
 */

public class InfomationNewPresenter extends BasePresenter<InfomationNewMvp> {
    private final DataManager mDataManager;

    @Inject
    public InfomationNewPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void getTinfoDiscover(int pageNum){
        mDataManager.getTinfoDiscover("2",pageNum)
                .compose(RxThread.<BaseModel<List<InfomationModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<InfomationModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<InfomationModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }
                });
    }

    public void getTinfoCared(int pageNum){
        mDataManager.getTinfoCared("2",pageNum)
                .compose(RxThread.<BaseModel<List<InfomationModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<InfomationModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<InfomationModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }
                });
    }


    public void addLikeInfo(String topicType ,String topicId){
        mDataManager.addLikeInfo(topicType, topicId)
                .compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                    }
                });
    }
    //  15.3 获取Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void selectAlert() {
        bMvpView.showProgress(true);
        String userId = ZSApp.getInstance().getUserId();
        String classId = ZSApp.getInstance().getClassId();
        String schoolId = ZSApp.getInstance().getSchoolId();
        String babyId = ZSApp.getInstance().getBabyId();
        mDataManager.selectAlert(userId, classId, schoolId, babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        final TAlertBooleanModel tAlertBooleanModel=JSONObject.parseObject(jsonObject.toJSONString(), TAlertBooleanModel.class);
                        mDataManager.selectInfoIsUpdate().compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On()).subscribe(new SubscriberClass<String>(bMvpView) {
                            @Override
                            protected void callSuccess(String str) {
                                tAlertBooleanModel.setInfomsg(Integer.valueOf(str)>0?0:1);
                                ZSApp.getInstance().settAlertBooleanModel(tAlertBooleanModel);
                                bMvpView.showBadgeView(tAlertBooleanModel);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                    }
                });
    }

}
