package com.abings.baby.ui.main;

import android.content.Intent;
import android.os.Build;
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

import com.abings.baby.BuildConfig;
import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.InformationFragment;
import com.abings.baby.ui.Information.infomationNew.BaseInfomationFragmentNew;
import com.abings.baby.ui.carebaby.CareBabySureActivity;
import com.abings.baby.ui.main.adapter.DateExpandableListViewAdapter;
import com.abings.baby.ui.main.fm.BabyFragment;
import com.abings.baby.ui.main.fm.MeFragment;
import com.abings.baby.ui.main.fm.SchoolFragment;
import com.abings.baby.ui.publish.PublishActivity;
import com.abings.baby.ui.publishpicture.PublishPictureActivity;
import com.abings.baby.ui.publishvideo.PublishVideoActivity;
import com.abings.baby.ui.publishvideo.VideoRecordActivity;
import com.alibaba.fastjson.JSONArray;
import com.alipay.euler.andfix.Compat;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.BaseInfoDetailEventModel;
import com.hellobaby.library.data.model.JPushModel;
import com.hellobaby.library.data.model.PatchBean;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.ui.main.BaseLibMainActivity;
import com.hellobaby.library.ui.upapp.UpAppDialogActivity;
import com.hellobaby.library.utils.AppUtils;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.IPopupWindowMenuOnClick;
import com.hellobaby.library.widget.PopupWindowMenu;
import com.hellobaby.library.widget.ToastUtils;
import com.umeng.onlineconfig.OnlineConfigAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
    private MeFragment meFragment;
    //校园红点
    Badge schoolBadgeView;
    //系统通知
    Badge settingBadgeView;

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        UpAppDialogActivity.startMustAppDialog(bContext, ZSApp.getInstance().getAppVersionModel(), AppUtils.getVersionName(bContext));
        schoolBadgeView = new QBadgeView(this).bindTarget(bIvSchool).setGravityOffset(18f, 8f, true).setShowShadow(false);
        settingBadgeView = new QBadgeView(this).bindTarget(bIvMe).setGravityOffset(18f, 8f, true).setShowShadow(false);
        babyFragment.setMainActivityViewPagerAdapter(mAdapter);
        schoolFragment.setMainActivityViewPagerAdapter(mAdapter);
        meFragment.setMainActivityViewPagerAdapter(mAdapter);
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

        //读取服务器未处理通知   2017-2-23 sw
        presenter.sendNotificationWithout(ZSApp.getInstance().getUserId());
        presenter.insertEquipmentUser(AppUtils.getVersionName(bContext));

        //通过友盟在线参数  读取是否需要下载修复包
        getUmengOnLinedata();

