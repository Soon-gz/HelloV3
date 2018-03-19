package com.abings.baby.ui.search;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.main.fm.school.NoSchoolMvp;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

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

    public void selectIndexBySearch(String searchContent) {
        resetSubscription();
        String babyId = ZSApp.getInstance().getBabyId();
        mDataManager.selectIndexBySearch(babyId,searchContent)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(final JSONArray albumModels) {
                            bMvpView.showListData(JSONArray.parseArray(albumModels.toJSONString(),AlbumModel.class));
                    }
                });
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
    // 3.1获取baby所在班级的校园信息 通过type区分   /tevet/selectEventByClassToOneBaby
    public void selectSchooltByClassToOneBaby(String condition) {
        String classid = String.valueOf(ZSApp.getInstance().getBabyModel().getClassId());
        String babyid = String.valueOf(ZSApp.getInstance().getBabyModel().getBabyId());
        String schoolid = String.valueOf(ZSApp.getInstance().getBabyModel().getSchoolId());
        mDataManager.selectEventByClassToOneBaby(classid, babyid, schoolid, Const.SCHOOL_ALL,condition)
                .compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showSchoolListData(jsonObject);
                    }
                });
    }
}
