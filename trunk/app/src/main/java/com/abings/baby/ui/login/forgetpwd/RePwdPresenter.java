package com.abings.baby.ui.login.forgetpwd;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;
import com.hellobaby.library.utils.MD5Utils;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/9/28.
 * description :
 */

public class RePwdPresenter extends BasePresenter<RePwdMvpView> {

    private final DataManager mDataManager;

    @Inject
    public RePwdPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    /**
     * 获取验证码
     * @param phone
     */
    public void checkPhoneExistsSmsCode(String phone) {
        resetSubscription();
        mDataManager.checkPhoneExists(phone)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("已发送验证码");
                    }
                });
    }

    /**
     * 注册用户
     * @param phone
     * @param pwd
     * @param smsCode
     */
    public void smsCodeBasedPasswordChange(String phone, String pwd, String smsCode) {
        resetSubscription();
        bMvpView.showProgress(true);
        mDataManager.smsCodeBasedPasswordChange(phone, MD5Utils.encodeMD5(pwd), smsCode)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("密码修改成功");
                        bMvpView.rePwdSuccess();
                    }
                });
    }

}
