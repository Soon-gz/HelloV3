package com.hellobaby.library.ui.information;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知界面
 */
public abstract class BaseInformationFragment extends BaseLibFragment implements SwipeRefreshLayout.OnRefreshListener {

    public SwipeRefreshLayout mSwipeRefreshLayout;
    public View search_icon;
    protected RecyclerView recyclerView;
    protected PageModel pageModel=new PageModel();

    protected List<InformationModel> mDatas = new ArrayList<>();

    protected RecyclerViewAdapterInformationList adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_infortmation;
    }

    @Override
    protected void initViewsAndEvents() {
        mSwipeRefreshLayout=(SwipeRefreshLayout)mContentView.findViewById(R.id.information_swipeRefresh);
        recyclerView = (RecyclerView) mContentView.findViewById(R.id.alert_activity_recyclerView);
        search_icon = mContentView.findViewById(R.id.search_action);
        iniAdapter();
        initListData();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    public void showData(Object o) {

    }

    /**
     * 重写这个方法
     */
    protected void iniAdapter() {
    }

    @Override
    public void onRefresh() {
        initListData();
    }

    public abstract void initListData();

    @Override
    public void showError(String err) {
        super.showError(err);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
