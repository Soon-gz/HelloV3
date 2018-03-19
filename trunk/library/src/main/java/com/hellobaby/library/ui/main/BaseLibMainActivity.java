package com.hellobaby.library.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.widget.custom.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/11/21.
 * description : 主类的基础类
 */

public abstract class BaseLibMainActivity<T> extends BaseLibActivity<T> implements ViewPager.OnPageChangeListener, View.OnClickListener {

    protected NoScrollViewPager bViewPager;
    protected ImageView bIvBaby;//第一个选项
    protected ImageView bIvSchool;//校园
    protected ImageView bIvPublish;//发布
    protected ImageView bIvNews;
    protected ImageView bIvMe;
    protected List<Fragment> mContentList = new ArrayList<>();
    protected FragmentPagerAdapter mAdapter;
    protected DrawerLayout bDrawerLayout;
    //是否在咨询界面
    protected boolean isInNewsFrgment = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_main;
    }

    protected abstract Fragment getBabyFragment();

    protected abstract Fragment getSchoolFragment();

    protected abstract Fragment getNewsFragment();

    protected abstract Fragment getMeFragment();

    protected abstract void setBottomMenuImage(ImageView baby, ImageView school, ImageView publish, ImageView news, ImageView me);

    protected abstract void showPopupMenu();

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        bViewPager = (NoScrollViewPager) findViewById(R.id.libMain_viewpager);
        bViewPager.setOffscreenPageLimit(4);
        EventBus.getDefault().register(this);
        bIvBaby = (ImageView) findViewById(R.id.libMain_iv_baby);
        bIvSchool = (ImageView) findViewById(R.id.libMain_iv_school);
        bIvPublish = (ImageView) findViewById(R.id.libMain_iv_publish);
        bIvNews = (ImageView) findViewById(R.id.libMain_iv_news);
        bIvMe = (ImageView) findViewById(R.id.libMain_iv_me);

        setBottomMenuImage(bIvBaby, bIvSchool, bIvPublish, bIvNews, bIvMe);

        bIvBaby.setOnClickListener(this);
        bIvSchool.setOnClickListener(this);
        bIvPublish.setOnClickListener(this);
        bIvNews.setOnClickListener(this);
        bIvMe.setOnClickListener(this);
        bIvBaby.setSelected(true);
        initViewPager();
        bDrawerLayout = (DrawerLayout) findViewById(R.id.libMain_drawerLayout);
        bDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    /**
     * 初始化ViewPager
     */
    protected void initViewPager() {
        mContentList.add(getBabyFragment());
        mContentList.add(getSchoolFragment());
        mContentList.add(getNewsFragment());
        mContentList.add(getMeFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
            //在 PagerAdapter 中的实现是直接传回 POSITION_UNCHANGED。
            // 如果该函数不被重载，则会一直返回 POSITION_UNCHANGED，从而导致 ViewPager .dataSetChanged() 被调用时，
            // 认为不必触发 PagerAdapter.instantiateItem()。很多人因为没有重载该函数，
            // 而导致调用PagerAdapter.notifyDataSetChanged() 后，什么都没有发生。
            @Override
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }
        };


        bViewPager.setAdapter(mAdapter);

        bViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        onClickBottomBar(v);
    }

    protected void onClickBottomBar(View view) {
        int id = view.getId();
        bDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (id == R.id.libMain_iv_baby) {
            initBottomBar();
            bViewPager.setCurrentItem(0);
            bIvBaby.setSelected(true);
            if(isUnlockedBabyFragment()){
                bDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            isInNewsFrgment = false;
            //切换页面时触发，隐藏图标
            EventBus.getDefault().post(Const.SELECT_OTHER_PAGES);
        } else if (id == R.id.libMain_iv_school) {
            initBottomBar();
            bIvSchool.setSelected(true);
            bViewPager.setCurrentItem(1);
            BadgeViewModel b=new BadgeViewModel();
            b.setType(0);
            EventBus.getDefault().post(b);
            //切换页面时触发，校园界面校长端需要多一个功能
            EventBus.getDefault().post(Const.SELECT_SCHOOL);
            isInNewsFrgment = false;
        } else if (id == R.id.libMain_iv_publish) {
            showPopupMenu();
        } else if (id == R.id.libMain_iv_news) {
            initBottomBar();
            if (isInNewsFrgment){
                refreshAllData();
            }
            isInNewsFrgment = true;
            bIvNews.setSelected(true);
            bViewPager.setCurrentItem(2);
            //切换页面时触发，隐藏图标
            EventBus.getDefault().post(Const.SELECT_OTHER_PAGES);
        } else if (id == R.id.libMain_iv_me) {
            initBottomBar();
            bIvMe.setSelected(true);
            bViewPager.setCurrentItem(3);
            isInNewsFrgment = false;
            //切换页面时触发，隐藏图标
            EventBus.getDefault().post(Const.SELECT_OTHER_PAGES);
//            BadgeViewModel b=new BadgeViewModel();
//            b.setType(1);
//            EventBus.getDefault().post(b);
        }
    }

    protected void refreshAllData() {
    }

    protected void initBottomBar(){
        bIvBaby.setSelected(false);
        bIvSchool.setSelected(false);
        bIvNews.setSelected(false);
        bIvMe.setSelected(false);
    }
    /**
     * 是否解锁BabyFragment
     */
    protected abstract boolean isUnlockedBabyFragment();

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //教师红点待定
    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel){
    }
}
