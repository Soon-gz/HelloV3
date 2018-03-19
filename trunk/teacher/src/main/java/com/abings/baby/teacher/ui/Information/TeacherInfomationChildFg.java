package com.abings.baby.teacher.ui.Information;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TeacherInfomationChildFg extends BaseLibFragment implements InformationMvp,SwipeRefreshLayout.OnRefreshListener{

    protected RecyclerView lib_baseinfomation_child;
    protected BaseInfoNewsAdapter baseAdapter;
    protected List<InfomationModel> modelList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    PageModel pageModel=new PageModel();


    @Inject
    InformationPresenter presenter;

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
        presenter.attachView(this);
        initViews();
    }

    private void initViews() {
        mSwipeRefreshLayout=(SwipeRefreshLayout) mContentView.findViewById(R.id.lib_baseinfomation_school_swipeRefresh);
        modelList = new ArrayList<>();
        lib_baseinfomation_child = (RecyclerView) mContentView.findViewById(R.id.lib_baseinfomation_child);
        lib_baseinfomation_child.setLayoutManager(new LinearLayoutManager(getActivity()));
        baseAdapter = new BaseInfoNewsAdapter(getContext(),modelList,true);
        baseAdapter.setLoadingView(com.abings.baby.teacher.R.layout.footer_more);
        baseAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (pageModel.getPageNum() != pageModel.getPages()) {
                    presenter.getTinfoDiscover(pageModel.getPageNum());
                }
                else {
                    baseAdapter.setLoadEndView(com.abings.baby.teacher.R.layout.footer_loadend);
                }
            }
        });
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        lib_baseinfomation_child.setAdapter(baseAdapter);
//        lib_baseinfomation_child.setItemAnimator(new DefaultItemAnimator());
//        lib_baseinfomation_child.setHasFixedSize(true);
        presenter.getTinfoDiscover(pageModel.getPageNum());
    }

    @Override
    public void showData(Object o) {
        if(pageModel.getPageNum()==1){
            modelList.clear();
        }
        List<InfomationModel> models = (List<InfomationModel>) o;
        if (models != null||models.size()==0){
            modelList.addAll(models);
        }else {
            baseAdapter.setLoadEndView(com.abings.baby.teacher.R.layout.footer_loadend);
        }
        pageModel.setPageNum(pageModel.getPageNum() + 1);
        baseAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showListData(List<InformationModel> InformationModel) {

    }

    @Override
    public void showMsgFinish(String msg) {

    }

    @Override
    public void setPageModel(PageModel pageModel) {

    }

    @Override
    public void onRefresh() {
        pageModel = new PageModel();
        presenter.getTinfoDiscover(pageModel.getPageNum());
    }
}
