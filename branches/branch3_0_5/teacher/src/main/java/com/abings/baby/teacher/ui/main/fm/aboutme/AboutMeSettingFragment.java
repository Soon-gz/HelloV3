package com.abings.baby.teacher.ui.main.fm.aboutme;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.abings.baby.teacher.ui.changepwd.ChangePwdActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class AboutMeSettingFragment extends BaseFragment implements AboutMyselfMvpView{
    @Inject
    AboutMyselfPresenter presenter;
    @BindView(R.id.aboutMeSetting_tbtn_public)
    ToggleButton tBtn;
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
    }

    @Override
    public void showData(Object o) {}

    @OnClick(R.id.aboutmeSetting_tv_changepwd)
    public void onClick(){
        Intent intent = new Intent(getContext(), ChangePwdActivity.class);
        startActivity(intent);
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
        getActivity().finish();
    }
}
