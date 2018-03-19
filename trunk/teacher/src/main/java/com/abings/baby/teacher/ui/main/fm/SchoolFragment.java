package com.abings.baby.teacher.ui.main.fm;

import android.content.Intent;
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
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.main.fm.school.SchoolAllFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolCookbookFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolDynamicFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolEventFragment;
import com.abings.baby.teacher.ui.main.fm.school.SchoolMasterChooseActivity;
import com.abings.baby.teacher.ui.main.fm.school.SchoolNewsFragment;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.EventBusObject;
import com.hellobaby.library.data.model.TeacherAlertModel;
import com.hellobaby.library.utils.LogZS;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private String classesIDS = "";

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
//        schoolAllFragment.refreshSchoolAll();
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
        selectSchool.setOnClickListener(this);

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


        viewPager.setOffscreenPageLimit(5);
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
        }else if (viewId == R.id.mainTitle_iv_select_school){
            startActivityForResult(new Intent(getActivity(), SchoolMasterChooseActivity.class).putExtra(SchoolMasterChooseActivity.CLASS_IDS,classesIDS),SchoolMasterChooseActivity.REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SchoolMasterChooseActivity.REQUEST_CODE && resultCode == SchoolMasterChooseActivity.RESULT_CODE){
            String result = data.getStringExtra(SchoolMasterChooseActivity.RESULT_DATA);
            LogZS.i("result："+result);
            if (!result.equals(SchoolMasterChooseActivity.RESULT_DATA)){
                classesIDS = result;
            }else {
                classesIDS = "";
            }
            EventBusObject busObject = new EventBusObject();
            busObject.setFromWhere(SchoolMasterChooseActivity.class.getSimpleName());
            busObject.setToWhere(SchoolMasterChooseActivity.TO_WHERE);
            busObject.setMsg(result);
            EventBus.getDefault().post(busObject);
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


    /**
     * 作者：ShuWen
     * 日期：2017/12/28  9:45
     * 描述：校长端需要在校园界面可选择班级查看动态，这里需要一个图标，由于
     *      标题栏是共享的，所以需要在选择的时候BaseLibMainActivity选择了界面时
     *      发出通知，让schoolFragment进行更新UI
     * @return [返回类型说明]
     */
    @Subscribe
    public void onEvent(String select) {
        if (select.equals("selectSchool") && ZSApp.getInstance().isSchoolMaster()){
            selectSchool.setVisibility(View.VISIBLE);
        }
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
