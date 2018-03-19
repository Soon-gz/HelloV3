package com.abings.baby.ui.main.fm;


import android.util.Log;

import com.abings.baby.ZSApp;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.LogZS;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zwj on 2016/12/30.
 * description :
 */

public class MePresenter extends BasePresenter<MeMvpView> {

    private final DataManager dataManager;

    @Inject
    public MePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 上传宝宝头像
     */
    public void babyUploadHeadImg(String headImgPath) {
        resetSubscription();
        dataManager.babyUploadHeadImg(ZSApp.getInstance().getBabyId(), headImgPath)
                .compose(RxThread.<BaseModel<BabyModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<BabyModel>(bMvpView) {
                    @Override
                    protected void callSuccess(BabyModel babyModel) {
                        //设置头像
                        bMvpView.showMsg("上传成功");
                        ZSApp.getInstance().getBabyModel().setHeadImgUrl(babyModel.getHeadImgUrl());
                    }
                });
    }

    /**
     * 修改家长头像
     */
    public void userUploadHeadImg(String headImgPath) {
        resetSubscription();
        bMvpView.showProgress(true);
        String userId = ZSApp.getInstance().getUserId();
        dataManager.changeHeadImgById(userId, headImgPath)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("上传成功");
                        ZSApp.getInstance().getLoginUser().setHeadImageurl(s);
                    }
                });
    }

    /**
     * 更新宝宝信息
     *
     * @param babyModel
     */
    public void babyUpdate(BabyModel babyModel) {
        resetSubscription();
        String babyInfoJson = new Gson().toJson(babyModel);
        dataManager.babayUpdateInfo(babyInfoJson)
                .compose(RxThread.<BaseModel<BabyModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<BabyModel>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<BabyModel> babyModelBaseModel) {
                        bMvpView.showMsg("修改成功");
                    }
                });
    }

    /**
     * 更新用户信息
     *
     * @param userModel
     */
    public void userUpdateInfo(UserModel userModel) {
        String babyId = ZSApp.getInstance().getBabyId();
        resetSubscription();
        String userId = String.valueOf(userModel.getUserId());
        String userName = userModel.getUserName() == null ? "" : userModel.getUserName();
        String relation = userModel.getRelation() == null ? "" : userModel.getRelation();
        String userEmail = userModel.getUserEmail() == null ? "" : userModel.getUserEmail();

        dataManager.updateUserInfo(babyId, userId, userName, relation, userEmail)
                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                    @Override
                    protected void callSuccess(UserModel userModel) {
                        LogZS.d("userUpdateInfo--->ok");
                    }
                });
    }

    /**
     * 5.4接口功能：家长端，获取家庭人s信息
     */
    public void selectCareUsers() {
        resetSubscription();
        dataManager.selectCareUsers(ZSApp.getInstance().getBabyId())
                .compose(RxThread.<BaseModel<List<UserModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<List<UserModel>>(bMvpView) {
                    @Override
                    protected void callSuccess(List<UserModel> userModels) {
                        bMvpView.babySetCareUsers(userModels);
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        LogZS.e(baseModel.getMsg());
                        bMvpView.babySetCareUsers(new ArrayList<UserModel>());
                    }
                });
    }


    /**
     * 修改手机号是否公开
     *
     * @param isPublic 是否公开 0：是 ；1：否
     */
    public void changePublic(String isPublic) {
        String userId = ZSApp.getInstance().getUserId();

        dataManager.changePublic(userId, isPublic)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<String>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<String> stringBaseModel) {
                    }
                });
    }

    public void logout() {
        resetSubscription();
        dataManager.logout()
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.logoutSuccess();
                    }
                });
    }

//    /**
//     * 关注与他人的宝宝
//     *
//     * @param babyId
//     */
//    public void insertCareBaby(String babyId) {
//        resetSubscription();
//        final String userId = String.valueOf(ZSApp.getInstance().getLoginUser().getUserId());
//        dataManager.insertCareBaby(userId,babyId)
//                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
//                .subscribe(new SubscriberClass<String>(bMvpView) {
//                    private boolean isSuccess = false;
//                    @Override
//                    protected void callSuccess(String s) {
//                        isSuccess = true;
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
//                        if(isSuccess){
//                            getLoginInfo(userId);
//                        }
//                    }
//                });
//    }

    /**
     * 获取用户信息
     */
    public void getLoginInfo(){
        final String userId = String.valueOf(ZSApp.getInstance().getLoginUser().getUserId());
        dataManager.selectTUserbabyById(userId)
                .flatMap(new Func1Class<List<BabyModel>, BaseModel<UserModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<UserModel>> callSuccess(List<BabyModel> listBaseModel) {
                        if (listBaseModel.size() <= 0) {
//                            bMvpView.showMsg("对不起，您还没有宝宝");
                            return null;
                        } else {
//                            ZSApp.getInstance().setBabyModel(listBaseModel.get(0));
                            ZSApp.getInstance().setListBaby(listBaseModel);
                            return dataManager.selectUserExtendByUBId(userId, ZSApp.getInstance().getBabyId());
                        }
                    }
                })
                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                    @Override
                    protected void callSuccess(UserModel userModel) {

                        userModel.setSelected(true);//设置为选中
                        ZSApp.getInstance().setLoginUser(userModel);
                        bMvpView.careBabySuccess();
                    }
                });
    }

    /**
     *
     * @param userId
     * @param babyId
     */
    public void cancelCareBabyById(final String userId, final String babyId, final boolean isBaby){
        dataManager.cancelCareBabyById(userId,babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        if(isBaby){
                            bMvpView.showMsg("您已取消关注该宝宝");
                        }else{
                            bMvpView.showMsg("取消成功");
                        }
                        bMvpView.cancelCareBabySuccess(userId,babyId,isBaby);
                    }
                });
    }

    public void deleteHeightWeightByBabyId(String babyId){
        dataManager.deleteHeightWeightByBabyId(babyId).compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                    }
                });
    }
    //   15.2  更新Alert  系统消息、收发件箱、校园、宝宝评语、考勤、教学计划等。
    public void updateAlert(TAlertModel tAlert) {
        bMvpView.showProgress(true);
        String userId= ZSApp.getInstance().getUserId();
        resetSubscription();
        tAlert.setUserId(Integer.valueOf(userId));
        tAlert.setBabyId(Integer.valueOf(ZSApp.getInstance().getBabyId()));
        String tAlertStr=new Gson().toJson(tAlert);
        dataManager.updateAlert(tAlertStr).compose(RxThread.<BaseModel<JSONObject>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<JSONObject>(bMvpView) {
                    @Override
                    protected void callSuccess(JSONObject jsonObject) {
                        LogZS.i("更新时间成功");
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

    public void updatePick(String userId, String babyId, boolean isPick){
        dataManager.updatePick(userId,babyId,isPick)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("设置成功");
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
//                        super.callError(baseModel);
                        bMvpView.showError("设置失败");
                    }
                });
    }
}
