package com.hellobaby.library.ui.aboutapp;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;


public abstract class BaseLibAboutApp<T> extends BaseLibTitleActivity<T> {

    protected TextView lib_about_version;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_lib_about_app;
    }


    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        lib_about_version = (TextView) findViewById(R.id.lib_about_version);
        setBtnLeftClickFinish();
    }

}
