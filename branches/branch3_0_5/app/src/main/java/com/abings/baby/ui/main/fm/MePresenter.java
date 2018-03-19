package com.abings.baby.ui.main.fm;


import com.abings.baby.ZSApp;
import com.google.gson.Gson;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
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

    /**
     * 关注与他人的宝宝
     *
     * @param babyId
     */
    public void insertCareBaby(String babyId) {
        resetSubscription();
        final String userId = String.valueOf(ZSApp.getInstance().getLoginUser().getUserId());
        dataManager.insertCareBaby(userId,babyId)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    private boolean isSuccess = false;
                    @Override
                    protected void callSuccess(String s) {
                        isSuccess = true;
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(isSuccess){
                            getLoginInfo(userId);
                        }
                    }
                });
    }

    private void getLoginInfo(final String userId){
        dataManager.selectTUserbabyById(userId)
                .flatMap(new Func1Class<List<BabyModel>, BaseModel<UserModel>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<UserModel>> callSuccess(List<BabyModel> listBaseModel) {
                        if (listBaseModel.size() <= 0) {
                            bMvpView.showMsg("对不起，您还没有宝宝");
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
                        bMvpView.showMsg("关注宝宝成功");
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
                        bMvpView.showMsg("取消成功");
                        bMvpView.cancelCareBabySuccess(userId,babyId,isBaby);
                    }
                });
    }
}
