package com.abings.baby.ui.Information;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.message.MsgCenterMvp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.model.TeacherModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

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
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.toJSONString(),InformationModel.class));
                    }
                });
    }
//分页的咨询
    public void selectNewsInfoBySchoolIdPage(int PageNum) {
        mDataManager.selectNewsInfoBySchoolIdPage(ZSApp.getInstance().getSchoolId(),PageNum).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.getJSONArray("result").toJSONString(),InformationModel.class));
                        bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                    }
                });
    }
    public void SelectTFavorite() {
        resetSubscription();
        bMvpView.showProgress(true);
        mDataManager.SelectTFavorite(ZSApp.getInstance().getUserId(),"1").compose(RxThread.<BaseModel<JSONArray>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONArray>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONArray jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.toJSONString(), InformationModel.class));
                    }
                });
    }
//分页的收藏夹
    public void SelectTFavoritePage(int PageNum) {
        resetSubscription();
        if(null!=bMvpView){
            bMvpView.showProgress(true);
        }
        mDataManager.SelectTFavoritePage(ZSApp.getInstance().getUserId(),"1",PageNum).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.getJSONArray("result").toJSONString(),InformationModel.class));
                        bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
                    }
                });
    }
//    //    5.13接口功能：家长端，收藏夹-收藏、添加
////    9.10 教师端同
//    public Observable<BaseModel<JSONObject>> insertTFavorite(String createId, String CreateType, String favoriteType, String favoriteFavoriteid) {
//        return mAPIService.insertTFavorite(token, createId, CreateType, favoriteType, favoriteFavoriteid);
//    }
public void insertTFavorite(String favoriteType,String favoriteFavoriteid) {
    mDataManager.insertTFavorite(ZSApp.getInstance().getUserId(),"1",favoriteType,favoriteFavoriteid).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
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
