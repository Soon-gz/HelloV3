package com.abings.baby.teacher.ui.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.information.BaseFavoriteActivity;
import com.hellobaby.library.ui.information.RecyclerViewAdapterInformationList;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.baseadapter.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 收藏夹
 */
public class FavoriteActivity extends BaseFavoriteActivity implements InformationMvp{
    public static final int WEB_VIEW_CODE=1000;
    @Inject
    InformationPresenter presenter;
    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mDatas.clear();
        presenter.attachView(this);
        pageModel=new PageModel();
        presenter.SelectTFavoritePage(pageModel.getPageNum());
    }
    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
//        presenter.attachView(this);
//        pageModel=new PageModel();
//        presenter.SelectTFavoritePage(pageModel.getPageNum());
    }

    @Override
    public void initListData() {
        mDatas.clear();
        pageModel=new PageModel();
        presenter.SelectTFavoritePage(pageModel.getPageNum());
    }

    @Override
    public void showListData(List<InformationModel> informationModel) {
        if (informationModel == null) {
            mDatas.addAll(new ArrayList<InformationModel>());
        } else {
            mDatas.addAll(informationModel);
        }
            adapter.notifyDataSetChanged();
        if(pageModel.getPageNum() != pageModel.getPages()){
            adapter.setLoadingView(R.layout.footer_more);
        }else {
            adapter.setLoadEndView(R.layout.footer_loadend);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMsgFinish(String msg) {
        ToastUtils.showToast(this,msg);
        finish();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterInformationList(this, mDatas, true);
        Intent intent = new Intent(bContext, WebViewActivity.class);
        intent.putExtra("isFavorite",true);
        adapter.setWebViewIntent(intent);
        adapter.setLoadingView(R.layout.footer_more);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if(pageModel.getPageNum()!=pageModel.getPages())
                    presenter.SelectTFavoritePage(pageModel.getPageNum()+1);
                else {
                    adapter.setLoadEndView(R.layout.footer_loadend);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.SelectTFavorite();
    }
}
