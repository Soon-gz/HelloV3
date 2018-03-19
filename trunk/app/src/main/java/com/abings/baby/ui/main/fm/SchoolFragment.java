package com.abings.baby.ui.main.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.main.fm.school.NoSchoolMvp;
import com.abings.baby.ui.main.fm.school.NoSchoolPresenter;
import com.abings.baby.ui.main.fm.school.SchoolAllFragment;
import com.abings.baby.ui.main.fm.school.SchoolCookbookFragment;
import com.abings.baby.ui.main.fm.school.SchoolDynamicFragment;
import com.abings.baby.ui.main.fm.school.SchoolEventFragment;
import com.abings.baby.ui.main.fm.school.SchoolNewsFragment;
import com.abings.baby.widget.custom.zstitlebar.IZSMainTitleBarCloseOrOpenListener;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public class SchoolFragment extends BaseMainTitleFragment implements View.OnClickListener, ViewPager.OnPageChangeListener,NoSchoolMvp {
    private LinearLayout llAll;
    private LinearLayout llNo;
    private TextView tvAll;
    private TextView tvNews;
    private TextView tvDynamic;
    private TextView tvCookbook;
    private TextView tvEvent;
    private ViewPager viewPager;
    private Button noschool;
    FragmentStatePagerAdapter mAdapter;
@Inject
    NoSchoolPresenter presenter;
    protected List<Fragment> mContentList = new ArrayList<>();
    private FragmentPagerAdapter mFragmentPagerAdapter;
    public void setMainActivityViewPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter){
        mFragmentPagerAdapter = fragmentPagerAdapter;
    }
    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected int getContentViewID() {
        return R.layout.fragment_school;
    }

    @Override
    protected void changeBaby() {
        initfrg();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initfrg();
    }

    public void initfrg() {
        mContentList.clear();
//        if (ZSApp.getInstance().getClassId().equals("0")) {
//            mContentList.add(new NoSchoolFragment());
//            if (null != llAll)
//                llAll.setVisibility(View.GONE);
//        } else {
            mContentList.clear();
            mContentList.add(0,new SchoolAllFragment());
            mContentList.add(new SchoolNewsFragment());
            mContentList.add(new SchoolDynamicFragment());
            mContentList.add(new SchoolCookbookFragment());
            mContentList.add(new SchoolEventFragment());
            if (null != llAll)
                llAll.setVisibility(View.VISIBLE);
//        }
        if(mAdapter!=null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        zsMainTitleBar.setListenerCloseOrOpen(new IZSMainTitleBarCloseOrOpenListener() {
            @Override
            public void titleBarCloseFinish() {
                if(isChangeBaby){
                    mFragmentPagerAdapter.notifyDataSetChanged();
                }
                isChangeBaby = false;
            }

            @Override
            public void titleBarOpenFinish() {

            }
        });
        llAll = (LinearLayout) flContent.findViewById(R.id.fragSchool_ll);
        llNo = (LinearLayout) flContent.findViewById(R.id.fragSchool_ll_no);
        tvAll = (TextView) flContent.findViewById(R.id.fragSchool_tv_all);
        tvNews = (TextView) flContent.findViewById(R.id.fragSchool_tv_news);
        tvDynamic = (TextView) flContent.findViewById(R.id.fragSchool_tv_dynamic);
        tvCookbook = (TextView) flContent.findViewById(R.id.fragSchool_tv_cookbook);
        tvEvent = (TextView) flContent.findViewById(R.id.fragSchool_tv_event);
        viewPager = (ViewPager) flContent.findViewById(R.id.fragSchool_viewPager);
        noschool=(Button) flContent.findViewById(R.id.NoSchool_btn);
        noschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateTBabyOnClassId();
            }
        });
        tvAll.setSelected(true);
        tvAll.setOnClickListener(this);
        tvNews.setOnClickListener(this);
        tvDynamic.setOnClickListener(this);
        tvCookbook.setOnClickListener(this);
        tvEvent.setOnClickListener(this);
        if (ZSApp.getInstance().getClassId().equals("0")){
            llAll.setVisibility(View.GONE);
            llNo.setVisibility(View.VISIBLE);
        }
        else{
            llAll.setVisibility(View.VISIBLE);
            llNo.setVisibility(View.GONE);
        }
        mAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
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

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void showData(Object o) {

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
//
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public  void onEvent(String message){
        if(message.equals("reflushSchool"))
        initfrg();
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
        EventBus.getDefault().post("reflushSchool");
    }

    //消除红点的EventBus
    @Subscribe
    public void onEvent(BadgeViewModel b) {
        if (b.getType() == 2) {
//            zsMainTitleBar.setDateBadgeViewShow(false);
            TAlertModel tAlertModel=new TAlertModel();
//            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
//            presenter.updateAlert(tAlertModel);
        } else if (b.getType() == 3) {
//            zsMainTitleBar.setMsgBadgeViewShow(false);
            TAlertModel tAlertModel=new TAlertModel();
            tAlertModel.setMsgLastMaxTime(System.currentTimeMillis());
            presenter.updateAlert(tAlertModel);
        }
    }
    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel){
        if(ZSApp.getInstance().getBabyModel().isCreator()) {
            if (tAlertBooleanModel.getEvaluation() == 0 || tAlertBooleanModel.getTeaching() == 0 || tAlertBooleanModel.getAttendance() == 0) {
                zsMainTitleBar.setDateBadgeViewShow(true);
            } else {
                zsMainTitleBar.setDateBadgeViewShow(false);
            }
        }else {
            if (tAlertBooleanModel.getEvaluation() == 0 || tAlertBooleanModel.getTeaching() == 0) {
                zsMainTitleBar.setDateBadgeViewShow(true);
            } else {
                zsMainTitleBar.setDateBadgeViewShow(false);
            }
        }
        if(tAlertBooleanModel.getMsg()==0){
            zsMainTitleBar.setMsgBadgeViewShow(true);
        }else{
            zsMainTitleBar.setMsgBadgeViewShow(false);
        }
    }
}
