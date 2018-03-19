package com.abings.baby.teacher.ui.Information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.InfomationModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.base.BaseLibActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoSearchActivity extends BaseLibActivity implements InformationMvp{

    protected RecyclerView info_search_rv;
    protected BaseInfoNewsAdapter baseAdapter;
    protected List<InfomationModel> infomationModelList;
    protected ImageView info_libTitle_iv_left;
    @BindView(R.id.info_searchcontent_et)
    EditText info_searchcontent_et;

    @Inject
    InformationPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_info_search;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        info_search_rv = (RecyclerView) findViewById(R.id.info_search_rv);
        infomationModelList = new ArrayList<>();
        info_libTitle_iv_left = (ImageView) findViewById(R.id.info_libTitle_iv_left);
        presenter.attachView(this);
//        for (int i = 0; i < 10; i++) {
//            InfomationModel infomationModel = new InfomationModel();
//            infomationModel.setType(2);
//            if (i > 6){
//                infomationModel.setImageurl("http://dl.bizhi.sogou.com/images/2012/03/14/124196.jpg,http://dl.bizhi.sogou.com/images/2012/03/14/124196.jpg,http://dl.bizhi.sogou.com/images/2012/03/14/124196.jpg,http://dl.bizhi.sogou.com/images/2012/03/14/124196.jpg");
//            }else {
//                infomationModel.setImageurl("http://dl.bizhi.sogou.com/images/2012/03/14/124196.jpg");
//            }
//            infomationModelList.add(infomationModel);
//
//        }

        baseAdapter = new BaseInfoNewsAdapter(this,infomationModelList,false);

        info_search_rv.setAdapter(baseAdapter);
        info_search_rv.setLayoutManager(new LinearLayoutManager(bContext));
        info_search_rv.setItemAnimator(new DefaultItemAnimator());
        info_search_rv.setHasFixedSize(true);

        initClick();
    }

    private void initClick() {
        info_libTitle_iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @OnClick(R.id.info_searchtitle_iv_right)
    public void click(View view){
        String condition = info_searchcontent_et.getText().toString().trim();
        presenter.searchInfoMsg(condition);
    }


    @Override
    public void showData(Object o) {
        List<InfomationModel> models = (List<InfomationModel>) o;
        if (models != null && models.size() > 0){
            infomationModelList.addAll(models);
        }else {
            infomationModelList.clear();
        }
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showListData(List<InformationModel> InformationModel) {

    }

    @Override
    public void showMsgFinish(String msg) {

    }

    @Override
    public void setPageModel(PageModel pageModel) {

    }
}
