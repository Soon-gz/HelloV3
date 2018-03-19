package com.abings.baby.teacher.ui.Information;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/6.
 */

public class InformationPresenter extends BasePresenter<InformationMvp> {
    private final DataManager mDataManager;

    @Inject
    public InformationPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void selectNewsInfoBySchoolId() {
        mDataManager.selectNewsInfoBySchoolId(ZSApp.getInstance().getSchoolId(),"").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.toJSONString(), InformationModel.class));
                    }
                });
    }

    public void SelectTFavorite() {
        mDataManager.SelectTFavorite(ZSApp.getInstance().getTeacherId(), "2").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.toJSONString(), InformationModel.class));
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        super.callError(baseModel);
                        bMvpView.showListData(null);
                    }
                });
    }

    //    //    5.13接口功能：家长端，收藏夹-收藏、添加
////    9.10 教师端同
//    public Observable<BaseModel<JSONObject>> insertTFavorite(String createId, String CreateType, String favoriteType, String favoriteFavoriteid) {
//        return mAPIService.insertTFavorite(token, createId, CreateType, favoriteType, favoriteFavoriteid);
//    }
    public void insertTFavorite(String favoriteType, String favoriteFavoriteid) {
        mDataManager.insertTFavorite(ZSApp.getInstance().getTeacherId(), "2", favoriteType, favoriteFavoriteid).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsg("收藏成功");
                    }
                });
    }

    //    //    5.14接口功能：家长端，收藏夹-取消收藏
////    9.11 教师端同
//    public Observable<BaseModel<JSONObject>> DeleteTFavorite(String favoriteId, String CreateType) {
//        return mAPIService.DeleteTFavorite(token, favoriteId, CreateType);
//    }
    public void DeleteTFavorite(String favoriteId) {
        mDataManager.DeleteTFavorite(favoriteId).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showMsgFinish("取消收藏成功");
                    }
                });
    }
}
