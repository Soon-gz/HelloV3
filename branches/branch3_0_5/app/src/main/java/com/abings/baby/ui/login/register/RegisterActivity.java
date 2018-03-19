package com.abings.baby.ui.login.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.ui.login.register.BaseRegisterActivity;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/10/14.
 * description : 注册
 */

public class RegisterActivity extends BaseRegisterActivity implements RegisterMvpView{

    @Inject
    RegisterPresenter presenter;

    @Override
    protected void initDaggerInject() {
        super.initDaggerInject();
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        setTitleText("注册");
        bBtnOk.setText("注册");
    }

    @Override
    protected void getSmsCodeClickListener() {
        presenter.checkUserExitsSmsCode(bEtPhone.getText().toString().trim());
    }

    @Override
    protected void okOnClick(EditText etPhone, EditText etSmsCode, EditText etPwd, EditText etRePwd) {
        super.okOnClick(etPhone, etSmsCode, etPwd, etRePwd);
        presenter.subRegister(etPhone.getText().toString().trim(),etPwd.getText().toString().trim(),etSmsCode.getText().toString().trim());
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void registerSuccess() {
        finish();
    }
}
