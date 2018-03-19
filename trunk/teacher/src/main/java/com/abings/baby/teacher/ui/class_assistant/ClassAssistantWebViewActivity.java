package com.abings.baby.teacher.ui.class_assistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.Information.InformationMvp;
import com.abings.baby.teacher.ui.Information.InformationPresenter;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.InformationModel;
import com.hellobaby.library.data.model.PageModel;
import com.hellobaby.library.ui.webview.BaseWebViewActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.custom.ShareBottomDialog;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2017/1/10.
 * 课堂助手的网页展示
 */

public class ClassAssistantWebViewActivity extends BaseWebViewActivity implements InformationMvp {
    @Inject
    InformationPresenter presenter;
    private ClassAssistantModel model;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(), this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        findViewById(R.id.webView_root).setBackgroundColor(getResources().getColor(R.color.white));
        setTitleText("正文");
        setTitleBackground(R.color.white);
        model = getIntent().getParcelableExtra(ClassAssistantModel.kName);

        final String[] items = {"收藏", "分享"};
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        if (position == 0){
                            presenter.insertTFavorite("3", model.getAssistId()+"");
                        }else if (position == 1) {
                            ShareBottomDialog.getShareUrlBottomDialog(bContext, Const.URL_ClassRoom+model.getAssistImageurls().split(",")[0], model.getDetailsUrl(), "来自哈喽宝贝教师端", model.getTitle());
                        }
                    }
                });
            }
        });
    }


    @Override
    public void showListData(List<InformationModel> InformationModel) {

    }

    @Override
    public void showMsgFinish(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void setPageModel(PageModel pageModel) {

    }
}
