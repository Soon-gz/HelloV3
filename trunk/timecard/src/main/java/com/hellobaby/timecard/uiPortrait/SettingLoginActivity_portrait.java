package com.hellobaby.timecard.uiPortrait;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.hellobaby.timecard.ui.setting.ChooseSettingActivity;
import com.hellobaby.timecard.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingLoginActivity_portrait extends BaseActivity  {

    @BindView(R.id.settingLogin_btn_login)
    Button btnLogin;
    @BindView(R.id.settingLogin_et_userName)
    EditText etUserName;
    @BindView(R.id.settingLogin_et_pwd)
    EditText etPwd;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_login_portrait;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("admin".equals(etUserName.getText().toString().trim())&&"123456".equals(etPwd.getText().toString().trim())){
                    Intent intent = new Intent(bContext, SettingActivity_portrait.class);
                    startActivity(intent);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etUserName,InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
                    finishDefault();
                }else{
                    showError("请输入正确的用户名和密码");
                }
            }
        });
    }

    @OnClick(R.id.main_iv_setting)
    public void click(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etUserName,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(etUserName.getWindowToken(), 0);
        finish();
    }

    @Override
    public void showData(Object o) {

    }
}
