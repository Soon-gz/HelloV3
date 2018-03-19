package com.abings.baby.ui.Information.InfomationChild;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.Information.infomationNew.BaseInfoNewsAdapter;
import com.abings.baby.ui.Information.infomationNew.InfomationNewPresenter;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.BaseInfoDetailEventModel;
import com.hellobaby.library.data.model.InfoChildHeartModel;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.TAlertBooleanModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BaseInfomationChildFg extends BaseLibFragment implements InfomationNewMvp{

    protected RecyclerView lib_baseinfomation_child;
    protected BaseInfoNewsAdapter baseAdapter;
    protected List<InfomationModel> modelList;

//    private String type = "1";
    private int pageNum = 1;
    private boolean isLastPage = false;
    private boolean needClearData = true;
    private boolean isNeedScroll = false;
    private boolean isLoading = false;

    @BindView(R.id.lib_baseinfomation_school_swipeRefresh)
    SwipeRefreshLayout lib_baseinfomation_school_swipeRefresh;

    @Inject
    InfomationNewPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_infomation_child_fg;
    }

    @Override
    protected void initViewsAndEvents() {
//        type = getArguments().getString("type");
        presenter.attachView(this);
        initViews();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    //接收用户删除咨询，用于更新当前数据
    @Subscribe
    public void onEventMainThread(BaseInfoDetailEventModel event) {
        if(!needClearData){
            pageNum = 1;
            needClearData = true;
            isLastPage = false;
            init();
        }
    }

    @Subscribe
    public void onEventMainThread(InfoChildHeartModel event) {
        if (event.isLikeClick()){
            modelList.get(event.getPosition()).setIsLike(event.getIsLike());
            modelList.get(event.getPosition()).setLikeNum(event.getLikeNum()+"");
        }else {
            modelList.get(event.getPosition()).setCommentNum(event.getCommentNum());
        }
        baseAdapter.notifyDataSetChanged();
    }


    private void initViews() {
        modelList = new ArrayList<>();
        lib_baseinfomation_child = (RecyclerView) mContentView.findViewById(R.id.lib_baseinfomation_child);
        pageNum = 1;
        isLastPage = false;
        baseAdapter = new BaseInfoNewsAdapter(getContext(), modelList, true) {
            @Override
            protected void disLikeInfoMsg(InfomationModel data) {
                if (data.getState() == 1){
                    presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                }else {
                    presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                }
            }

            @Override
            protected void addLikeInfoMsg(InfomationModel data) {
                if (data.getState() == 1){
                    presenter.addLikeInfo(data.getState()+"",data.getInfoId()+"");
                }else {
                    presenter.addLikeInfo(data.getState()+"",data.getSubAlbumId()+"");
                }
            }
        };

        baseAdapter.setLoadingView(R.layout.footer_more);

        lib_baseinfomation_child.setLayoutManager(new LinearLayoutManager(getActivity()));
        lib_baseinfomation_child.setItemAnimator(new DefaultItemAnimator());
        lib_baseinfomation_child.setHasFixedSize(true);

        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (!isLastPage){
                    pageNum++;
                    init();
                    lib_baseinfomation_school_swipeRefresh.setRefreshing(true);
                }
            }
        });

        lib_baseinfomation_child.setAdapter(baseAdapter);

        if (!isLoading){
            init();
        }

        lib_baseinfomation_school_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                needClearData = true;
                isLastPage = false;
                init();
            }
        });
    }


    public void init(){
        isLoading = true;
//        if (type.equals("1")){
//            presenter.getTinfoDiscover(pageNum);
//        }else {
            presenter.getTinfoCared(pageNum);
//        }
    }

    @Override
    public void showData(Object o) {
        List<InfomationModel> models = (List<InfomationModel>) o;
        if (lib_baseinfomation_school_swipeRefresh != null && lib_baseinfomation_school_swipeRefresh.isRefreshing()){
            lib_baseinfomation_school_swipeRefresh.setRefreshing(false);
        }
        if (needClearData){
            modelList.clear();
            isNeedScroll = true;
            needClearData = false;
            presenter.selectAlert();
        }else {
            isNeedScroll = false;
        }
        if (models != null && models.size() > 0 && !modelList.containsAll(models)){
            modelList.addAll(models);
            isLastPage = false;
        }else {
            isLastPage = true;
            baseAdapter.setLoadingView(R.layout.footer_loadend);
        }
        baseAdapter.notifyDataSetChanged();
        if (isNeedScroll){
            lib_baseinfomation_child.scrollToPosition(0);
        }
        isLoading = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void showBadgeView(TAlertBooleanModel tAlertBooleanModel) {
        EventBus.getDefault().post(tAlertBooleanModel);
    }
}
