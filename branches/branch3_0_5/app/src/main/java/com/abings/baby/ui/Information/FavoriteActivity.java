package com.abings.baby.ui.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.information.BaseFavoriteActivity;
import com.hellobaby.library.ui.information.RecyclerViewAdapterInformationList;

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
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
    }

    @Override
    public void showListData(List<InformationModel> InformationModel) {
//        adapter.setNewData(InformationModel);
        mDatas.addAll(InformationModel);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatas.clear();
        presenter.attachView(this);
        presenter.SelectTFavorite();
    }

    @Override
    public void showMsgFinish(String msg) {
    }

    @Override
    protected void iniAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterInformationList(this, mDatas, false);
        Intent intent = new Intent(bContext, WebViewActivity.class);
        intent.putExtra("isFavorite",true);
        adapter.setWebViewIntent(intent);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.SelectTFavorite();
    }
}
