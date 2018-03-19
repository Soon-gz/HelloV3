package com.abings.baby.teacher.ui.changephone;

import com.hellobaby.library.data.DataManager;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.data.remote.rx.RxThread;
import com.hellobaby.library.data.remote.rx.SubscriberClass;
import com.hellobaby.library.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/5/3.
 * description :
 */

public class ChangePhonePresenter extends BasePresenter<ChangePhoneMvpView> {
    @Inject
    DataManager dataManager;

    @Inject
    public ChangePhonePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 获取验证码
     */
    public void getChangePhoneSmsCode(String phone) {
        dataManager.tChangePhoneCode(phone)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                    }
                });

    }

    /**
     * 修改手机号
     */
    public void smsCodeBasedPhoneNumChange(String newPhone, String smsCode, String oldPwd) {
        dataManager.tsmsCodeBasedPhoneNumChange(newPhone, smsCode, oldPwd)
                .compose(RxThread.<BaseModel<String>>subscribe_Io_Observe_On())
                .subscribe(new SubscriberClass<String>(bMvpView) {
                    @Override
                    protected void callSuccess(String s) {
                        bMvpView.showMsg("手机号码修改成功，请使用新手号登录");
                        bMvpView.smsCodeBasedPhoneNumChangeSuccess();
                    }
                });
    }
}
