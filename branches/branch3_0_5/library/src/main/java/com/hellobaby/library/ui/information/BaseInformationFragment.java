package com.hellobaby.library.ui.information;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.base.BaseLibFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知界面
 */
public class BaseInformationFragment extends BaseLibFragment {

    public View search_icon;
    protected RecyclerView recyclerView;

    protected List<InformationModel> mDatas = new ArrayList<>();

    protected RecyclerViewAdapterInformationList adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.libactivity_infortmation;
    }

    @Override
    protected void initViewsAndEvents() {
        recyclerView = (RecyclerView) mContentView.findViewById(R.id.alert_activity_recyclerView);
        search_icon = mContentView.findViewById(R.id.search_action);
        iniAdapter();
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
}
