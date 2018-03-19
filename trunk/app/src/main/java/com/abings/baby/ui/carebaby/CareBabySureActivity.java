package com.abings.baby.ui.carebaby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.alibaba.fastjson.JSON;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.JPushModel;
import com.hellobaby.library.data.model.ServerCarebabyCache;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class CareBabySureActivity extends BaseLibActivity implements CareBabyMvpView {

    @Inject
    CareBabyPresenter presenter;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_care_baby_sure;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        if (getIntent().getStringExtra(Const.AGREELIST) != null && "receiver".equals(getIntent().getSerializableExtra(Const.FROMWHERE))) {
            String content = getIntent().getStringExtra(Const.AGREELIST);
            final JPushModel jPushModel = JSON.parseObject(content,JPushModel.class);
            BottomDialogUtils.getBottomAgreeDialog(this, jPushModel.getJsonObject().getAlert(), new BottomDialogUtils.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                    switch (position) {
                        case 0:
                            presenter.insertCareBaby(jPushModel.getJsonObject().getCareUserId() + "", jPushModel.getJsonObject().getBabyId() + "");
                            finish();
                            break;
                        case 1:
                            presenter.disAgreeCareBaby(jPushModel.getJsonObject().getCareUserId() + "", jPushModel.getJsonObject().getBabyId() + "");
                            finish();
                            break;
                    }
                }
            });
        }else {
            if (ZSApp.getInstance().getServerCarebabyCaches() != null){
                 int index = 0;
                 List<ServerCarebabyCache>caches = ZSApp.getInstance().getServerCarebabyCaches();
                 //清空缓存，避免内存占用
                 ZSApp.getInstance().setServerCarebabyCaches(null);
                 String userId = caches.get(index).getCareUserId()+"";
                 String babyId = caches.get(index).getBabyId()+"";
                showBottomDialog(userId,babyId,index,caches);
            }
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//需要添加的语句
    }

    public void showBottomDialog(final String userId, final String babyId, final int index, final List<ServerCarebabyCache> caches){
        boolean isFinalyRequest = false;
        if (index >= caches.size()){
            return ;
        }
        if (index == caches.size() - 1){
            isFinalyRequest = true;
        }
        final String userIdnext = caches.get(index).getCareUserId()+"";
        final String babyIdnext = caches.get(index).getBabyId()+"";
        final int indexnext = index+1;
        final boolean finalIsFinalyRequest = isFinalyRequest;
        BottomDialogUtils.getBottomAgreeDialog(this, caches.get(index).getAlert(), new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                switch (position) {
                    case 0:
                        presenter.insertCareBabyWithList(userId, babyId, finalIsFinalyRequest);
                        showBottomDialog(userIdnext,babyIdnext,indexnext,caches);
                        break;
                    case 1:
                        presenter.disAgreeCareBabyWithList(userId, babyId, finalIsFinalyRequest);
                        showBottomDialog(userIdnext,babyIdnext,indexnext,caches);
                        break;
                }
            }
        });
    }

    @Override
    public void showData(Object o) {
        ToastUtils.showNormalToast(bContext, "操作成功。");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.hellobaby.library.R.anim.activity_bottom_in, com.hellobaby.library.R.anim.activity_bottom_out);
    }

    @Override
    public void finalyRequest() {
        ToastUtils.showNormalToast(bContext, "操作成功。");
        finish();
    }
}
