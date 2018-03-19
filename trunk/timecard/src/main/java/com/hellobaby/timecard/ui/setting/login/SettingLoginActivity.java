package com.hellobaby.timecard.ui.setting.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hellobaby.timecard.R;
import com.hellobaby.timecard.ui.base.BaseActivity;
import com.hellobaby.timecard.ui.setting.ChooseSettingActivity;
import com.hellobaby.timecard.ui.setting.SettingActivity;

/**
 * Created by zwj on 2017/3/13.
 * description :
 */

public class SettingLoginActivity extends BaseActivity {
    private ImageView ivBack;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPwd;

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting_login;
    }

    @Override
    protected void initDaggerInject() {
getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        ivBack = (ImageView) findViewById(R.id.settingLogin_iv_back);
        btnLogin = (Button) findViewById(R.id.settingLogin_btn_login);
        etUserName = (EditText) findViewById(R.id.settingLogin_et_userName);
        etPwd = (EditText) findViewById(R.id.settingLogin_et_pwd);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTCMDialog("返回首页", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("admin".equals(etUserName.getText().toString().trim())&&"123456".equals(etPwd.getText().toString().trim())){
                    Intent intent = new Intent(bContext, ChooseSettingActivity.class);
                    startActivity(intent);
                    finishDefault();
                }else{
                    showError("请输入正确的用户名和密码");
                }
            }
        });
    }
}
