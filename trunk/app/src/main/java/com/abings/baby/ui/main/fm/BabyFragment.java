package com.abings.baby.ui.main.fm;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.adapter.HeaderAndFooterRecyclerViewAdapter;
import com.abings.baby.adapter.MyAdapter;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.util.RecyclerViewUtils;
import com.abings.baby.widget.custom.zstitlebar.IZSMainTitleBarCloseOrOpenListener;
import com.hellobaby.library.widget.IZSMainTitleScreenLightListener;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.BadgeViewModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.data.model.PubilsPicEventBusModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.data.model.TAlertModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.DeviceUtils;
import com.hellobaby.library.utils.GravitySnapHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by zwj on 2016/10/10.
 * description :
 */

public class BabyFragment extends BaseMainTitleFragment implements BabyFragmentMvpView, SwipeRefreshLayout.OnRefreshListener, IZSMainTitleScreenLightListener {
    @Inject
    BabyFragmentPresenter presenter;
    private RecyclerView recyclerView;

    PubilsPicEventBusModel temp=new PubilsPicEventBusModel();
    private PageModel currentPageNum = new PageModel();
    private List<AlbumModel> walls;
    private MyAdapter myAdapter;
    TextView more;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;
    Boolean isSlidingToLast=false;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent((((BaseLibActivity) getActivity()).getActivityComponent()), getActivity()).inject(this);
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_baby;
    }

    @Override
    protected void changeBaby() {
        //清空宝宝的首页先
        EventBus.getDefault().post("reflushSchool");
        refershIndexCommon(new ArrayList<AlbumModel>());
        if (currentPageNum == null)
            currentPageNum = new PageModel();
        currentPageNum.setPageNum(1);
        presenter.getIndexCommon();
    }

    private FragmentPagerAdapter mFragmentPagerAdapter;

    public void setMainActivityViewPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        mFragmentPagerAdapter = fragmentPagerAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 21 ) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(com.hellobaby.library.R.color.gray6c));
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        progressBar = (ProgressBar) flContent.findViewById(R.id.progress_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) flContent.findViewById(R.id.babyfg_sr);
        mSwipeRefreshLayout.setColorSchemeResources(com.hellobaby.library.R.color.colorPrimary, com.hellobaby.library.R.color.colorAccent, com.hellobaby.library.R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        zsMainTitleBar.setMainActivity(getActivity());
        zsMainTitleBar.setfromMain(true);
        zsMainTitleBar.setScreenLightListener(this);
        zsMainTitleBar.setListenerCloseOrOpen(new IZSMainTitleBarCloseOrOpenListener() {
            @Override
            public void titleBarCloseFinish() {
                if (isChangeBaby) {
                    if (mFragmentPagerAdapter != null)
                        mFragmentPagerAdapter.notifyDataSetChanged();
                }
                isChangeBaby = false;
            }

            @Override
            public void titleBarOpenFinish() {

            }
        });
        recyclerView = (RecyclerView) flContent.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        walls = new ArrayList<>();
        presenter.attachView(this);
        presenter.getIndexCommon();
        myAdapter = new MyAdapter(this.getActivity(), walls);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(myAdapter);
        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        final View moreView = getActivity().getLayoutInflater().inflate(R.layout.index_footer_more, null);
        more = (TextView) moreView.findViewById(R.id.more);
        more.getLayoutParams().height = DeviceUtils.getScreenHeight(getContext()) / 2;
        RecyclerViewUtils.setFooterView(recyclerView, moreView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);//dy用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE && walls.size() != 0 && isSlidingToLast) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    RecyclerView.ViewHolder firstViewHolder = recyclerView
                            .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
                    int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    // 判断是否滚动到底部，并且是向下
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        if (currentPageNum.getPageNum() == currentPageNum.getPages())
                            more.setText("已经加载完毕");
                        else
                            presenter.getIndexCommonPage(currentPageNum.getPageNum() + 1);
                    }
                } else {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            }
        });
    }


    @Override
    public void showData(Object o) {

    }


    @Override
    public void refershIndexCommon(List<AlbumModel> albumModels) {
        if (walls == null)
            walls = new ArrayList<>();
        walls.clear();
        walls.addAll(albumModels);
        if (myAdapter == null) {
            myAdapter = new MyAdapter(getActivity(), walls);
        }
//        walls.clear();
        if (walls != null && walls.size() <= 0) {
            String commonId = String.valueOf(Integer.MAX_VALUE);
            String content = "来自哈喽宝贝";
            AlbumModel a1 = new AlbumModel();
            a1.setUserId(commonId);
            a1.setUserName(content);
            a1.setCommonId(commonId);
            a1.setTitle("成长记录");
            a1.setContent(content);
            a1.setLastmodifyTime("2017-01-01 12:00:00");
            a1.setImageUrl("common_growth_record.png");
            a1.setImageNames("common_growth_record.png,");
            a1.setImageIds(commonId + ",");
            a1.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a1);

            AlbumModel a2 = new AlbumModel();
            a2.setUserId(commonId);
            a2.setCommonId(commonId);
            a2.setTitle("校园动态");
            a2.setUserName(content);
            a2.setContent(content);
            a2.setLastmodifyTime("2017-01-01 12:00:00");
            a2.setImageUrl("common_school_dynamics.png");
            a2.setImageNames("common_school_dynamics.png,");
            a2.setImageIds(commonId + ",");
            a2.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a2);

            AlbumModel a3 = new AlbumModel();
            a3.setUserId(commonId);
            a3.setCommonId(commonId);
            a3.setTitle("育儿经验");
            a3.setUserName(content);
            a3.setContent(content);
            a3.setLastmodifyTime("2017-01-01 12:00:00");
            a3.setImageUrl("common_parenting_information.png");
            a3.setImageNames("common_parenting_information.png,");
            a3.setImageIds(commonId + ",");
            a3.setType(AlbumModel.TYPE_ALBUM);
            walls.add(a3);
        }

        myAdapter.notifyDataSetChanged();
        if (currentPageNum == null)
            currentPageNum = new PageModel();
        currentPageNum.setPageNum(1);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        EventBus.getDefault().post("reflushdot");
    }

    @Override
    public void addIndexCommon(List<AlbumModel> albumModels, PageModel page) {
        if (walls == null)
            walls = new ArrayList<>();
        walls.addAll(albumModels);
        if (myAdapter == null)
            myAdapter = new MyAdapter(this.getActivity(), walls);
        myAdapter.notifyDataSetChanged();
        currentPageNum = page;
        if (currentPageNum.getPageNum() == currentPageNum.getPages())
            more.setText("");
    }

    @Override
    public void refershIndexDate(JSONArray jsonArray) {

    }

    @Override
    public void uploadFinish(String albumId) {
        progressBar.setVisibility(View.GONE);
        if(temp.getExistCommonId()==null||temp.getExistCommonId().equals("")) {
            presenter.setAlbumCoverByCommonId(albumId);
        }
        requestIndexCommon();
    }

    @Override
    public void uploadProgress(double text) {
        progressBar.setProgress((int) text);
    }

    @Override
    public void onRefresh() {
        if (currentPageNum == null)
            currentPageNum = new PageModel();
        currentPageNum.setPageNum(1);
        presenter.getIndexCommon();
    }

    private void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        if (brightness == -1){
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        }else {
            lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        }
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void changeScreenLight() {
        setLight(getActivity(),255);
    }

    @Override
    public void dissChangeScreenLisght() {
        setLight(getActivity(), -1);
    }

    public class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    //主界面请求刷新
    public void requestIndexCommon() {
        refershIndexCommon(new ArrayList<AlbumModel>());
        presenter.getIndexCommon();
        currentPageNum.setPageNum(1);
//        if(walls.size()!=0)
//        recyclerView.smoothScrollToPosition(1);
    }

    @Override
    public void showError(String err) {
        super.showError(err);
        if (currentPageNum.getTotal() == 0)
            more.setText("已经加载完毕");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    //消除红点的EventBus
    @Subscribe
    public void onEvent(BadgeViewModel b) {
        if (b.getType() == 2) {
//            zsMainTitleBar.setDateBadgeViewShow(false);
            TAlertModel tAlertModel = new TAlertModel();
//            tAlertModel.setSchoolLastMaxTime(System.currentTimeMillis());
//            presenter.updateAlert(tAlertModel);
        } else if (b.getType() == 3) {
//            zsMainTitleBar.setMsgBadgeViewShow(false);
            TAlertModel tAlertModel = new TAlertModel();
            tAlertModel.setMsgLastMaxTime(System.currentTimeMillis());
            presenter.updateAlert(tAlertModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEvent(TAlertBooleanModel tAlertBooleanModel) {
        if (ZSApp.getInstance().getBabyModel().isCreator()) {
            if (tAlertBooleanModel.getEvaluation() == 0 || tAlertBooleanModel.getTeaching() == 0 || tAlertBooleanModel.getAttendance() == 0) {
                zsMainTitleBar.setDateBadgeViewShow(true);
            } else {
                zsMainTitleBar.setDateBadgeViewShow(false);
            }
        } else {
            if (tAlertBooleanModel.getEvaluation() == 0 || tAlertBooleanModel.getTeaching() == 0) {
                zsMainTitleBar.setDateBadgeViewShow(true);
            } else {
                zsMainTitleBar.setDateBadgeViewShow(false);
            }
        }
        if (tAlertBooleanModel.getMsg() == 0) {
            zsMainTitleBar.setMsgBadgeViewShow(true);
        } else {
            zsMainTitleBar.setMsgBadgeViewShow(false);
        }
    }

    @Subscribe
    public void onEvent(PubilsPicEventBusModel b) {
        progressBar.setVisibility(View.VISIBLE);
        temp=b;
        presenter.createAlbum(b.getTitle(), b.getContent(), b.getImageList(), getActivity(),b.getExistCommonId(),b.getIsPublic());
    }
}
