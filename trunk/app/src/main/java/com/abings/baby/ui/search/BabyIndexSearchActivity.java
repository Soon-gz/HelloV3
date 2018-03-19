package com.abings.baby.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseActivity;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.event.EventDetailActivity;
import com.abings.baby.ui.main.fm.school.SchoolRVAdapter;
import com.alibaba.fastjson.JSONArray;
import com.hellobaby.library.data.model.AlbumModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.main.fm.school.BaseSchoolRVAdapter;
import com.hellobaby.library.ui.main.fm.school.SchoolItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/1/19.
 */

public class BabyIndexSearchActivity extends BaseActivity implements SearchMvp {
    RecyclerView bRecyclerView;
    protected List<AlbumModel> bListData = new ArrayList<>();
    protected RecyclerViewAdapterSearchIndex bAdapter;
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
        bAdapter = new RecyclerViewAdapterSearchIndex(this, bListData, false);
        bRecyclerView.setAdapter(bAdapter);
        bAdapter.notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showListData(List<AlbumModel> lists) {
        bListData.clear();
        bAdapter.setNewData(lists);
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSchoolListData(JSONArray lists) {

    }

    @Override
    public void showError(String err) {
        super.showError(err);
        bListData.clear();
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInformationListData(List<InformationModel> lists) {

    }

    @OnClick(R.id.searchtitle_iv_right)
    public void click() {
        presenter.selectIndexBySearch(textView.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.libTitle_iv_left)
    public void clickFinish() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.selectIndexBySearch(textView.getText().toString());
    }
}
