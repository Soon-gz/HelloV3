package com.abings.baby.ui.main.fm.aboutme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.changepwd.ChangePwdActivity;
import com.abings.baby.ui.main.fm.MeMvpView;
import com.hellobaby.library.widget.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class AboutMeSettingFragment extends BaseFragment {

    @BindView(R.id.aboutMeSetting_tbtn_public)
    ToggleButton tBtn;
    @BindView(R.id.aboutmeSetting_tv_exit)
    TextView tvExit;

    private MeMvpView mvpView;

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
    }

    @Override
    public void showData(Object o) {
    }


    @OnClick(R.id.aboutmeSetting_tv_changepwd)
    public void onClick() {
        Intent intent = new Intent(getContext(), ChangePwdActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.aboutmeSetting_tv_exit)
    public void onExitClick() {
        mvpView.logoutClick();
    }


}
