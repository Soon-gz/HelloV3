package com.hellobaby.library.ui.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.ui.base.BaseLibActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/11/28.
 * description : 首页消息中心
 */

public abstract class BaseMsgActivity<T> extends BaseLibActivity<T> {

    /**
     * 标题外壳
     */
    private RelativeLayout flTitle;
    /**
     * 左边按钮
     */
    private ImageView ivLeft;
    /**
     * 中间空间
     */
    private CommonTabLayout ivCenter;
    public String[] mTitles = {"收件箱", "发件箱"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    /**
     * 右边按钮
     */
    private ImageView ivRight;
    private ViewPager viewPager;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_basemsg;
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        flTitle = (RelativeLayout) findViewById(R.id.baseMsg_rl_title);
        ivLeft = (ImageView) findViewById(R.id.baseMsg_iv_left);
        ivCenter = (CommonTabLayout) findViewById(R.id.baseMsg_iv_center);
        ivRight = (ImageView)findViewById(R.id.baseMsg_iv_right);
        viewPager = (ViewPager) findViewById(R.id.baseMsg_viewpager);
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        ivCenter.setTabData(mTabEntities);
       final List<Fragment> fragments = new ArrayList<>();
        fragments.add(getInBox());
        fragments.add(getOutBox());
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
                ivCenter.setCurrentTab(position);
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
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildNewMsg();
            }
        });
    }

    /**
     * 发件箱
     * @return
     */
    protected abstract Fragment getOutBox();

    /**
     * 收件箱
     * @return
     */
    protected abstract Fragment getInBox();

    /**
     * 新建邮件
     * @return
     */
    protected abstract void buildNewMsg();

}
