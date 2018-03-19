package com.abings.baby.teacher.ui.login.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hellobaby.library.ui.login.register.BaseRegisterActivity;

/**
 * Created by zwj on 2016/10/14.
 * description : 注册
 */

public class RegisterActivity extends BaseRegisterActivity {

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        setTitleText("注册");
        bBtnOk.setText("注册");
    }

    @Override
    protected void getSmsCodeClickListener() {

    }

    @Override
    public void showData(Object o) {

    }

}
