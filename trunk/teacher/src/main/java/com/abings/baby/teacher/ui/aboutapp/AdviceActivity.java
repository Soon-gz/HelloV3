package com.abings.baby.teacher.ui.aboutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.BaseModel;
import com.hellobaby.library.ui.aboutapp.BaseLibAdviceActivity;
import com.hellobaby.library.utils.PhoneUtil;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.ToastUtils;

import javax.inject.Inject;

import butterknife.OnClick;

public class AdviceActivity extends BaseLibAdviceActivity<String> {
    @OnClick(R.id.libTitle_iv_right)
    public void adviceOnClick(){
        if (isEmailOrPhone()){
            presenter.postAdvices(getValueOfView(lib_about_advice_edt),getValueOfView(lib_about_advice_phone_edt),getValueOfView(lib_about_advice_email_edt), PhoneUtil.getDeviceManufacture() +" "+PhoneUtil.getDeviceVersion()  );
        }
    }

    @Inject
    AdvicePresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void showData(String baseModel) {
        ToastUtils.showNormalToast(bContext,"意见反馈成功，谢谢您的建议，我们会及时处理。");
        finish();
    }
}
