package com.hellobaby.library.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏夹
 */
public class BaseFavoriteActivity extends BaseLibTitleActivity {

    protected RecyclerView recyclerView;

    protected List<InformationModel> mDatas = new ArrayList<>();

    protected RecyclerViewAdapterInformationList adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_infortmation;
    }



    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setTitleText("收藏夹");
        setBtnLeftClickFinish();
        ((LinearLayout)findViewById(R.id.ll)).setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.alert_activity_recyclerView);
        iniAdapter();
    }
    @Override
    public void showData(Object o) {

    }
    protected void iniAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterInformationList(this, mDatas, false);
        adapter.setOnItemClickListener(new OnItemClickListeners<InformationModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, InformationModel data, int position) {
                Intent intent = new Intent(BaseFavoriteActivity.this, BaseWebViewActivity.class);
                intent.putExtra("webUrl", data.getDetailsUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String err) {
        super.showError(err);
        adapter.setNewData(new ArrayList<InformationModel>());
    }
}