//        //初始化红点
//        if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getSchool()==0) {
//            schoolBadgeView = new QBadgeView(this).bindTarget(bIvSchool).setBadgeNumber(-1).setGravityOffset(18f, 8f, true);
//        }else {
//            schoolBadgeView = new QBadgeView(this).bindTarget(bIvSchool).setBadgeNumber(0).setGravityOffset(18f, 8f, true);
//        }
//        if(ZSApp.getInstance().gettAlertBooleanModel()!=null&&ZSApp.getInstance().gettAlertBooleanModel().getSysmsg()==0) {
//            settingBadgeView = new QBadgeView(this).bindTarget(bIvMe).setBadgeNumber(-1).setGravityOffset(18f, 8f, true);
//        } else {
//            settingBadgeView = new QBadgeView(this).bindTarget(bIvMe).setBadgeNumber(0).setGravityOffset(18f, 8f, true);
//        }

    }

    /**
     * 作者：ShuWen
     * 日期：2017/12/11  12:11
     * 描述：友盟在线参数获取，用于更新，热更新工具 ：使用 apktool 生成patch修复文件
     *
     */

    public void getUmengOnLinedata(){
        String onLineInfo = OnlineConfigAgent.getInstance().getConfigParams(this, Const.ANDFIX_UMENG_ONLINE_DATA);
        LogZS.i("参数："+onLineInfo);
        if (onLineInfo != null && !onLineInfo.equals("")){
            PatchBean bean = new Gson().fromJson(onLineInfo,PatchBean.class);
            if(bean.isIsNeedUpdate()){
                //http://hellobaobei.oss-cn-shanghai.aliyuncs.com/static/andfix/1.0_1.0_baby.patch
                String url = Const.getBabyAndfixURL(bean.getUrl(),bean.getApp_v(),bean.getPath_v());
                LogZS.i("url:"+url);
                if (bean.getApp_v().equals(BuildConfig.VERSION_NAME)){
                    String fileName = Const.ANDFIX_FILE_PATH + Const.getBabyAndfixFileName(bean.getApp_v(),bean.getPath_v());
                    LogZS.i("fileName:"+fileName);
                    File file = new File(fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    presenter.downloadPatch(url,fileName);
                }else{
                    LogZS.i("当前版本"+BuildConfig.VERSION_NAME+"暂无热更新需求！");
                }
            }else{
                LogZS.i("暂无热更新需求！");
            }
        }else{
            LogZS.i("没有在线参数");
        }
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
        return new BaseInfomationFragmentNew();
    }

    @Override
    protected Fragment getMeFragment() {
        meFragment = new MeFragment();
        return meFragment;
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
    protected void onResume() {
        super.onResume();
        ZSApp.isAtMainActivity = true;
        if (ZSApp.getInstance().isCarebaby()) {
            ZSApp.getInstance().setCarebaby(false);
            presenter.getLoginInfo();
        }
    }

    @Override
    protected void refreshAllData() {
        EventBus.getDefault().post(new BaseInfoDetailEventModel(""));
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
        PopupWindowMenu popupWindowMenu = new PopupWindowMenu(bContext, items, true, llMain);
        popupWindowMenu.setItemOnClick(new IPopupWindowMenuOnClick() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
//                    Intent intent = VideoRecordActivity.startRecordActivity(Const.videoPath);
//                    intent.setClass(bContext, VideoRecordActivity.class);
                    Intent intent = new Intent(bContext, PublishVideoActivity.class);
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
    protected void onPause() {
        super.onPause();
        ZSApp.isAtMainActivity = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ZLLog","  MainActivity   onActivityResult  requestCode=>"+requestCode+" ;resultCode=>"+resultCode);
        if (requestCode == mainRequestCode && (resultCode == mainRequestCode || isPublishVideo)) {
            //有数据改编后回来的展示
            isPublishVideo = false;
            bViewPager.setCurrentItem(0);
            babyFragment.requestIndexCommon();
            onClickBottomBar(bIvBaby);
            if(null!=data&&data.hasExtra("PubilsPicEventBusModel")) {
                EventBus.getDefault().post(data.getSerializableExtra("PubilsPicEventBusModel"));
            }
        } else if (requestCode == BabyFragmentRequestCode) {
            babyFragment.requestIndexCommon();
        }
        if (resultCode == BabyFragmentEditResultCode) {
//            onClickBottomBar(bIvBaby);
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
    public void showBadgeView(TAlertBooleanModel tAlertBooleanModel) {
        EventBus.getDefault().post(tAlertBooleanModel);
        if (tAlertBooleanModel.getSchool() == 0) {
            schoolBadgeView.setBadgeNumber(-1);
        } else {
            schoolBadgeView.setBadgeNumber(0);
        }
        if (tAlertBooleanModel.getSysmsg() == 0) {
            settingBadgeView.setBadgeNumber(-1);
        } else {
            settingBadgeView.setBadgeNumber(0);
        }
    }

    @Override
    public void download(String fileName) {
        if (Compat.isSupport() && isSupportAndfix()){
            try {
                ZSApp.getInstance().getPatchManager().addPatch(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 作者：ShuWen
     * 日期：2017/12/11  14:54
     * 描述：判断版本是否支持热更新   从3.4.2开始使用热更新 andfix
     *
     */
    private boolean isSupportAndfix() {
        String[] startVersion = Const.ANDFIX_START_VERSION.split("\\.");
        String[] currentVersion = BuildConfig.VERSION_NAME.split("\\.");
        boolean isSupport = false;
        for (int i = 0; i < startVersion.length; i++) {
            int start_version = Integer.parseInt(startVersion[i]);
            int current_version = Integer.parseInt(currentVersion[i]);
            if (current_version > start_version){
                isSupport = true;
                break;
            }
        }
        return isSupport;
    }

    @Override
    public void showBottomDialog(List list) {
        if (list != null && list.size() > 0) {
            ZSApp.getInstance().setServerCarebabyCaches(list);
            startBottomActivity(new Intent(this, CareBabySureActivity.class));
        }
    }

    public void startBottomActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(com.hellobaby.library.R.anim.activity_bottom_in, com.hellobaby.library.R.anim.activity_bottom_out);
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

    @Subscribe
    public void onEvent(JPushModel message) {
        if (bViewPager.getCurrentItem() == 0 || bViewPager.getCurrentItem() == 1) {
            presenter.getLoginInfo();
            ZSApp.getInstance().setCarebaby(false);
        }
    }

    //消除红点的EventBus
    @Subscribe
    public void onEvent(BadgeViewModel b) {
        if (b.getType() == 0) {
            schoolBadgeView.setBadgeNumber(0);
            TAlertModel tAlertModel = new TAlertModel();
            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
            presenter.updateAlert(tAlertModel);
        } else if (b.getType() == 1) {
            TAlertModel tAlertModel = new TAlertModel();
            tAlertModel.setSysmsgLastMaxTime(System.currentTimeMillis());
            settingBadgeView.setBadgeNumber(0);
            presenter.updateAlert(tAlertModel);
        }
    }

    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel) {
        if (tAlertBooleanModel.getSchool() == 0) {
            schoolBadgeView.setBadgeNumber(-1);
        } else {
            schoolBadgeView.setBadgeNumber(0);
        }
        if (tAlertBooleanModel.getSysmsg() == 0) {
            settingBadgeView.setBadgeNumber(-1);
        } else {
            settingBadgeView.setBadgeNumber(0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.selectAlert();
    }

    //刷新红点
    @Subscribe
    public void onEvent(String message) {
        if (message.equals("reflushdot")) {
            presenter.selectAlert();
        }
    }

}
