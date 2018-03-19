package com.abings.baby.teacher.ui.Information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ClassAssistantModel;
import com.hellobaby.library.data.model.InfomationModel;
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

public class InformationWebViewActivity extends BaseWebViewActivity {
    @Inject
    InformationPresenter presenter;
    private InfomationModel model;

    @Override
    protected void initDaggerInject() {
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        setTitleText("正文");
        model = (InfomationModel) getIntent().getSerializableExtra("InfomationModel");

        final String[] items = {"分享", "取消"};
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogUtils.getBottomListDialog(bContext, items, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                       if (position == 0) {
                            ShareBottomDialog.getShareUrlBottomDialog(bContext,model.getInfoImageurls().split(",")[0], model.getDetailsUrl(),"来自哈喽宝贝教师端", model.getTitle());
                        }
                    }
                });
            }
        });
    }
}
