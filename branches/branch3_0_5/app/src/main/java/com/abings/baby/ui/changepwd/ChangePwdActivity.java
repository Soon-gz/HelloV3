package com.abings.baby.ui.changepwd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.login.forgetpwd.RePwdMvpView;
import com.abings.baby.ui.login.forgetpwd.RePwdPresenter;
import com.hellobaby.library.ui.changepwd.BaseChangePwdActivity;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/11/2.
 * description : 修改密码
 */

public class ChangePwdActivity extends BaseChangePwdActivity implements RePwdMvpView{

    @Inject
    RePwdPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }
    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    protected void getSmsCodeClickListener() {
        presenter.checkPhoneExistsSmsCode(bEtPhone.getText().toString().trim());
    }

    @Override
    protected void okOnClick(EditText etPhone, EditText etSmsCode, EditText etPwd, EditText etRePwd) {
        super.okOnClick(etPhone, etSmsCode, etPwd, etRePwd);
        presenter.smsCodeBasedPasswordChange(etPhone.getText().toString().trim(),etPwd.getText().toString().trim(),etSmsCode.getText().toString().trim());
    }
    @Override
    public void showData(Object o) {

    }

    @Override
    public void rePwdSuccess() {
        finish();
    }
}
