package com.abings.baby.teacher.ui.search;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.text.ParseException;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/6.
 */

public class SearchPresenter extends BasePresenter<SearchMvp> {
    private final DataManager mDataManager;

    @Inject
    public SearchPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void selectNewsInfoBySchoolId(String text) {
        mDataManager.selectNewsInfoBySchoolId(ZSApp.getInstance().getSchoolId(),text).compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showInformationListData(JSONArray.parseArray(jsonObject.toJSONString(), InformationModel.class));
                    }
                });
    }
    public void selectSchoolOnTeacher(String condition){
        String teacherid = String.valueOf(ZSApp.getInstance().getTeacherModel().getTeacherId());
        String schoolid = String.valueOf(ZSApp.getInstance().getSchoolModel().getSchoolId());
        mDataManager.selectEventOnTeacher(teacherid, Const.SCHOOL_ALL,schoolid,condition)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject){
                            bMvpView.showSchoolListData(jsonObject);
                    }
                });
    }
}
