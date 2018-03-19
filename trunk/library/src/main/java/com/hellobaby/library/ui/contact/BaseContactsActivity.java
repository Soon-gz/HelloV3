package com.hellobaby.library.ui.contact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.ui.base.BaseLibActivity;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseContactsActivity extends BaseLibActivity implements ViewPager.OnPageChangeListener {

    protected List<Fragment> mContentList = new ArrayList<>();
    protected FragmentPagerAdapter mAdapter;
    protected ViewPager bViewPager;
    CommonTabLayout tabLayout;
    public String[] mTitles = {"班级", "学校"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    ImageView leftbtn;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_contacts_main;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        leftbtn = (ImageView) findViewById(R.id.libTitle_iv_left);
        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tabLayout = (CommonTabLayout) findViewById(R.id.tab_Fragment_title);
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tabLayout.setTabData(mTabEntities);
        bViewPager = (ViewPager) findViewById(R.id.libContacts_viewpager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
        };
        bViewPager.setAdapter(mAdapter);
        bViewPager.addOnPageChangeListener(this);
        tabLayout.setVisibility(View.GONE);
        mContentList.add(setClassFragment());
        boolean isTeacher = isTeacher();
        if (isTeacher) {
            tabLayout.setVisibility(View.VISIBLE);
            mContentList.add(setSchoolFragment());
        }
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                bViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
        };


        bViewPager.setAdapter(mAdapter);

        bViewPager.addOnPageChangeListener(this);
    }

    protected abstract boolean isTeacher();


    public Fragment setClassFragment() {
        return null;
    }

    public Fragment setSchoolFragment() {
        return null;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        tabLayout.scrollTo(position,0);
        tabLayout.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
