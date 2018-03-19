package com.abings.baby.ui.main.fm;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BabyFragmentPresenter extends BasePresenter<BabyFragmentMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public BabyFragmentPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void getIndexCommon() {
        bMvpView.showProgress(true);
        resetSubscription();
        String babyId = ZSApp.getInstance().getBabyId();
        LogZS.i("进来了");
        dataManager.getIndexCommon(babyId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i(jsonObject.getString("result"));
                        List<AlbumModel> lists = new ArrayList<AlbumModel>();
                       JSONArray jsonArray=jsonObject.getJSONArray("result");
                        LogZS.i(jsonArray.toJSONString());
                        lists=JSON.parseArray(jsonArray.toJSONString(),AlbumModel.class);
                        bMvpView.refershIndexCommon(lists);
                        JSONArray timeArray=jsonObject.getJSONArray("resultDate");
                        bMvpView.refershIndexDate(timeArray);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                        bMvpView.refershIndexCommon(new ArrayList<AlbumModel>());
                    }
                });
    }
}
