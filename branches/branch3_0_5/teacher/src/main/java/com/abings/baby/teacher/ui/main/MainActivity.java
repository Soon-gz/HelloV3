package com.abings.baby.teacher.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.Information.InformationFragment;
import com.abings.baby.teacher.ui.main.fm.MeFragment;
import com.abings.baby.teacher.ui.main.fm.SchoolFragment;
import com.abings.baby.teacher.ui.main.fm.TeacherFragment;
import com.abings.baby.teacher.ui.msg.MsgActivity;
import com.abings.baby.teacher.ui.publishevent.PublishEventActivity;
import com.abings.baby.teacher.ui.publishpicture.PublishPictureActivity;
import com.hellobaby.library.ui.main.BaseLibMainActivity;
import com.hellobaby.library.widget.IPopupWindowMenuOnClick;
import com.hellobaby.library.widget.PopupWindowMenu;
import com.hellobaby.library.widget.ToastUtils;

import javax.inject.Inject;

public class MainActivity extends BaseLibMainActivity implements MainMvpView {

    @Inject
    MainPresenter presenter;

    public static final int mainRequestCode = 777;
    private SchoolFragment schoolFragment;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, MainActivity.this).inject(MainActivity.this);
    }

    @Override
    protected void initButterKnifeBind() {

    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    protected Fragment getBabyFragment() {
        return new TeacherFragment();
    }

    @Override
    protected Fragment getSchoolFragment() {
        schoolFragment = new SchoolFragment();
        return schoolFragment;
    }

    @Override
    protected Fragment getNewsFragment() {
        return new InformationFragment();
    }

    @Override
    protected Fragment getMeFragment() {
        return new MeFragment();
    }

    @Override
    protected void setBottomMenuImage(ImageView baby, ImageView school, ImageView publish, ImageView news, ImageView me) {
        baby.setImageResource(R.drawable.selector_main_teacher);
        school.setImageResource(R.drawable.selector_main_school);
        publish.setImageResource(R.drawable.main_publish);
        news.setImageResource(R.drawable.selector_main_news);
        me.setImageResource(R.drawable.selector_main_me);
    }

    @Override
    protected void showPopupMenu() {
        PopupWindowMenu.Item[] items =
                {
                        new PopupWindowMenu.Item(R.drawable.ppw_msg, "通知", MainActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_publish, "班级动态", MainActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_event, "活动", MainActivity.class)
                };
        LinearLayout llMain = (LinearLayout) findViewById(R.id.activity_main);
        PopupWindowMenu popupWindowMenu = new PopupWindowMenu(bContext, items, false, bViewPager);
        popupWindowMenu.setItemOnClick(new IPopupWindowMenuOnClick() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {

                    Intent intent = new Intent(bContext, MsgActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(bContext, PublishPictureActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                } else if (position == 2) {
                    Intent intent = new Intent(bContext, PublishEventActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                }
            }
        });
        popupWindowMenu.showPpw(llMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mainRequestCode && resultCode == mainRequestCode) {
            bViewPager.setCurrentItem(1);
            initBottomBar();
            bIvSchool.setSelected(true);
            schoolFragment.refreshSchoolAll();
        }
    }

    @Override
    protected boolean isUnlockedBabyFragment() {
        return false;
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                ToastUtils.showNormalToast(bContext, "再按一次退出程序");
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
//                System.exit(0);//否则退出程序
                presenter.logout();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void logoutSuccess() {
        finish();
    }
}
