package com.abings.baby.ui.login;

import android.util.Log;

import com.abings.baby.ZSApp;
import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.data.remote.rx.Func1Class;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberBase;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private final DataManager mDataManager;
    private String mUserId;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    /**
     * 登录
     *
     * @param name 手机号
     * @param pwd  密码
     */
    public void login(final String name, final String pwd) {
        resetSubscription();
        bMvpView.showProgress(true);
        mDataManager.checkLoginUser(name, pwd)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String token) {
                        DataManager.token = token;
                        resetSubscription();
                        bMvpView.showProgress(true);
                        //继续登录
                        mDataManager.checkToken()
                                .flatMap(new Func1Class<String, BaseModel<List<BabyModel>>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<List<BabyModel>>> callSuccess(String userId) {
                                        mUserId = userId;
                                        //userId 作为bugly显示的id
                                        CrashReport.setUserId(mUserId);
                                        ZSApp.getInstance().setUserId(userId);
                                        return mDataManager.selectTUserbabyById(userId);
                                    }
                                })
                                .flatMap(new Func1Class<List<BabyModel>, BaseModel<UserModel>>(bMvpView) {
                                    @Override
                                    protected Observable<BaseModel<UserModel>> callSuccess(List<BabyModel> listBaseModel) {
                                        if (listBaseModel.size() <= 0) {
                                            bMvpView.showMsg("对不起，您还没有宝宝");
                                            bMvpView.toNeedBaby();
                                            return null;
                                        } else {
                                            ZSApp.getInstance().setBabyModel(listBaseModel.get(0));
                                            ZSApp.getInstance().setListBaby(listBaseModel);
                                            bMvpView.loginSuccess();
                                            return mDataManager.selectUserExtendByUBId(mUserId, ZSApp.getInstance().getBabyId());
                                        }
                                    }
                                })
                                .compose(RxThread.<BaseModel<UserModel>>subscribe_Io_Observe_On())
                                .subscribe(new SubscriberClass<UserModel>(bMvpView) {
                                    @Override
                                    protected void callSuccess(UserModel userModel) {
                                        Log.i("ZLog", "---->" + userModel.toString());
                                        userModel.setSelected(true);//设置为选中
                                        ZSApp.getInstance().setLoginUser(userModel);
                                    }
                                });
                    }
                });


    }

    /**
     * 获取宝宝的列表
     */
    public void getUserBabys() {
        resetSubscription();
//        bMvpView.showProgress(true);
        mDataManager.selectUserByToken()
                .flatMap(new Func1Class<UserModel, BaseModel<List<BabyModel>>>(bMvpView) {
                    @Override
                    protected Observable<BaseModel<List<BabyModel>>> callSuccess(UserModel userModel) {
                        ZSApp.getInstance().setLoginUser(userModel);
                        Log.i("ZLog", "selectUserByToken--->" + userModel.toString());
                        return mDataManager.selectTUserbabyById(String.valueOf(userModel.getUserId()));
                    }
                })
                .compose(RxThread.<BaseModel<List<BabyModel>>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberBase<BaseModel<List<BabyModel>>>(bMvpView) {
                    @Override
                    public void onNext(BaseModel<List<BabyModel>> listBaseModel) {
                        if (listBaseModel.getData().size() <= 0) {
                            bMvpView.showMsg("对不起，您还没有宝宝");
                            bMvpView.toNeedBaby();
                        } else {
                            ZSApp.getInstance().setBabyModel(listBaseModel.getData().get(0));
                            ZSApp.getInstance().setListBaby(listBaseModel.getData());
                            Log.i("ZLog", "getUserBabys--->" + ZSApp.getInstance().getBabyModel().toString());
                            bMvpView.loginSuccess();
                        }
                    }
                });
    }

    public void emptyLogin() {
        UserModel userModel = new UserModel();
        userModel.setUserId(100);
        ZSApp.getInstance().setLoginUser(userModel);
        BabyModel babyModel = new BabyModel();
        babyModel.setBabyId(100);
        babyModel.setClassId(100);
        babyModel.setMasterId(100);
        babyModel.setSchoolId(199);
        ZSApp.getInstance().setBabyModel(babyModel);
        List<BabyModel> list = new ArrayList<>();
        list.add(babyModel);
        list.add(babyModel);
        ZSApp.getInstance().setListBaby(list);
        bMvpView.loginSuccess();
    }

}
