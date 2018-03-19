package com.abings.baby.teacher.ui.main.fm.aboutme;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.aboutapp.AboutAppActivity;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.abings.baby.teacher.ui.changepwd.ChangePwdActivity;
import com.abings.baby.teacher.ui.login.LoginActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.utils.GlideCacheUtil;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


public class AboutMeSettingFragment extends BaseFragment implements AboutMyselfMvpView{
    @Inject
    AboutMyselfPresenter presenter;
    @BindView(R.id.aboutMeSetting_tbtn_public)
    ToggleButton tBtn;
    @BindView(R.id.aboutmeSetting_tv_cacheSize)
    TextView tvCacheSize;
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
        presenter.attachView(this);
        if (ZSApp.getInstance().getTeacherModel().isPublic()) {
            tBtn.setToggleOn();
        } else {
            tBtn.setToggleOff();
        }
        tBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                ZSApp.getInstance().getTeacherModel().setIsPublic(on);
                presenter.teacherChangePublic(ZSApp.getInstance().getTeacherModel().getIsPublic());
            }
        });
        tvCacheSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideCacheUtil.getInstance().clearImageAllCache(getContext());
                tvCacheSize.setText("清理缓存(0k)");
                showMsg("缓存已全部清理");
            }
        });
    }

    @Override
    public void showData(Object o) {}

    @OnClick({R.id.aboutmeSetting_tv_changepwd,R.id.about})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.aboutmeSetting_tv_changepwd:
                Intent intent = new Intent(getContext(), ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                startActivity(new Intent(getActivity(), AboutAppActivity.class));
                break;
        }

    }

    @OnClick(R.id.aboutme_tv_logout)
    public void logoutOnClick(){
        String[] items = {"请确认是否退出","是","否"};
        BottomDialogUtils.getBottomListDialog(getActivity(), items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if(id==1){
                    presenter.logout();
                }
            }
        });
    }




    @Override
    public void logoutSuccess() {
        SharedPreferencesUtils.setParam(getActivity(), Const.keyPhoneNum,"");
        SharedPreferencesUtils.setParam(getActivity(),Const.keyPwd,"");
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
        //关闭推送
        JPushInterface.stopPush(getActivity());
    }
}
