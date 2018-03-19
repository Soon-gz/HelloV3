package com.abings.baby.teacher.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abings.baby.teacher.BuildConfig;
import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.Information.TeacherInfomationFragmentNew;
import com.abings.baby.teacher.ui.login.LoginActivity;
import com.abings.baby.teacher.ui.main.fm.MeFragment;
import com.abings.baby.teacher.ui.main.fm.SchoolFragment;
import com.abings.baby.teacher.ui.main.fm.TeacherFragment;
import com.abings.baby.teacher.ui.msg.MsgActivity;
import com.abings.baby.teacher.ui.msg.build.MsgBuildActivity;
import com.abings.baby.teacher.ui.publishevent.PublishEventActivity;
import com.abings.baby.teacher.ui.publishpicture.PublishPictureActivity;
import com.alipay.euler.andfix.Compat;
import com.google.gson.Gson;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.PatchBean;
import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.data.model.TeacherAlertModel;
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

import javax.inject.Inject;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends BaseLibMainActivity implements MainMvpView {

    @Inject
    MainPresenter presenter;

    public static final int mainRequestCode = 777;
    private SchoolFragment schoolFragment;
    //校园红点
    Badge schoolBadgeView;
    //系统通知
    Badge settingBadgeView;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, MainActivity.this).inject(MainActivity.this);
    }

    @Override
    protected void initButterKnifeBind() {

    }


    @Override
    public void showData(Object o) {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        UpAppDialogActivity.startMustAppDialog(bContext, ZSApp.getInstance().getAppVersionModel(), AppUtils.getVersionName(bContext));
        schoolBadgeView = new QBadgeView(this).bindTarget(bIvSchool).setGravityOffset(18f, 8f, true).setShowShadow(false);
        settingBadgeView = new QBadgeView(this).bindTarget(bIvMe).setGravityOffset(18f, 8f, true).setShowShadow(false);
        presenter.attachView(this);
        presenter.insertEquipmentTeacher(AppUtils.getVersionName(bContext));

        getUmengOnLinedata();
    }

    @Override
    protected Fragment getBabyFragment() {
        return new TeacherFragment();
    }

    @Override
    protected Fragment getSchoolFragment() {
        schoolFragment = new SchoolFragment();
        return schoolFragment;
    }

    @Override
    protected Fragment getNewsFragment() {
        return new TeacherInfomationFragmentNew();
    }

    @Override
    protected Fragment getMeFragment() {
        return new MeFragment();
    }

    @Override
    protected void setBottomMenuImage(ImageView baby, ImageView school, ImageView publish, ImageView news, ImageView me) {
        baby.setImageResource(R.drawable.selector_main_teacher);
        school.setImageResource(R.drawable.selector_main_school);
//        publish.setImageResource(R.drawable.main_publish_gray);
        news.setImageResource(R.drawable.selector_main_news);
        me.setImageResource(R.drawable.selector_main_me);
    }

    @Override
    protected void showPopupMenu() {
        PopupWindowMenu.Item[] items =
                {
                        new PopupWindowMenu.Item(R.drawable.ppw_msg, "通知", MainActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_publish, "班级动态", MainActivity.class),
                        new PopupWindowMenu.Item(R.drawable.ppw_event, "活动", MainActivity.class)
                };
        LinearLayout llMain = (LinearLayout) findViewById(R.id.activity_main);
        PopupWindowMenu popupWindowMenu = new PopupWindowMenu(bContext, items, true, llMain);
        popupWindowMenu.setItemOnClick(new IPopupWindowMenuOnClick() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {

                    Intent intent = new Intent(bContext, MsgBuildActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(bContext, PublishPictureActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                } else if (position == 2) {
                    Intent intent = new Intent(bContext, PublishEventActivity.class);
                    startActivityForResult(intent, mainRequestCode);
                }
            }
        });
        popupWindowMenu.showPpw(llMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mainRequestCode && resultCode == mainRequestCode) {
            bViewPager.setCurrentItem(1);
            initBottomBar();
            bIvSchool.setSelected(true);
            schoolFragment.refreshSchoolAll();
            EventBus.getDefault().post("reflushfr");
        }
        //暂时这么解决吧
        if (requestCode == mainRequestCode && resultCode == 888){
            EventBus.getDefault().post("reflushfre");
        }
    }

    @Override
    protected boolean isUnlockedBabyFragment() {
        return false;
    }

    private long firstTime = 0;

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

    @Override
    public void logoutSuccess() {
        System.exit(0);
        finish();
    }

    @Override
    public void changedot(TeacherAlertBooleanModel teacherAlertBooleanModel) {
       if(teacherAlertBooleanModel.getSchool()==0){
           //有新消息
           schoolBadgeView.setBadgeNumber(-1);
       }else {
           schoolBadgeView.setBadgeNumber(0);
       }
        if(teacherAlertBooleanModel.getSysmsg()==0){
            //有新消息
            settingBadgeView.setBadgeNumber(-1);
        }else {
            settingBadgeView.setBadgeNumber(0);
        }
        EventBus.getDefault().post(teacherAlertBooleanModel);
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

    //消除红点的EventBus
    @Subscribe
    public void onEvent(BadgeViewModel b) {
        if (b.getType() == 0) {
            schoolBadgeView.setBadgeNumber(0);
            TeacherAlertModel tAlertModel=new TeacherAlertModel();
            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
            presenter.updateTeacherAlert(tAlertModel);
        } else if (b.getType() == 1) {
            settingBadgeView.setBadgeNumber(0);
            TeacherAlertModel tAlertModel=new TeacherAlertModel();
            tAlertModel.setSysmsgLastMaxTime(System.currentTimeMillis());
            presenter.updateTeacherAlert(tAlertModel);
        } else if (b.getType() == 2) {   //更新消息时间
            TeacherAlertModel tAlertModel=new TeacherAlertModel();
            tAlertModel.setMsgLastMaxTime(System.currentTimeMillis());
            presenter.updateTeacherAlert(tAlertModel);
            BadgeViewModel badgeViewModel=new BadgeViewModel();
        }
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
                String url = Const.getTeacherAndfixURL(bean.getUrl(),bean.getApp_v(),bean.getPath_v());
                LogZS.i("url:"+url);
                if (bean.getApp_v().equals(BuildConfig.VERSION_NAME)){
                    String fileName = Const.ANDFIX_FILE_PATH_TEACHER + Const.getTeacherAndfixFileName(bean.getApp_v(),bean.getPath_v());
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

    //刷新红点
    @Subscribe
    public void onEvent(String message) {
        if(message.equals("reflushdot")){
            presenter.selectTeacherAlert();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.selectTeacherAlert();
    }
}
