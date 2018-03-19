package com.abings.baby.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.login.forgetpwd.ForgetPwdActivity;
import com.abings.baby.ui.login.needbaby.NeedBabyActivity;
import com.abings.baby.ui.login.register.RegisterActivity;
import com.abings.baby.ui.main.MainActivity;
import com.abings.baby.util.SharedPreferencesUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.AppVersionModel;
import com.hellobaby.library.ui.login.BaseLoginActivity;
import com.hellobaby.library.ui.upapp.UpAppDialogActivity;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.MD5Utils;
import com.hellobaby.library.utils.PermissionUtils;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;


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
        ZSApp.getInstance().setLoginUser(null);
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
//            hintKb();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            bEtUserName.setText(phoneNum);
            bEtPwd.setText(pwd);
            if (getIntent().getBooleanExtra("isErr", false)) {
                return;
            }
            presenter.login(phoneNum, pwd);
        } else {
            //非自动登录
            UpAppDialogActivity.startMustAppDialog(bContext, ZSApp.getInstance().getAppVersionModel(), AppUtils.getVersionName(bContext));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this)) {
            //成功
        } else {
            showError("获取权限失败");//会对您的使用造成不便
        }
    }

    @Override
    protected void loginClickListener(String userName, String pwd) {
        String md5Pwd = pwd;
        if (pwd.length() != 32) {
            md5Pwd = MD5Utils.encodeMD5(pwd);
        }
        presenter.login(userName, md5Pwd);
        //退出登录之后，切换账号。重新开启推送通知
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this);
        }
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

    @Override
    public void toUpdate(AppVersionModel model) {

    }


}
