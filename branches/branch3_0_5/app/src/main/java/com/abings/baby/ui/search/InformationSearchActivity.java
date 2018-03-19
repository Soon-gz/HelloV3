package com.abings.baby.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.Information.WebViewActivity;
import com.abings.baby.ui.base.BaseActivity;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.information.RecyclerViewAdapterInformationList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/19.
 */

public class InformationSearchActivity extends BaseActivity implements SearchMvp {
    RecyclerView bRecyclerView;
    protected List<InformationModel> bListData = new ArrayList<>();
    protected RecyclerViewAdapterInformationList bAdapter;
    @Inject
    SearchPresenter presenter;
    @BindView(R.id.searchcontent_et)
    TextView textView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        bRecyclerView = (RecyclerView) findViewById(R.id.search_rv);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bAdapter = new RecyclerViewAdapterInformationList(this, bListData, false);
        bAdapter.setWebViewIntent(new Intent(this, WebViewActivity.class));
        bRecyclerView.setAdapter(bAdapter);
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick(R.id.searchtitle_iv_right)
    public void click() {
        bAdapter.setKeyWords(textView.getText().toString());
        presenter.selectNewsInfoBySchoolId(textView.getText().toString());
    }

    @Override
    public void showListData(List<AlbumModel> lists) {

    }

    @Override
    public void showSchoolListData(JSONArray lists) {

    }

    @Override
    public void showInformationListData(List<InformationModel> lists) {
        bAdapter.setNewData(lists);
        bAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.libTitle_iv_left)
    public void clickFinish() {
        finish();
    }
}
