package com.abings.baby.ui.changephone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.main.fm.aboutme.AboutMeFamilyFragment;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.login.register.BasePwdActivity;
import com.hellobaby.library.utils.MD5Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zwj on 2017/05/03.
 * description : 修改手机号
 */
public class ChangePhoneActivity extends BasePwdActivity implements ChangePhoneMvpView {
    @Inject
    ChangePhonePresenter presenter;

    @BindView(R.id.changePhone_et_phone)
    EditText etPhone;
    @BindView(R.id.changePhone_et_smsCode)
    EditText etSmsCode;
    @BindView(R.id.changePhone_btn_getSmsCode)
    Button btnGetSmsCode;
    @BindView(R.id.changePhone_et_pwd)
    EditText etPwd;
    @BindView(R.id.changePhone_btn_ok)
    Button btnOk;

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        setBtnLeftClickFinish();
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isCanGetSmsCode()) {
                    LoginUtils.setBtnEnabled(s, etPhone, btnGetSmsCode);
                }
                LoginUtils.setBtnVisibility(s, btnOk, etSmsCode, etPwd);

            }
        });
        etSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnVisibility(s, btnOk, etPhone, etPwd);
            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnVisibility(s, btnOk, etPhone, etSmsCode);
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_changephone;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected Button getBtnGetSmsCode() {
        return btnGetSmsCode;
    }


    @Override
    protected void getSmsCodeClickListener() {
        presenter.getChangePhoneSmsCode(etPhone.getText().toString().trim());
    }

    @Override
    protected void okOnClick(EditText etPhone, EditText etSmsCode, EditText etPwd, EditText etRePwd) {
        super.okOnClick(etPhone, etSmsCode, etPwd, etRePwd);
        presenter.smsCodeBasedPhoneNumChange(etPhone.getText().toString().trim(), etSmsCode.getText().toString().trim(), MD5Utils.encodeMD5(etPwd.getText().toString().trim()));
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick({R.id.changePhone_btn_getSmsCode, R.id.changePhone_btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changePhone_btn_getSmsCode:
                smsCodeOnClick(etPhone);
                break;
            case R.id.changePhone_btn_ok:
                okOnClick(etPhone, etSmsCode, etPwd, etPwd);
                break;
        }
    }

    @Override
    public void smsCodeBasedPhoneNumChangeSuccess() {
        setResult(AboutMeFamilyFragment.ChangePhoneResultCode);
        finish();
    }
}
