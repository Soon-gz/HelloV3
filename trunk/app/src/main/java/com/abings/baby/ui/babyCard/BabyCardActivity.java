package com.abings.baby.ui.babyCard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.babyCard.babyCardRelations.BabyCardRelationActivity;
import com.abings.baby.ui.scan.ScanActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyRelationModel;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BabyCardActivity extends BaseLibTitleActivity<List<BabyRelationModel>> implements BabyCardMvpView<List<BabyRelationModel>>{

    public static final int BABY_CARD_ADD_RELAITON = 100;
    private static final int BABY_CARD_LIST = 0;
    private static final int BABY_CARD_NONE = 1;


    @BindView(R.id.babycard_list_rv)
    RecyclerView recyclerView;
    @BindView(R.id.babycard_list_fm)
    FrameLayout frameLayout;


    private List<BabyRelationModel> babyRelationModels;
    private BabycardAdapter babycardAdapter;
    private String qrcode;
    private boolean isFirstIn = true;

    @Inject
    BabyCardPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstIn){
            isFirstIn = false;
            return;
        }
        babyRelationModels.clear();
        presenter.getBabyRelationse(Integer.parseInt(ZSApp.getInstance().getBabyId()));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_baby_card;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent, this).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        babyRelationModels = new ArrayList<>();
        babycardAdapter = new BabycardAdapter(bContext,babyRelationModels,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(babycardAdapter);
        //获取该id的接送卡
        presenter.getBabyRelationse(Integer.parseInt(ZSApp.getInstance().getBabyId()));
    }

    @OnClick(R.id.babycard_add_btn)
    public void onClick(View view){
        startActivityForResult(new Intent(bContext,ScanActivity.class),BabyCardActivity.BABY_CARD_ADD_RELAITON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BABY_CARD_ADD_RELAITON && resultCode == ScanActivity.kSCAN_RESULT_CODE) {
            //扫描结果
            String result = data.getStringExtra(ScanActivity.kSCAN_RESULT);
            Log.i("tag00","扫描二维码："+result);
            qrcode = result;
            presenter.qrScanCode(qrcode);
        }

    }

    @Override
    public void showData(List<BabyRelationModel> babyRelationModels) {
        if (babyRelationModels != null && babyRelationModels.size() > 0){
            frameLayout.getChildAt(BABY_CARD_NONE).setVisibility(View.GONE);
            frameLayout.getChildAt(BABY_CARD_LIST).setVisibility(View.VISIBLE);
            this.babyRelationModels.addAll(babyRelationModels);
            babycardAdapter.notifyDataSetChanged();
        } else {
            frameLayout.getChildAt(BABY_CARD_LIST).setVisibility(View.GONE);
            frameLayout.getChildAt(BABY_CARD_NONE).setVisibility(View.VISIBLE);
            babycardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void qrScanCode() {
        Log.i("tag00","二维码检测成功。可以添加");
        if (qrcode != null){
            startActivity(new Intent(this, BabyCardRelationActivity.class).putExtra(Const.BABY_CARD_QRCODE,qrcode));
        }else {
            ToastUtils.showNormalToast(bContext,"二维码解析异常");
        }
    }



    @Override
    public void noCards() {
        babyRelationModels.clear();
        babycardAdapter.notifyDataSetChanged();
    }

    @Override
    public void qrcodeUnuse() {
        ToastUtils.showNormalToast(bContext,"该二维码不可用或已经注册过了。");
    }


}
