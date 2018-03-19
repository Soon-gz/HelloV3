package com.abings.baby.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.InformationFragment;
import com.abings.baby.ui.main.adapter.DateExpandableListViewAdapter;
import com.abings.baby.ui.main.fm.BabyFragment;
import com.abings.baby.ui.main.fm.MeFragment;
import com.abings.baby.ui.main.fm.SchoolFragment;
import com.abings.baby.ui.publish.PublishActivity;
import com.abings.baby.ui.publishpicture.PublishPictureActivity;
import com.abings.baby.ui.publishvideo.VideoRecordActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.Const;
import com.hellobaby.library.ui.main.BaseLibMainActivity;
import com.hellobaby.library.widget.IPopupWindowMenuOnClick;
import com.hellobaby.library.widget.PopupWindowMenu;
import com.hellobaby.library.widget.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 主界面
 */
public class MainActivity extends BaseLibMainActivity implements MainMvpView {

    @Inject
    MainPresenter presenter;
    /**
     * 是否发布了视频
     */
    public static boolean isPublishVideo = false;
    public static final int mainRequestCode = 666;
    public static final int BabyFragmentRequestCode = 667;
    public static final int BabyFragmentEditResultCode = 668;
    private BabyFragment babyFragment;
    private SchoolFragment schoolFragment;

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        EventBus.getDefault().register(this);

        babyFragment.setMainActivityViewPagerAdapter(mAdapter);
        schoolFragment.setMainActivityViewPagerAdapter(mAdapter);
        presenter.attachView(this);
        //TODO 首页侧滑隐藏
//        bDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        //年 组集合
        final List<String> yearList = new ArrayList<>();//年
        yearList.add("2017");
        yearList.add("2016");
        yearList.add("2015");

        //月 子集合
        List<List<String>> monthsList = new ArrayList<>();//月
        List<String> monthList1 = new ArrayList<>();
        monthList1.add("1月");
        List<String> monthList2 = new ArrayList<>();
        monthList2.add("12月");
        monthList2.add("11月");
        monthList2.add("10月");
        List<String> monthList3 = new ArrayList<>();
        monthList3.add("7月");
        monthList3.add("6月");
        monthList3.add("5月");
        monthsList.add(monthList1);
        monthsList.add(monthList2);
        monthsList.add(monthList3);

        final ExpandableListView eListView = (ExpandableListView) findViewById(R.id.libMain_drawerLayoutMenuEListView);
        final DateExpandableListViewAdapter expandableListViewAdapter = new DateExpandableListViewAdapter(this, yearList, monthsList);
        eListView.setAdapter(expandableListViewAdapter);
        eListView.setGroupIndicator(null);//去掉前面的箭头
        expandableListViewAdapter.setOnChildClickListener(eListView, 0, 0);
        eListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //保证唯一展开
                return parent.isGroupExpanded(groupPosition);
            }
        });
        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int size = yearList.size();
                for (int i = 0; i < size; i++) {
                    if (i != groupPosition) {
                        //监听展开，关闭非当前的组
                        eListView.collapseGroup(i);
                    }
                }
            }
        });
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                expandableListViewAdapter.setOnChildClickListener(parent, groupPosition, childPosition);
                bDrawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });
    }

    @Override
    protected boolean isUnlockedBabyFragment() {
        //TODO 设置是否为开启
//        return true;
        return false;
    }


    @Override
    protected Fragment getBabyFragment() {
        babyFragment = new BabyFragment();
        return babyFragment;
    }

    @Override
    protected Fragment getSchoolFragment() {
        schoolFragment = new SchoolFragment();
        return schoolFragment;
    }

    @Override
    protected Fragment getNewsFragment() {
        return new InformationFragment();
    }

    @Override
    protected Fragment getMeFragment() {
        return new MeFragment();
    }

    @Override
    protected void setBottomMenuImage(ImageView baby, ImageView school, ImageView publish, ImageView news, ImageView me) {
        baby.setImageResource(R.drawable.selector_main_baby);
        school.setImageResource(R.drawable.selector_main_school);
//        publish.setImageResource(R.drawable.main_babynormal_icon);
        news.setImageResource(R.drawable.selector_main_news);
        me.setImageResource(R.drawable.selector_main_me);
    }

    @Override
    protected void showPopupMenu() {
        PopupWindowMenu.Item[] items =
                {new PopupWindowMenu.Item(R.drawable.ppw_video, "小视频", VideoRecordActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_camera, "相机", PublishPictureActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_diary, "日记", PublishActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_picture, "照片", PublishPictureActivity.class),
                };
        LinearLayout llMain = (LinearLayout) findViewById(R.id.activity_main);
        PopupWindowMenu popupWindowMenu = new PopupWindowMenu(bContext, items, false, bViewPager);
        popupWindowMenu.setItemOnClick(new IPopupWindowMenuOnClick() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    Intent intent = VideoRecordActivity.startRecordActivity(Const.videoPath);
                    intent.setClass(bContext, VideoRecordActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                } else if (position == 1) {
                    Intent intent = new Intent(bContext, PublishPictureActivity.class);
                    intent.putExtra("type", "相机");
                    startActivityForResult(intent, mainRequestCode);
                } else if (position == 2) {
                    Intent intent = new Intent(bContext, PublishActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                } else if (position == 3) {
                    Intent intent = new Intent(bContext, PublishPictureActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                }
            }
        });
        popupWindowMenu.showPpw(llMain);
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
    }

    @Override
    public void showData(Object o) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ZLog","requestCode："+requestCode+" ;resultCode:"+resultCode);
        if (requestCode == mainRequestCode&&(resultCode==mainRequestCode||isPublishVideo)) {
            //有数据改编后回来的展示
            isPublishVideo = false;
            bViewPager.setCurrentItem(0);
            babyFragment.requestIndexCommon();
            onClickBottomBar(bIvBaby);
        } else if (requestCode == BabyFragmentRequestCode) {
        }
        if(resultCode==BabyFragmentEditResultCode){
            onClickBottomBar(bIvBaby);
            babyFragment.requestIndexCommon();
        }
    }


//    @Override
//    public void onBackPressed() {
//        if (bDrawerLayout.isDrawerOpen(GravityCompat.END)) {
//            bDrawerLayout.closeDrawer(GravityCompat.END);
//        } else if(babyFragment.){}else{
//            long secondTime = System.currentTimeMillis();
//            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
//                ToastUtils.showNormalToast(bContext, "再按一次退出程序");
//                firstTime = secondTime;//更新firstTime
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }

    /*

        @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                ToastUtils.showNormalToast(bContext, "再按一次退出程序");
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
//                System.exit(0);//否则退出程序
                presenter.logout();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

     */

    private long firstTime = 0;

    @Override
    public void logoutSuccess() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (bDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                bDrawerLayout.closeDrawer(GravityCompat.END);
                return true;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 800) {//如果两次按键时间间隔大于800毫秒，则不退出
                    ToastUtils.showNormalToast(bContext, "再按一次退出程序");
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    presenter.logout();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(JSONArray message) {
        ToastUtils.showNormalToast(bContext, message.toJSONString());
    }
}
