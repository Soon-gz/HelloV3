package com.abings.baby.teacher.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.login.forgetpwd.ForgetPwdActivity;
import com.abings.baby.teacher.ui.login.register.RegisterActivity;
import com.abings.baby.teacher.ui.main.MainActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.login.BaseLoginActivity;
import com.hellobaby.library.utils.MD5Utils;
import com.hellobaby.library.utils.PermissionUtils;
import com.hellobaby.library.utils.SharedPreferencesUtils;

import javax.inject.Inject;

import static com.hellobaby.library.utils.MD5Utils.encodeMD5;

/**
 * Created by zwj on 2016/11/30.
 * description :登录
 */

public class LoginActivity extends BaseLoginActivity implements LoginMvpView{

    @Inject
    LoginPresenter presenter;

    @Override
    protected void initDaggerInject() {
        super.initDaggerInject();
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        bTvRegister.setVisibility(View.GONE);
        bTvRegister.setEnabled(false);
        bTvForgetPwd.setGravity(Gravity.CENTER);
        presenter.attachView(this);
//        bEtUserName.setText("18989619203");
//        bEtPwd.setText("123123");

        bTvForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忘记密码
                startActivity(new Intent(bContext, ForgetPwdActivity.class));
            }
        });
        bTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                startActivity(new Intent(bContext, RegisterActivity.class));
            }
        });
        PermissionUtils.getInstance().requestPermission(this);
        String phoneNum = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPhoneNum, "");
        String pwd = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPwd, "");
        if (phoneNum != null && !phoneNum.isEmpty() && pwd != null && !pwd.isEmpty()) {
            bEtUserName.setText(phoneNum);
            bEtPwd.setText(pwd);
            presenter.loginTeacher(phoneNum, pwd);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
            //成功
        } else {
            showToast("获取权限失败");//会对您的使用造成不便
        }
    }

    @Override
    protected void loginClickListener(String userName, String pwd) {
        String md5Pwd = pwd;
        if(md5Pwd.length()!=32){
            md5Pwd = MD5Utils.encodeMD5(pwd);
        }
        presenter.loginTeacher(userName,md5Pwd);
//        presenter.selectSchoolByTeacherId();
//        presenter.emptyLogin();
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    public void toMain() {
        String pwd = bEtPwd.getText().toString().trim();
        if(pwd.length()!=32){
            pwd = MD5Utils.encodeMD5(pwd);
        }
        SharedPreferencesUtils.setParam(bContext, Const.keyPhoneNum, bEtUserName.getText().toString().trim());
        SharedPreferencesUtils.setParam(bContext, Const.keyPwd, pwd);
        hintKb();
        startActivity(new Intent(bContext, MainActivity.class));
        finish();
    }
    // 此方法只是关闭软键盘
    protected void hintKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
