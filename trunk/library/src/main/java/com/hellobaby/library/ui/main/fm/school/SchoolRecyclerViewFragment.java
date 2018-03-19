package com.hellobaby.library.ui.main.fm.school;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/12/20.
 * description :
 */

public abstract class SchoolRecyclerViewFragment<T> extends BaseLibFragment<T>implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView bRecyclerView;
    protected List<SchoolItem> bListData;
    protected BaseSchoolRVAdapter bAdapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public PageModel pageModel=new PageModel();
    protected String classesIDS = "resultData";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_school_recyclerview;
    }

    @Override
    protected void initViewsAndEvents() {
        bRecyclerView = (RecyclerView) mContentView.findViewById(R.id.schoolRecyclerView);
        mSwipeRefreshLayout=(SwipeRefreshLayout)mContentView.findViewById(R.id.school_swipeRefresh);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bListData = new ArrayList<>();
        initAdapter();
        initListData();
        bRecyclerView.setAdapter(bAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public abstract void initAdapter();

    public abstract void initListData();

    @Override
    public void onRefresh() {
        initListData();
    }

    @Override
    public void showError(String err) {
//        super.showError(err);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMsg(String msg) {
//        super.showMsg(msg);
    }
}
