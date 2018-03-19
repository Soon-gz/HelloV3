package com.abings.baby.ui.Information;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.search.InformationSearchActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.information.BaseInformationFragment;
import com.hellobaby.library.ui.information.RecyclerViewAdapterInformationList;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/10.
 */

public class InformationFragment extends BaseInformationFragment implements InformationMvp{
    @Inject
    InformationPresenter presenter;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        presenter.attachView(this);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InformationSearchActivity.class));
            }
        });
    }

    @Override
    public void showListData(List<InformationModel> InformationModel) {
//        adapter.setNewData(InformationModel);
        mDatas.addAll(InformationModel);
        adapter.notifyDataSetChanged();
        if(pageModel.getPageNum() != pageModel.getPages()){
            adapter.setLoadingView(R.layout.footer_more);
        }else {
            adapter.setLoadEndView(R.layout.footer_loadend);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMsgFinish(String msg) {

    }

    @Override
    public void setPageModel(PageModel pageModel) {
        this.pageModel=pageModel;
        if(pageModel.getPageNum() != pageModel.getPages()){
            adapter.setLoadingView(R.layout.footer_more);
        }else {
            adapter.setLoadEndView(R.layout.footer_loadend);
        }
    }

    @Override
    protected void iniAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterInformationList(getContext(), mDatas, true);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        adapter.setWebViewIntent(intent);
        adapter.setLoadingView(R.layout.footer_more);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(pageModel.getPageNum()!=pageModel.getPages())
                    presenter.selectNewsInfoBySchoolIdPage(pageModel.getPageNum()+1);
                else {
                    adapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initListData() {
        pageModel=new PageModel();
//        presenter.selectNewsInfoBySchoolId();
        mDatas.clear();
        presenter.selectNewsInfoBySchoolIdPage(pageModel.getPageNum());
    }
}
