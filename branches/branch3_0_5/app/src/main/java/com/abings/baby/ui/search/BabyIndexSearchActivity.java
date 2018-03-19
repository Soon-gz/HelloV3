package com.abings.baby.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import static com.tencent.open.utils.Global.getContext;

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
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showListData(List<AlbumModel> lists) {
        bAdapter.setNewData(lists);
        bAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSchoolListData(JSONArray lists) {

    }

    @Override
    public void showInformationListData(List<InformationModel> lists) {

    }

    @OnClick(R.id.searchtitle_iv_right)
    public void click() {
        presenter.selectIndexBySearch(textView.getText().toString());
    }

    @OnClick(R.id.libTitle_iv_left)
    public void clickFinish() {
        finish();
    }
}
