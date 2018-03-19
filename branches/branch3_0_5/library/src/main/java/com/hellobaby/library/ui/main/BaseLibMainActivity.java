package com.hellobaby.library.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.widget.custom.NoScrollViewPager;

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
        } else if (id == R.id.libMain_iv_school) {
            initBottomBar();
            bIvSchool.setSelected(true);
            bViewPager.setCurrentItem(1);
        } else if (id == R.id.libMain_iv_publish) {
            showPopupMenu();
        } else if (id == R.id.libMain_iv_news) {
            initBottomBar();
            bIvNews.setSelected(true);
            bViewPager.setCurrentItem(2);
        } else if (id == R.id.libMain_iv_me) {
            initBottomBar();
            bIvMe.setSelected(true);
            bViewPager.setCurrentItem(3);
        }
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
}
