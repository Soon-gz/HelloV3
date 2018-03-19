package com.abings.baby.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.login.forgetpwd.ForgetPwdActivity;
import com.abings.baby.ui.login.needbaby.NeedBabyActivity;
import com.abings.baby.ui.login.register.RegisterActivity;
import com.abings.baby.ui.main.MainActivity;
import com.abings.baby.util.SharedPreferencesUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.login.BaseLoginActivity;
import com.hellobaby.library.utils.MD5Utils;
import com.hellobaby.library.utils.PermissionUtils;

import javax.inject.Inject;

import static com.hellobaby.library.utils.MD5Utils.encodeMD5;


/**
 * Created by zwj on 2016/9/23.
 * description : 登录界面
 */

public class LoginActivity extends BaseLoginActivity implements LoginMvpView {

    @Inject
    LoginPresenter presenter;


    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }


    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
//        bEtUserName.setText("18658686815");
//        bEtPwd.setText("123456");
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
//        bIvLog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                needBabyOnClick(v);
//            }
//        });
        PermissionUtils.getInstance().requestPermission(this);

        String phoneNum = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPhoneNum, "");
        String pwd = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPwd, "");
        if (phoneNum != null && !phoneNum.isEmpty() && pwd != null && !pwd.isEmpty()) {
            bEtUserName.setText(phoneNum);
            bEtPwd.setText(pwd);
            presenter.login(phoneNum, pwd);
            hintKb();
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
        if (pwd.length() != 32) {
            md5Pwd = MD5Utils.encodeMD5(pwd);
        }
        presenter.login(userName, md5Pwd);
//        presenter.getUserBabys();
//        toNeedBaby();
//        presenter.emptyLogin();
    }

    public void needBabyOnClick(View view) {
        Intent intent = new Intent(bContext, NeedBabyActivity.class);
        startActivity(intent);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void loginSuccess() {
        SharedPreferencesUtils.setParam(bContext, Const.keyPhoneNum, bEtUserName.getText().toString().trim());
        String pwd = bEtPwd.getText().toString().trim();
        if (pwd.length() != 32) {
            SharedPreferencesUtils.setParam(bContext, Const.keyPwd, MD5Utils.encodeMD5(pwd));
        }
        Intent intent = new Intent(bContext, MainActivity.class);
        startActivityDefault(intent);
        finishDefault();

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

    @Override
    public void toNeedBaby() {
        SharedPreferencesUtils.setParam(bContext, Const.keyPhoneNum, bEtUserName.getText().toString().trim());
        String pwd = bEtPwd.getText().toString().trim();
        if (pwd.length() != 32) {
            SharedPreferencesUtils.setParam(bContext, Const.keyPwd, MD5Utils.encodeMD5(pwd));
        }
        Intent intent = new Intent(bContext, NeedBabyActivity.class);
        startActivity(intent);
    }

}
