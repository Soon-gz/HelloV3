package com.abings.baby.ui.main.fm.aboutme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.aboutapp.AboutAppActivity;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.changepwd.ChangePwdActivity;
import com.abings.baby.ui.main.fm.MeMvpView;
import com.hellobaby.library.utils.GlideCacheUtil;
import com.hellobaby.library.widget.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class AboutMeSettingFragment extends BaseFragment {

    @BindView(R.id.aboutMeSetting_tbtn_public)
    ToggleButton tBtn;
    @BindView(R.id.aboutmeSetting_tv_exit)
    TextView tvExit;
    @BindView(R.id.aboutmeSetting_tv_cacheSize)
    TextView tvCacheSize;


    private MeMvpView mvpView;
    public AboutMeSettingFragment(){}
    public AboutMeSettingFragment(MeMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_aboutme_setting;
    }

    @Override
    protected void initViewsAndEvents() {
        tvCacheSize.setText("清理缓存("+GlideCacheUtil.getInstance().getCacheSize(getContext())+")");
        if (ZSApp.getInstance().getLoginUser().isPublic()) {
            tBtn.setToggleOn();
        } else {
            tBtn.setToggleOff();
        }
        tBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                mvpView.changePublicClick(on);
            }
        });
        tvCacheSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideCacheUtil.getInstance().clearImageAllCache(getContext());
                tvCacheSize.setText("清理缓存(0k)");
            }
        });
    }

    @Override
    public void showData(Object o) {
    }


    @OnClick({R.id.aboutmeSetting_tv_changepwd,R.id.about,R.id.aboutmeSetting_tv_exit})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about:
                startActivity(new Intent(getContext(), AboutAppActivity.class));
                break;
            case R.id.aboutmeSetting_tv_changepwd:
                Intent intent = new Intent(getContext(), ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.aboutmeSetting_tv_exit:
                mvpView.logoutClick();
                break;
        }

    }


}
