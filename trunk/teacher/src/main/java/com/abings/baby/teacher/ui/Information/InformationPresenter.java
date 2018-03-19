package com.abings.baby.teacher.ui.Information;

import com.abings.baby.teacher.ZSApp;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.List;

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

    public void getTinfoDiscover(int pageNum){
        mDataManager.getTinfoDiscover("1",pageNum)
                .compose(RxThread.<BaseModel<List<InfomationModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<InfomationModel>>(bMvpView) {

                    @Override
                    protected void callSuccess(List<InfomationModel> infomationModels) {
                        bMvpView.showData(infomationModels);
                    }

                    @Override
                    protected void callServerError(BaseModel baseModel) {
                        bMvpView.showData(null);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showData(null);
                    }
                });
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
//咨询
    public void selectNewsInfoBySchoolIdPage(int pageNum) {
        mDataManager.selectNewsInfoBySchoolIdPage(ZSApp.getInstance().getSchoolId(),pageNum).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        bMvpView.showListData(JSONArray.parseArray(jsonObject.getJSONArray("result").toJSONString(),InformationModel.class));
                        bMvpView.setPageModel(JSONObject.toJavaObject(jsonObject.getJSONObject("page"),PageModel.class));
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
    //分页的收藏夹
    public void SelectTFavoritePage(int PageNum) {
        resetSubscription();
        bMvpView.showProgress(true);
        mDataManager.SelectTFavoritePage(ZSApp.getInstance().getTeacherId(),"2",PageNum).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
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
}
