package com.abings.baby.ui.Information.infomationNew;

import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ShuWen on 2017/6/15.
 */

public class InfoSearchPresenter extends BasePresenter<InfoSearchMvp>{
    private final DataManager mDataManager;

    @Inject
    public InfoSearchPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void searchInfoMsg(String condition){
        LogZS.i("搜索条件："+condition);
        mDataManager.searchInfoMsg(condition)
                .compose(RxThread.<BaseModel<List<InfomationModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<InfomationModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<InfomationModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showMsg("未在公开数据搜索到该内容~");
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
}
