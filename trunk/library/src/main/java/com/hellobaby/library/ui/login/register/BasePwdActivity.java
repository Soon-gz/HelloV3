package com.hellobaby.library.ui.login.register;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.PasswordUtils;
import com.hellobaby.library.utils.SharedPreferencesUtils;

/**
 * Created by zwj on 2016/11/3.
 * description :
 */

public abstract class BasePwdActivity<T> extends BaseLibTitleActivity<T> {
    protected Handler mHandler = new Handler();


    /**
     * 获取短信验证码的按钮
     *
     * @return
     */
    protected abstract Button getBtnGetSmsCode();

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        long smsCodeTime = (long) SharedPreferencesUtils.getParam(bContext, "smsCodeTime", 0L);
        long currentTime = System.currentTimeMillis();
        long difTimeS = 60 - (currentTime - smsCodeTime) / 1000;
        if (difTimeS < 60 && difTimeS > 0) {
            refreshCountdown((int) difTimeS);
        }
    }

    protected void refreshCountdown(int initTime) {
        getBtnGetSmsCode().setEnabled(false);
        mHandler.post(new CountDownRunnable(initTime));
    }

    /**
     * 倒计时
     */
    private class CountDownRunnable implements Runnable {
        private int mTime;

        public CountDownRunnable(int time) {
            mTime = time;
        }

        @Override
        public void run() {
            mTime--;
            boolean isFinishing = bContext.isFinishing();
            if (isFinishing) {
                return;
            }
            getBtnGetSmsCode().setText(mTime + "秒后重发");
            if (mTime > 0) {
                mHandler.postDelayed(this, 1000);
            } else {
                getBtnGetSmsCode().setEnabled(true);
                getBtnGetSmsCode().setText("重新发送");
            }
        }
    }

    /**
     * 验证码按钮的点击事件
     *
     * @param etPhone
     */
    protected void smsCodeOnClick(@NonNull EditText etPhone) {
        String phone = etPhone.getText().toString().trim();
        if (PasswordUtils.isUserNameAvailable(phone) == null) {
            // 申请服务器，开始进行倒计时
            SharedPreferencesUtils.setParam(bContext, "smsCodeTime", System.currentTimeMillis());
            refreshCountdown(60);
            getSmsCodeClickListener();
        } else {
            showToast("请输入正确的手机号");
        }
    }

    protected abstract void getSmsCodeClickListener();

    /**
     * 确认按钮的点击时间
     *
     * @param etPhone
     * @param etPwd
     * @param etRePwd
     */
    protected void okOnClick(EditText etPhone, EditText etSmsCode, EditText etPwd, EditText etRePwd) {
        String phone = etPhone.getText().toString().trim();
        String smsCode = etSmsCode.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String rePwd = etRePwd.getText().toString().trim();
        //验证两次密码
        if (!pwd.equals(rePwd)) {
            showToast("两次密码不一致");
            return;
        }
        //验证密码长度
        if (PasswordUtils.isPwdLengthRange(pwd) != null) {
            showToast("请输入6-16位的密码，以字母和数字组成");
            return;
        }
    }

    protected boolean isCanGetSmsCode() {
        String btnSmsCodeStr = getBtnGetSmsCode().getText().toString().trim();
        return "获取验证码".equals(btnSmsCodeStr) || "重新发送".equals(btnSmsCodeStr);
    }
}
