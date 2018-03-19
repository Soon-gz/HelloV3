package com.hellobaby.library.ui.login;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.PasswordUtils;
import com.hellobaby.library.utils.ScreenUtils;

/**
 * Created by zwj on 2016/11/30.
 * description : 登录
 */

public abstract class BaseLoginActivity<T> extends BaseLibActivity<T> {


    /**
     * 用户名
     */
    protected EditText bEtUserName;
    /**
     * 密码
     */
    protected EditText bEtPwd;
    /**
     * 登录按钮
     */
    protected Button bBtnLogin;
    /**
     * 忘记密码
     */
    protected TextView bTvForgetPwd;
    /**
     * 注册
     */
    protected TextView bTvRegister;
    /**
     * 上不的log
     */
    protected ImageView bIvLog;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_login;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        initView();
        initListener();
    }

    private void initListener() {
        bEtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setFocusChangeLeftDrawable(bEtUserName, hasFocus, bContext, R.drawable.login_user, R.string.login_username);
            }
        });
        bEtPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setFocusChangeLeftDrawable(bEtPwd, hasFocus, bContext, R.drawable.login_pwd, R.string.login_pwd);
            }
        });

        bEtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnVisibility(s, bEtPwd, bBtnLogin);
            }
        });
        bEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                LoginUtils.setBtnVisibility(s, bEtUserName, bBtnLogin);
            }
        });
        bBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                String userName = bEtUserName.getText().toString().trim();
                String pwd = bEtPwd.getText().toString().trim();

                String ret = PasswordUtils.isLoginAvailable(userName, pwd);
                if (ret == null) {
//                    loginPresenter.login(userName, pwd);

                    loginClickListener(userName,pwd);
                } else {
                    showToast("请输入正确的手机号和密码");
                }
            }
        });
    }

    /**
     * 登录监听
     * @param userName
     * @param pwd
     */
    protected abstract void loginClickListener(String userName, String pwd);

    private void initView() {
        bEtUserName = (EditText) findViewById(R.id.login_et_username);
        bEtPwd = (EditText) findViewById(R.id.login_et_pwd);
        bBtnLogin = (Button) findViewById(R.id.login_btn_login);
        bTvForgetPwd = (TextView) findViewById(R.id.login_btn_forgetPwd);
        bTvRegister = (TextView) findViewById(R.id.login_btn_register);
        bIvLog = (ImageView) findViewById(R.id.login_iv_log);
        setLeftDrawable(this, bEtUserName, R.drawable.login_user);
        setLeftDrawable(this, bEtPwd, R.drawable.login_pwd);
    }


    protected void setLeftDrawable(Context context, EditText et, int drawableRes) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        int width = ScreenUtils.dip2px(context, 22);
        int height = ScreenUtils.dip2px(context, 22);
        drawable.setBounds(0, 0, width, height);
        et.setCompoundDrawables(drawable, null, null, null);
        et.setCompoundDrawablePadding(ScreenUtils.dip2px(context,7f));
    }


    /**
     * 设置左侧的图片
     *
     * @param et          需要控制的EditText
     * @param isFocus     是否改变了状态
     * @param drawableRes 需要展示的图片
     */
    private void setFocusChangeLeftDrawable(EditText et, boolean isFocus, Context context, @DrawableRes int drawableRes, @StringRes int stringRes) {
        if (isFocus) {
            //获取焦点
            et.setBackgroundResource(R.drawable.et_bg_focused);
            et.setHintTextColor(getResources().getColor(R.color.transparent));
            et.setCompoundDrawables(null, null, null, null);
        } else {
            //无焦点，判断当前的是否为空
            String userName = et.getText().toString().trim();
            if (userName.isEmpty()) {
                et.setBackgroundResource(R.drawable.et_bg_default);
                et.setHint(stringRes);
                et.setHintTextColor(getResources().getColor(R.color.etHintColor));
                setLeftDrawable(context, et, drawableRes);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finishDefault();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
