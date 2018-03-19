package com.abings.baby.teacher.ui.Information;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.search.InformationSearchActivity;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.information.BaseInformationFragment;
import com.hellobaby.library.ui.information.RecyclerViewAdapterInformationList;

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
        presenter.selectNewsInfoBySchoolId();
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InformationSearchActivity.class));
            }
        });
    }

    @Override
    public void showListData(List<InformationModel> InformationModel) {
        adapter.setNewData(InformationModel);
    }

    @Override
    public void showMsgFinish(String msg) {

    }

    @Override
    protected void iniAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterInformationList(getContext(), mDatas, false);
        adapter.setWebViewIntent(new Intent(getActivity(), WebViewActivity.class));
        recyclerView.setAdapter(adapter);
    }
}
