package com.abings.baby.ui.login.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.login.register.BasePwdActivity;
import com.hellobaby.library.utils.PasswordUtils;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/05/03.
 * description : 注册 + 用户姓名
 */

public class RegisterActivity extends BasePwdActivity implements RegisterMvpView {

    @Inject
    RegisterPresenter presenter;

    protected EditText bEtName;
    protected EditText bEtPhone;
    protected EditText bEtSmsCode;
    protected Button bBtnGetSmsCode;
    protected EditText bEtPwd;
    protected EditText bEtRePwd;
    protected Button bBtnOk;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_register_name;
    }

    @Override
    protected Button getBtnGetSmsCode() {
        return bBtnGetSmsCode;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        initView();
        super.initViewsAndEvents(savedInstanceState);
        setBtnLeftClickFinish();
        presenter.attachView(this);
        setTitleText("注册");
        bBtnOk.setText("注册");
    }


    private void initView() {
        bEtName = (EditText) findViewById(R.id.registerName_et_name);
        bEtPhone = (EditText) findViewById(R.id.registerName_et_phone);
        bEtSmsCode = (EditText) findViewById(R.id.registerName_et_smsCode);
        bBtnGetSmsCode = (Button) findViewById(R.id.registerName_btn_getSmsCode);
        bEtPwd = (EditText) findViewById(R.id.registerName_et_pwd);
        bEtRePwd = (EditText) findViewById(R.id.registerName_et_rePwd);
        bBtnOk = (Button) findViewById(R.id.registerName_btn_ok);

        //用户姓名
        bEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtSmsCode, bEtPwd, bEtRePwd, bEtPhone);
            }
        });
        //手机号
        bEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isCanGetSmsCode()) {
                    LoginUtils.setBtnEnabled(s, bEtPhone, bBtnGetSmsCode);
                }
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtSmsCode, bEtPwd, bEtRePwd, bEtName);
            }
        });
        //短信验证码
        bEtSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtPwd, bEtRePwd, bEtName);
            }
        });
        //密码
        bEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtSmsCode, bEtRePwd, bEtName);
            }
        });
        //重复密码
        bEtRePwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtSmsCode, bEtPwd, bEtName);
            }
        });

        //获取短信验证按钮
        bBtnGetSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isPhoneRet = PasswordUtils.isPhoneNum(bEtPhone.getText().toString().trim());
                if (isPhoneRet != null) {
                    showToast(isPhoneRet);
                    return;
                }
                smsCodeOnClick(bEtPhone);
            }
        });
        //确认按钮
        bBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isPhoneRet = PasswordUtils.isPhoneNum(bEtPhone.getText().toString().trim());
                if (isPhoneRet != null) {
                    showToast(isPhoneRet);
                    return;
                }
                okOnClick(bEtPhone, bEtSmsCode, bEtPwd, bEtRePwd);
            }
        });
    }


    @Override
    protected void getSmsCodeClickListener() {
        presenter.checkUserExitsSmsCode(bEtPhone.getText().toString().trim());
    }

    @Override
    public void registerSuccess() {
        finish();
    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void okOnClick(EditText etPhone, EditText etSmsCode, EditText etPwd, EditText etRePwd) {
        super.okOnClick(etPhone, etSmsCode, etPwd, etRePwd);
        presenter.subRegister(etPhone.getText().toString().trim(), etPwd.getText().toString().trim(), etSmsCode.getText().toString().trim(), bEtName.getText().toString().trim());
    }
}
