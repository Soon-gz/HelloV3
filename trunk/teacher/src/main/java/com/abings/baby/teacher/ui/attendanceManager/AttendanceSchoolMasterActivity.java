package com.abings.baby.teacher.ui.attendanceManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.abings.baby.teacher.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AttendanceSchoolMasterActivity extends BaseLibActivity {

    @BindView(R.id.school_master_center)
    CommonTabLayout MasterCenter;
    @BindView(R.id.school_master_viewpager)
    ViewPager MasterViewpager;

    public String[] mTitles = {"我的", "全校"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    List<BaseLibFragment> fragments;
    FragmentPagerAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_attendance_school_master;
    }

    @Override
    protected void initDaggerInject() {

    }

    @OnClick(R.id.school_master_back)
    public void onCLickMaster(View view){
        finish();
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        MasterCenter.setTabData(mTabEntities);
        fragments = new ArrayList<>();

        //两个fragment
        fragments.add(SchoolMasterFragment.newInstance(SchoolMasterFragment.MIME_ATTENDANCE));
        fragments.add(SchoolMasterFragment.newInstance(SchoolMasterFragment.ALL_ATTENDANCE));

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public BaseLibFragment getItem(int position) {
                return fragments.get(position);
            }
        };

        MasterViewpager.setAdapter(adapter);
//      联动设置
        MasterViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MasterCenter.setCurrentTab(MasterViewpager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        MasterCenter.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                MasterViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    @Override
    public void showData(Object o) {

    }
}
