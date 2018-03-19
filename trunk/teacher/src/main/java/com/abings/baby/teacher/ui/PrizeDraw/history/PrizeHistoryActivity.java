package com.abings.baby.teacher.ui.PrizeDraw.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.history.fm.PrizeFragment;
import com.abings.baby.teacher.ui.PrizeDraw.history.fm.ScoreFragment;
import com.abings.baby.teacher.ui.base.BaseActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.data.model.TabEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PrizeHistoryActivity extends BaseActivity {


    @BindView(R.id.prize_viewpager)
    ViewPager viewPager;

    @BindView(R.id.prize_iv_center)
    CommonTabLayout ivCenter;

    public String[] mTitles = {"积分", "订单"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_prize_history;
    }

    @Override
    protected void initDaggerInject() {
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        ivCenter.setTabData(mTabEntities);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ScoreFragment());
        fragments.add(new PrizeFragment());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ivCenter.setCurrentTab(viewPager.getCurrentItem());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ivCenter.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }


    @OnClick(R.id.prize_iv_left)
    public void prizeOnClick(View view){
        switch (view.getId()){
            case R.id.prize_iv_left:
                finish();
                break;
        }
    }

    @Override
    public void showData(Object o) {

    }
}
