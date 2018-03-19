package com.abings.baby.teacher.ui.main.fm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ZSApp;
import com.abings.baby.teacher.ui.Information.FavoriteActivity;
import com.abings.baby.teacher.ui.alert.AlertActivity;
import com.abings.baby.teacher.ui.base.BaseFragment;
import com.abings.baby.teacher.ui.contact.ContactsActivity;
import com.abings.baby.teacher.ui.event.EventListActivity;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMeSchoolFragment;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMeSettingFragment;
import com.abings.baby.teacher.ui.main.fm.aboutme.AboutMyselfFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.SchoolModel;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.data.model.TeacherAlertBooleanModel;
import com.hellobaby.library.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.fragmentAboutMe_rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.tab_Fragment_title)
    CommonTabLayout tabLayout;
    @BindView(R.id.mefragment_vp)
    ViewPager mViewPager;
    @BindView(R.id.aboutme_im_alert)
    ImageView ivAlert;
    @BindView(R.id.mefragment_civ_schoolHead)
    CircleImageView civSchoolHead;//校园头像

    @BindView(R.id.mefragment_tv_schoolName)
    TextView tvSchoolName;
    Badge alertBadgeView;

    public String[] mTitles = {"学校", "个人", "设置"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> mContentList = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentList.add(new AboutMeSchoolFragment());
        mContentList.add(new AboutMyselfFragment());
        mContentList.add(new AboutMeSettingFragment());
    }

    @Override
    protected void initViewsAndEvents() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if(ZSApp.getInstance().getTeacherAlertBooleanModel().getSysmsg()==0)   {
            alertBadgeView = new QBadgeView(getContext()).bindTarget(ivAlert).setBadgeNumber(-1).setGravityOffset(4f, 1f, true).setShowShadow(false);
        }  else {
            alertBadgeView = new QBadgeView(getContext()).bindTarget(ivAlert).setBadgeNumber(0).setGravityOffset(4f, 1f, true).setShowShadow(false);
        }
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tabLayout.setTabData(mTabEntities);
        initViewPager();
        {
            SchoolModel schoolModel = ZSApp.getInstance().getSchoolModel();
            tvSchoolName.setText(schoolModel.getSchoolName());
            ImageLoader.loadHeadTarget(getActivity(), schoolModel.getHeadImageurlAbs(), civSchoolHead);
        }
    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {


        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mContentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mContentList.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }
    @Override
    public void showData(Object o) {}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if(position<3){
//            tabLayout.scrollTo(position,0);
////            tabLayout.setCurrentTab(position);
//        }else{
//            tabLayout.setCurrentTab(2);
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            tabLayout.setCurrentTab(mViewPager.getCurrentItem());
        }
    }

//    @OnClick(R.id.mefragment_iv_babyHead)
//    public void onPhotoClick() {
//        Intent intent = new Intent(this.getActivity(), SinglePhotoActivity.class);
//        mHeadImageView.setDrawingCacheEnabled(true);
//        intent.putExtra("bitmap", mHeadImageView.getDrawingCache());
//        startActivityForResult(intent, 200);
//    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 200 && resultCode == -1) {
//            mHeadImageView.setImageURI(data.getData());
//        }
//        mHeadImageView.setDrawingCacheEnabled(false);
//    }
    @OnClick(R.id.aboutme_im_alert)
    public void onClick() {
        startActivityForResult(new Intent(this.getActivity(), AlertActivity.class), Const.NORMAL_ACTIVITY_RESULT);
    }

    @OnClick(R.id.aboutme_im_event)
    public void onEventClick() {
        startActivity(new Intent(this.getActivity(), EventListActivity.class));
    }
    @OnClick(R.id.aboutme_im_favorite)
    public void onFavoriteClick() {
        startActivity(new Intent(this.getActivity(), FavoriteActivity.class));
    }

    @OnClick(R.id.aboutme_im_contact)
    public void onContactClick() {
        startActivity(new Intent(this.getActivity(), ContactsActivity.class));
    }
    @Subscribe
    public void onEvent(TeacherAlertBooleanModel tAlertBooleanModel){
        if(tAlertBooleanModel.getSysmsg()==0){
            alertBadgeView.setBadgeNumber(-1);
        }else {
            alertBadgeView.setBadgeNumber(0);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
