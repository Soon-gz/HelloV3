package com.abings.baby.teacher.ui.class_assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.abings.baby.teacher.ui.class_assistant.search.ClassAssistantSearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by zwj on 2017/6/5.
 * 课堂助手
 */

public class ClassAssistantActivity extends BaseTitleActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{


    @BindView(R.id.classAssistant_tv_all)
    TextView tvAll;
    @BindView(R.id.classAssistant_tv_healthy)
    TextView tvHealthy;
    @BindView(R.id.classAssistant_tv_science)
    TextView tvScience;
    @BindView(R.id.classAssistant_tv_society)
    TextView tvSociety;
    @BindView(R.id.classAssistant_tv_language)
    TextView tvLanguage;
    @BindView(R.id.classAssistant_tv_art)
    TextView tvArt;
    @BindView(R.id.classAssistant_viewPager)
    ViewPager viewPager;

    protected List<Fragment> mContentList = new ArrayList<>();

    @Override
    public void showData(Object o) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_class_assistant;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(ClassAssistantActivity.this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        setTitleText("课堂助手");
        setBtnRightDrawableRes(R.drawable.main_titlebar_search);
        setTitleBackground(R.color.white);

        tvAll.setSelected(true);
        tvAll.setOnClickListener(this);
        tvHealthy.setOnClickListener(this);
        tvScience.setOnClickListener(this);
        tvSociety.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);
        tvArt.setOnClickListener(this);

        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_ALL));
        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_HEALTHY));
        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_SCIENCE));
        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_SOCIETY));
        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_LANGUAGE));
        mContentList.add(new ClassAssistantFragment(ClassAssistantFragment.TYPE_ART));

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
        };
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitleTag(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.classAssistant_tv_all) {
            //全部
            tvAll.setSelected(true);
            setTitleTag(0);
            viewPager.setCurrentItem(0);
        } else if (viewId == R.id.classAssistant_tv_healthy) {
            //
            tvHealthy.setSelected(true);
            setTitleTag(1);
            viewPager.setCurrentItem(1);
        } else if (viewId == R.id.classAssistant_tv_science) {
            //
            tvScience.setSelected(true);
            setTitleTag(2);
            viewPager.setCurrentItem(2);
        } else if (viewId == R.id.classAssistant_tv_society) {
            //
            tvSociety.setSelected(true);
            setTitleTag(3);
            viewPager.setCurrentItem(3);
        } else if (viewId == R.id.classAssistant_tv_language) {
            //活动
            tvLanguage.setSelected(true);
            setTitleTag(4);
            viewPager.setCurrentItem(4);
        } else if (viewId == R.id.classAssistant_tv_art) {
            //活动
            tvArt.setSelected(true);
            setTitleTag(5);
            viewPager.setCurrentItem(5);
        }
    }

    private void setTitleTag(int position) {
        tvAll.setSelected(false);
        tvHealthy.setSelected(false);
        tvScience.setSelected(false);
        tvSociety.setSelected(false);
        tvLanguage.setSelected(false);
        tvArt.setSelected(false);
        if (position == 0) {
            //全部
            tvAll.setSelected(true);
        } else if (position == 1) {
            //新闻
            tvHealthy.setSelected(true);
        } else if (position == 2) {
            //动态
            tvScience.setSelected(true);
        } else if (position == 3) {
            //食谱
            tvSociety.setSelected(true);
        } else if (position == 4) {
            //活动
            tvLanguage.setSelected(true);
        } else if (position == 5) {
            //活动
            tvArt.setSelected(true);
        }
    }

    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        //进入搜索界面
        Intent intent = new Intent(bContext, ClassAssistantSearchActivity.class);
        startActivity(intent);
    }
}
