package com.abings.baby.ui.login.register;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.MD5Utils;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/01/04.
 * description :
 */

public class RegisterPresenter extends BasePresenter<RegisterMvpView> {

    private final DataManager mDataManager;

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    /**
     * 获取验证码
     * @param phone
     */
    public void checkUserExitsSmsCode(String phone) {
        mDataManager.checkUserExits(phone)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("已发送验证码");
                    }

                    @Override
                    protected void callError(BaseModel baseModel) {
                        bMvpView.showMsg("该账号已注册，请不要重复注册。");
                    }
                });
    }

    /**
     * 注册用户
     * @param phone
     * @param pwd
     * @param smsCode
     */
    public void subRegister(String phone, String pwd, String smsCode,String userName) {
        bMvpView.showProgress(true);
        mDataManager.subRegister(phone, MD5Utils.encodeMD5(pwd), smsCode,userName)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("注册成功");
                        bMvpView.registerSuccess();
                    }
                });
    }
}
