package com.abings.baby.ui.main.fm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.BuildConfig;
import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.Information.FavoriteActivity;
import com.abings.baby.ui.alert.AlertActivity;
import com.abings.baby.ui.base.BaseFragment;
import com.abings.baby.ui.contact.ContactsActivity;
import com.abings.baby.ui.event.EventListActivity;
import com.abings.baby.ui.login.LoginActivity;
import com.abings.baby.ui.login.needbaby.NeedBabyActivity;
import com.abings.baby.ui.main.fm.aboutme.AboutMeBabyFragment;
import com.abings.baby.ui.main.fm.aboutme.AboutMeFamilyFragment;
import com.abings.baby.ui.main.fm.aboutme.AboutMeSettingFragment;
import com.abings.baby.ui.scan.ScanActivity;
import com.abings.baby.util.SharedPreferencesUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.JPushModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.data.model.TabEntity;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;
import com.hellobaby.library.widget.crop.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public class MeFragment extends BaseFragment implements MeMvpView, ViewPager.OnPageChangeListener {

    @Inject
    MePresenter presenter;

    @BindView(R.id.fragmentAboutMe_rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.tab_Fragment_title)
    CommonTabLayout tabLayout;
    @BindView(R.id.mefragment_vp)
    ViewPager mViewPager;
    @BindView(R.id.mefragment_iv_babyHead)
    CircleImageView mHeadImageView;
    @BindView(R.id.mefragment_tv_babyName)
    TextView tvBabyName;
    @BindView(R.id.fgme_recyclerView_otherBaby)
    RecyclerView rvOtherBaby;
    @BindView(R.id.aboutme_im_alert)
    ImageView ivAlert;
    Badge alertBadgeView;

    public String[] mTitles = {"宝宝", "家庭", "设置"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> mContentList = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private AboutMeBabyFragment meBabyFragment;
    private AboutMeFamilyFragment familyFragment;
    private AboutMeSettingFragment settingFragment;
    private BaseAdapter<BabyModel> otherBabyAdapter;
    private List<BabyModel> listBabyModels;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    public void setMainActivityViewPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter){
        mFragmentPagerAdapter = fragmentPagerAdapter;
    }

    public MeFragment() {
    }

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

        meBabyFragment = new AboutMeBabyFragment(this);
        familyFragment = new AboutMeFamilyFragment(this);
        settingFragment = new AboutMeSettingFragment(this);
        mContentList.add(meBabyFragment);
        mContentList.add(familyFragment);
        mContentList.add(settingFragment);
    }

    @Override
    protected void initViewsAndEvents() {
        presenter.attachView(this);
        ImageLoader.loadHeadTarget(getActivity(), ZSApp.getInstance().getBabyModel().getHeadImgUrlAbs(), mHeadImageView);
        tvBabyName.setText(ZSApp.getInstance().getBabyModel().getBabyName());
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tabLayout.setTabData(mTabEntities);
        initViewPager();
        initRvOtherBaby();
        EventBus.getDefault().register(this);
        if (ZSApp.getInstance().gettAlertBooleanModel() != null && ZSApp.getInstance().gettAlertBooleanModel().getSysmsg() == 0) {
            alertBadgeView = new QBadgeView(getContext()).bindTarget(ivAlert).setBadgeNumber(-1).setGravityOffset(4f, 1f, true).setShowShadow(false);
        } else {
            alertBadgeView = new QBadgeView(getContext()).bindTarget(ivAlert).setBadgeNumber(0).setGravityOffset(4f, 1f, true).setShowShadow(false);
        }
//        alertBadgeView = new QBadgeView(getContext()).bindTarget(ivAlert).setGravityOffset(4f, 1f, true);
    }

    /**
     * 其它关系人宝宝
     */
    private void initRvOtherBaby() {
        listBabyModels = new ArrayList<>();
        listBabyModels.addAll(ZSApp.getInstance().getOtherListBaby());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvOtherBaby.setLayoutManager(linearLayoutManager);
        otherBabyAdapter = new BaseAdapter<BabyModel>(getActivity(), listBabyModels, false) {
            @Override
            protected void convert(ViewHolder holder, BabyModel data) {
                CircleImageView civ = holder.getView(R.id.recyclerItem_civ);
                if (data.isEmptyHeadImgUrl()) {
                    civ.setImageResource(R.drawable.head_holder);
                } else {
                    civ.setImageResource(R.drawable.head_holder);
                    ImageLoader.loadHeadTarget(getActivity(), data.getHeadImgUrlAbs(), civ);
                }
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.recycler_item_relation;
            }
        };
        rvOtherBaby.setAdapter(otherBabyAdapter);

        otherBabyAdapter.setOnItemClickListener(new OnItemClickListeners<BabyModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, BabyModel data, int position) {
//                mHeadImageView
                BabyModel babyModel = ZSApp.getInstance().getBabyModel();
                tvBabyName.setText(data.getBabyName());
                if (data.isEmptyHeadImgUrl()) {
                    mHeadImageView.setImageResource(R.drawable.head_holder);
                } else {
                    mHeadImageView.setImageResource(R.drawable.head_holder);
                    ImageLoader.loadHeadTarget(getActivity(), data.getHeadImgUrlAbs(), mHeadImageView);
                }
                ZSApp.getInstance().setBabyModel(data);
                listBabyModels.remove(position);
                listBabyModels.add(position, babyModel);
                otherBabyAdapter.notifyItemChanged(position);
                meBabyFragment.setBabyInfo();
                getRelationsClick();
                EventBus.getDefault().post("reflushdot");
                if(mFragmentPagerAdapter!=null){
                    mFragmentPagerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        tvBabyName.setText(ZSApp.getInstance().getBabyModel().getBabyName());
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
                mViewPager.setCurrentItem(position, true);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        if(position<3){
//            tabLayout.setCurrentTab(position);
//        }else{
//            tabLayout.setCurrentTab(2);
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        if(state==ViewPager.SCROLL_STATE_SETTLING){
//            tabLayout.setCurrentTab(mViewPager.getCurrentItem());
//        }
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            tabLayout.setCurrentTab(mViewPager.getCurrentItem());
        }
    }

    @OnClick(R.id.aboutme_im_alert)
    public void onClick() {
        startActivity(new Intent(this.getActivity(), AlertActivity.class));
        BadgeViewModel badgeViewModel = new BadgeViewModel();
        badgeViewModel.setType(1);
        EventBus.getDefault().post(badgeViewModel);
    }

    @OnClick(R.id.aboutme_im_contact)
    public void onClickcontact() {
        if (ZSApp.getInstance().getBabyModel().getClassId() <= 0) {
            showToast("您的宝宝还没有班级");
            return;
        }
        Intent intent = new Intent(this.getActivity(), ContactsActivity.class);
        intent.putExtra("isTeacher", false);
        startActivity(intent);
    }

    @OnClick(R.id.aboutme_im_event)
    public void onEventClick() {
        startActivity(new Intent(this.getActivity(), EventListActivity.class));
    }

    @OnClick(R.id.aboutme_im_favorite)
    public void onfavoriteClick() {
        startActivity(new Intent(this.getActivity(), FavoriteActivity.class));
    }


    /**
     * 添加宝宝到需要宝宝界面
     */
    @OnClick(R.id.fgme_bt_add)
    public void showAddBabyPopwindow(View view) {
        Intent intent = new Intent(getContext(), NeedBabyActivity.class);
        startActivityForResult(intent, ScanActivity.kSCAN_RESULT_CODE);
    }

    @OnClick(R.id.mefragment_iv_babyHead)
    public void onPhotoClick() {
        Intent intent = new Intent(this.getActivity(), SinglePhotoActivity.class);
        intent.putExtra(SinglePhotoActivity.kAPP_ID, BuildConfig.APPLICATION_ID);
        intent.putExtra("bitmap", ZSApp.getInstance().getBabyModel().getHeadImgUrlAbs());//这里展示的是大图
        if (ZSApp.getInstance().getBabyModel().isCreator()) {
            intent.putExtra("isCreate", true);
        } else {
            intent.putExtra("isCreate", false);
        }
        startActivityForResult(intent, 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == -1) {
            String headImgPath = FileUtils.getFilePathFromUri(getContext(), data.getData());
            presenter.babyUploadHeadImg(headImgPath);
            mHeadImageView.setImageURI(data.getData());
        }

        if (resultCode == ScanActivity.kSCAN_RESULT_CODE && requestCode == ScanActivity.kSCAN_RESULT_CODE) {
            //扫描结果
            presenter.getLoginInfo();


//            String result = data.getStringExtra(ScanActivity.kSCAN_RESULT);
//            String decode =  DESUtils.decodeUrl(result);
//
////            if (result.length() < 9 && Pattern.compile("[0-9]*").matcher(result).matches()) {
//            if (decode!=null) {
//                JSONObject jsonObject = JSON.parseObject(decode);
//                String name = jsonObject.getString("name");
//                String type =jsonObject.getString("type");
//                String babyId = jsonObject.getString("babyId");
//                if(jsonObject.containsKey("babyId")){
//                    presenter.insertCareBaby(babyId);
//                }else{
//                    showToast("请扫描哈喽宝贝的二维码");
//                }
//            } else {
//                showToast("请扫描哈喽宝贝的二维码");
//            }
        }
    }

    @Subscribe
    public void onEvent(JPushModel message) {
        presenter.getLoginInfo();
        ZSApp.getInstance().setCarebaby(false);
    }

    @Override
    public void babyUpdateInfo(BabyModel babyModel) {
        presenter.babyUpdate(babyModel);
    }

    @Override
    public void userUpdateInfoClick(UserModel userModel) {
        presenter.userUpdateInfo(userModel);
    }

    @Override
    public void getRelationsClick() {
        if (ZSApp.getInstance().getBabyModel().isCreator()) {
            //该宝宝主联系人的时候，才会去展示该宝宝的关系人
            presenter.selectCareUsers();
        } else {
            //不是主创的宝宝，清楚关系
            babySetCareUsers(new ArrayList<UserModel>());
        }
    }


    @Override
    public void babySetCareUsers(List list) {
        familyFragment.setRelations(list);
    }

    @Override
    public void userUploadHeadImgClick(String path) {
        presenter.userUploadHeadImg(path);
    }

    @Override
    public void relationUpdateRelationClick(UserModel userModel) {
        presenter.userUpdateInfo(userModel);
    }

    @Override
    public void changePublicClick(boolean onOff) {
        ZSApp.getInstance().getLoginUser().setIsPublic(onOff);
        presenter.changePublic(ZSApp.getInstance().getLoginUser().getIsPublic());
    }

    @Override
    public void logoutClick() {
        String[] items = {"请确认是否退出", "是", "否"};
        BottomDialogUtils.getBottomListDialog(getActivity(), items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (id == 1) {
                    presenter.logout();
                }
            }
        });

    }

    @Override
    public void logoutSuccess() {
        SharedPreferencesUtils.setParam(getActivity(), Const.keyPhoneNum, "");
        SharedPreferencesUtils.setParam(getActivity(), Const.keyPwd, "");
        ZSApp.getInstance().setBabyModel(null);
        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
        //关闭推送
        JPushInterface.stopPush(getActivity());
    }

    @Override
    public void careBabySuccess() {
        //关注
        listBabyModels.clear();
        listBabyModels.addAll(ZSApp.getInstance().getOtherListBaby());
        otherBabyAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelCareBabyClick(final String userId, final String babyId, final boolean isBaby) {
        String[] items = {"是否取消关注", "是", "否"};
        BottomDialogUtils.getBottomListDialog(getActivity(), items, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 1) {
                    presenter.cancelCareBabyById(userId, babyId, isBaby);
                }
            }
        });

    }

    @Override
    public void cancelCareBabySuccess(String userId, String babyId, boolean isBaby) {
        if (isBaby) {
            //取消关注宝宝
            List<BabyModel> list = ZSApp.getInstance().getListBaby();


            int removePosition = -1;
            int size = list.size();

            for (int i = 0; i < size; i++) {
                if (babyId.equals(String.valueOf(list.get(i).getBabyId()))) {
                    removePosition = i;
                }
            }
            list.remove(removePosition);
            if (list.size() <= 0) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                return;
            }
            ZSApp.getInstance().setBabyModel(list.get(0));
            listBabyModels.clear();
            listBabyModels.addAll(ZSApp.getInstance().getOtherListBaby());
            otherBabyAdapter.notifyDataSetChanged();
            meBabyFragment.setBabyInfo();
            //宝宝的名字
            tvBabyName.setText(ZSApp.getInstance().getBabyModel().getBabyName());
            if (ZSApp.getInstance().getBabyModel().isEmptyHeadImgUrl()) {
//                ImageLoader.load(getActivity(),R.drawable.head_holder,mHeadImageView);
                mHeadImageView.setImageResource(R.drawable.head_holder);
            } else {
                ImageLoader.loadHeadTarget(getActivity(), ZSApp.getInstance().getBabyModel().getHeadImgUrlAbs(), mHeadImageView);
            }
        } else {
            //取消联系人
            familyFragment.refreshRelations();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void deleteHeightWeightByBabyId(String babyId) {
        presenter.deleteHeightWeightByBabyId(babyId);
    }

    @Override
    public void updatePickChange(UserModel userModel, BabyModel babyModel, boolean on) {
        presenter.updatePick(String.valueOf(userModel.getUserId()),String.valueOf(babyModel.getBabyId()),on);
    }

    //消除红点的EventBus
    @Subscribe
    public void onEvent(BadgeViewModel b) {
        if (b.getType() == 0) {
//            schoolBadgeView.setBadgeNumber(0);
            TAlertModel tAlertModel = new TAlertModel();
            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
            presenter.updateAlert(tAlertModel);
        } else if (b.getType() == 1) {
            TAlertModel tAlertModel = new TAlertModel();
            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
            alertBadgeView.setBadgeNumber(0);
        }
    }

    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel) {
        if (tAlertBooleanModel.getSysmsg() == 0) {
            alertBadgeView.setBadgeNumber(-1);
        } else {
            alertBadgeView.setBadgeNumber(0);
        }
    }
}
