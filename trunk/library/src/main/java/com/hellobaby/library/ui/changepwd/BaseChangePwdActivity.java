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
import com.hellobaby.library.utils.PasswordUtils;

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
        bIvRight.setVisibility(View.GONE);
        bBtnOk.setVisibility(View.GONE);
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
                if (isCanGetSmsCode()) {
                    LoginUtils.setBtnEnabled(s, bEtPhone, bBtnGetSmsCode);
                }
                LoginUtils.setBtnVisibility(s, bIvRight, bEtSmsCode, bEtPwd, bEtRePwd);
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
                LoginUtils.setBtnVisibility(s, bIvRight, bEtPhone, bEtPwd, bEtRePwd);
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
                LoginUtils.setBtnVisibility(s, bIvRight, bEtPhone, bEtSmsCode, bEtRePwd);
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
                LoginUtils.setBtnVisibility(s, bIvRight, bEtPhone, bEtSmsCode, bEtPwd);
            }
        });

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
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = bEtPwd.getText().toString().trim();
                String rePwd = bEtRePwd.getText().toString().trim();
                if(!pwd.equals(rePwd)){
                    showToast("两次密码不一致");
                    return;
                }
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
