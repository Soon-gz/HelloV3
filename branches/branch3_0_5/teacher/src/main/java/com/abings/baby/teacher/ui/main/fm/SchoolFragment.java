package com.abings.baby.teacher.ui.main.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.main.fm.school.SchoolAllFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolCookbookFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolDynamicFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolEventFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolNewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public class SchoolFragment extends BaseMainTitleFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView tvAll;
    private TextView tvNews;
    private TextView tvDynamic;
    private TextView tvCookbook;
    private TextView tvEvent;
    private ViewPager viewPager;

    protected List<Fragment> mContentList = new ArrayList<>();
    private SchoolAllFragment schoolAllFragment;

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_school;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schoolAllFragment = new SchoolAllFragment();
        mContentList.add(schoolAllFragment);
        mContentList.add(new SchoolNewsFragment());
        mContentList.add(new SchoolDynamicFragment());
        mContentList.add(new SchoolCookbookFragment());
        mContentList.add(new SchoolEventFragment());
    }

    public void refreshSchoolAll(){
        schoolAllFragment.refreshSchoolAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        tvAll = (TextView) flContent.findViewById(R.id.fragSchool_tv_all);
        tvNews = (TextView) flContent.findViewById(R.id.fragSchool_tv_news);
        tvDynamic = (TextView) flContent.findViewById(R.id.fragSchool_tv_dynamic);
        tvCookbook = (TextView) flContent.findViewById(R.id.fragSchool_tv_cookbook);
        tvEvent = (TextView) flContent.findViewById(R.id.fragSchool_tv_event);
        viewPager = (ViewPager) flContent.findViewById(R.id.fragSchool_viewPager);


        tvAll.setSelected(true);
        tvAll.setOnClickListener(this);
        tvNews.setOnClickListener(this);
        tvDynamic.setOnClickListener(this);
        tvCookbook.setOnClickListener(this);
        tvEvent.setOnClickListener(this);

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
        };


        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.fragSchool_tv_all) {
            //全部
            tvAll.setSelected(true);
            setTitleTag(0);
            viewPager.setCurrentItem(0);
        } else if (viewId == R.id.fragSchool_tv_news) {
            //新闻
            tvNews.setSelected(true);
            setTitleTag(1);
            viewPager.setCurrentItem(1);
        } else if (viewId == R.id.fragSchool_tv_dynamic) {
            //动态
            tvDynamic.setSelected(true);
            setTitleTag(2);
            viewPager.setCurrentItem(2);
        } else if (viewId == R.id.fragSchool_tv_cookbook) {
            //食谱
            tvCookbook.setSelected(true);
            setTitleTag(3);
            viewPager.setCurrentItem(3);
        } else if (viewId == R.id.fragSchool_tv_event) {
            //活动
            tvEvent.setSelected(true);
            setTitleTag(4);
            viewPager.setCurrentItem(4);
        }
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

    private void setTitleTag(int position) {
        tvAll.setSelected(false);
        tvNews.setSelected(false);
        tvDynamic.setSelected(false);
        tvCookbook.setSelected(false);
        tvEvent.setSelected(false);
        if (position == 0) {
            //全部
            tvAll.setSelected(true);
        } else if (position == 1) {
            //新闻
            tvNews.setSelected(true);
        } else if (position == 2) {
            //动态
            tvDynamic.setSelected(true);
        } else if (position == 3) {
            //食谱
            tvCookbook.setSelected(true);
        } else if (position == 4) {
            //活动
            tvEvent.setSelected(true);
        }
    }
}
