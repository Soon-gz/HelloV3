package com.abings.baby.ui.aboutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.abings.baby.R;
import com.hellobaby.library.ui.aboutapp.BaseLibAboutApp;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import butterknife.OnClick;

public class AboutAppActivity extends BaseLibAboutApp {

    @OnClick({R.id.lib_about_app_userservice,R.id.lib_about_app_tofriends,
            R.id.lib_about_app_advices,R.id.lib_about_app_clientserver,
            R.id.lib_about_app_weixinclient})
    public void aboutOnClick(View view){
        switch (view.getId()){
            case R.id.lib_about_app_userservice:
                Intent intent = new Intent(this, BaseWebViewActivity.class);
                intent.putExtra(BaseWebViewActivity.kWebUrl,"http://www.hellobaobei.com.cn/protocol.html");
                intent.putExtra(BaseWebViewActivity.isHasRightBtn,"yes");
                startActivity(intent);
                break;
            case R.id.lib_about_app_tofriends:
                ShareBottomDialog.getShareUrlBottomDialog(bContext, R.drawable.logo_share, "http://a.app.qq.com/o/simple.jsp?pkgname=com.abings.baby", "来自好友推荐,一款好用的不能再好用的app。", "哈喽宝贝家长端");
//                ShareBottomDialog.getShareUrlWithTargetBottomDialog(bContext,null,"哈喽宝贝家长端","来自好友推荐,一款好用的不能再好用的app。",new UMImage(bContext,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487048492587&di=1114122594ba391f17e748499d8b703a&imgtype=0&src=http%3A%2F%2Fwww.ld12.com%2Fupimg358%2Fallimg%2Fc140918%2F14109D12OT30-251H1.jpg"),"http://a.app.qq.com/o/simple.jsp?pkgname=com.abings.baby","http://a.app.qq.com/o/simple.jsp?pkgname=com.abings.baby");
                break;
            case R.id.lib_about_app_advices:
                startActivity(new Intent(this,AdviceActivity.class));
                break;
            case R.id.lib_about_app_clientserver:
                break;
            case R.id.lib_about_app_weixinclient:
                break;
        }
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);

        String version = AppUtils.getVersionName(this);
        lib_about_version.setText(version);

    }

    @Override
    public void showData(Object o) {

    }
}
