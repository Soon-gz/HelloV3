package com.abings.baby.ui.Information.infomationNew;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.abings.baby.ZSApp;
import com.abings.baby.ui.Information.InfomationChild.BaseInfoHintActivity;
import com.abings.baby.ui.Information.InfomationChild.BaseInfoPersonalMsg;
import com.abings.baby.ui.Information.InfomationChild.BaseInfomationChildFg;
import com.abings.baby.ui.Information.InfomationChild.BaseInfomationChildFg2;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.LogZS;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class BaseInfomationFragmentNew extends BaseLibFragment implements View.OnClickListener{

    protected ViewPager news_viewpager;
    protected ImageView news_search;
    protected CommonTabLayout news_iv_center;
    protected CircleImageView news_image_head;
    protected ImageView news_hint;
    Badge news_hintBadge;

    public String[] mTitles = {"关注", "发现"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    BaseInfomationChildFg childFg;
    BaseInfomationChildFg2 childFg1;
    List<BaseLibFragment> fragments;
    FragmentPagerAdapter adapter;

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_infomation_fragment_new;
    }

    @Override
    protected void initViewsAndEvents() {
        initViews();
    }

    private void initViews() {
        news_viewpager = (ViewPager) mContentView.findViewById(R.id.news_viewpager);
        news_search = (ImageView) mContentView.findViewById(R.id.news_search);
        news_image_head = (CircleImageView) mContentView.findViewById(R.id.news_image_head);
        news_iv_center = (CommonTabLayout) mContentView.findViewById(R.id.news_iv_center);
        news_hint = (ImageView) mContentView.findViewById(R.id.news_hint);
        news_hintBadge = new QBadgeView(getContext()).bindTarget(news_hint).setBadgeNumber(0).setGravityOffset(4f, 5f, true).setShowShadow(false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        news_search.setOnClickListener(this);
        news_image_head.setOnClickListener(this);
        news_hint.setOnClickListener(this);

        ImageLoader.loadHeadTarget(getContext(),Const.URL_userHead+ZSApp.getInstance().getLoginUser().getHeadImageurl(),news_image_head);

        mTabEntities.clear();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        news_iv_center.setTabData(mTabEntities);
        fragments = new ArrayList<>();
        //关注
        childFg = new BaseInfomationChildFg();
        Bundle bundle = new Bundle();
        bundle.putString("type","0");
        childFg.setArguments(bundle);
        //发现
        childFg1 = new BaseInfomationChildFg2();
        Bundle bundle1 = new Bundle();
        bundle1.putString("type","1");
        childFg1.setArguments(bundle1);

        fragments.add(childFg);
        fragments.add(childFg1);
        adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public BaseLibFragment getItem(int position) {
                return fragments.get(position);
            }
        };

        news_viewpager.setAdapter(adapter);
        news_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                news_iv_center.setCurrentTab(news_viewpager.getCurrentItem());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        news_iv_center.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                news_viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.news_search:
                startActivity(new Intent(getActivity(),InfoSearchActivity.class));
                break;
            case R.id.news_image_head:
                Intent intent = new Intent(getActivity(), BaseInfoPersonalMsg.class);
                intent.putExtra("other","false");
                intent.putExtra("state","2");
                startActivity(intent);
                break;
            case R.id.news_hint:
                startActivity(new Intent(getActivity(),BaseInfoHintActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel) {
        if (tAlertBooleanModel.getInfomsg() == 0) {
            news_hintBadge.setBadgeNumber(-1);
        } else {
            news_hintBadge.setBadgeNumber(0);
        }
    }

    /**
     * reflushInfomation 在MeFragment中进行触发
     * @param test
     */
    @Subscribe
    public void onEvent(String test) {
        if (test.equals("reflushdot")){
            fragments.clear();
            //关注
            childFg = new BaseInfomationChildFg();
            //发现
            childFg1 = new BaseInfomationChildFg2();

            fragments.add(childFg);
            fragments.add(childFg1);

            news_viewpager.setAdapter(adapter);
            news_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    news_iv_center.setCurrentTab(news_viewpager.getCurrentItem());
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            news_viewpager.setCurrentItem(0);
            news_iv_center.setCurrentTab(0);
        }
    }
}
