package com.hellobaby.library.ui.login.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.utils.PasswordUtils;

/**
 * Created by zwj on 2016/12/5.
 * description : 注册&忘记密码基类
 */

public abstract class BaseRegisterActivity<T> extends BasePwdActivity<T> {


    protected EditText bEtPhone;
    protected EditText bEtSmsCode;
    protected Button bBtnGetSmsCode;
    protected EditText bEtPwd;
    protected EditText bEtRePwd;
    protected Button bBtnOk;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_register;
    }

    @Override
    protected Button getBtnGetSmsCode() {
        return bBtnGetSmsCode;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        initView();
        super.initViewsAndEvents(savedInstanceState);
        setBtnLeftClickFinish();
    }

    private void initView() {
        bEtPhone = (EditText) findViewById(R.id.register_et_phone);
        bEtSmsCode = (EditText) findViewById(R.id.register_et_smsCode);
        bBtnGetSmsCode = (Button) findViewById(R.id.register_btn_getSmsCode);
        bEtPwd = (EditText) findViewById(R.id.register_et_pwd);
        bEtRePwd = (EditText) findViewById(R.id.register_et_rePwd);
        bBtnOk = (Button) findViewById(R.id.register_btn_ok);
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
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtSmsCode, bEtPwd, bEtRePwd);
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
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtPwd, bEtRePwd);
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
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtSmsCode, bEtRePwd);
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
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtPhone, bEtSmsCode, bEtPwd);
            }
        });

        //获取短信验证按钮
        bBtnGetSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isPhoneRet = PasswordUtils.isPhoneNum(bEtPhone.getText().toString().trim());
                if(isPhoneRet!=null){
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
                if(isPhoneRet!=null){
                    showToast(isPhoneRet);
                    return;
                }
                okOnClick(bEtPhone, bEtSmsCode, bEtPwd, bEtRePwd);
            }
        });
    }

}
