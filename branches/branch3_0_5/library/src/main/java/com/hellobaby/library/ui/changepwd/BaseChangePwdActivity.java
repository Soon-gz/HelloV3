package com.hellobaby.library.ui.changepwd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.login.register.BasePwdActivity;

/**
 * Created by zwj on 2016/11/2.
 * description :修改密码的基类
 */

public abstract class BaseChangePwdActivity<T> extends BasePwdActivity<T> {
    protected EditText bEtPhone;
    protected EditText bEtSmsCode;
    protected Button bBtnGetSmsCode;
    protected EditText bEtPwd;
    protected EditText bEtRePwd;
    protected Button bBtnOk;
    protected LinearLayout bLlContent;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_changepwd;
    }


    @Override
    protected Button getBtnGetSmsCode() {
        return bBtnGetSmsCode;
    }


    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        initView();
        super.initViewsAndEvents(savedInstanceState);
        setBtnRightDrawableRes(R.drawable.title_right_arrow_black);
        setBtnLeftClickFinish();
    }

    private void initView() {
        bEtPhone = (EditText) findViewById(R.id.changePwd_et_phone);
        bEtSmsCode = (EditText) findViewById(R.id.changePwd_et_smsCode);
        bBtnGetSmsCode = (Button) findViewById(R.id.changePwd_btn_getSmsCode);
        bEtPwd = (EditText) findViewById(R.id.changePwd_et_pwd);
        bEtRePwd = (EditText) findViewById(R.id.changePwd_et_rePwd);
        bBtnOk = (Button) findViewById(R.id.changePwd_btn_ok);
        bLlContent = (LinearLayout) findViewById(R.id.changePwd_ll_content);
        bEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnEnabled(s, bEtPhone, bBtnGetSmsCode);
                LoginUtils.setBtnEnabled(s, bBtnOk, bEtSmsCode, bEtPwd, bEtRePwd);
            }
        });
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

        bBtnGetSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsCodeOnClick(bEtPhone);
            }
        });
        bBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okOnClick(bEtPhone, bEtSmsCode, bEtPwd, bEtRePwd);
            }
        });
    }


}
