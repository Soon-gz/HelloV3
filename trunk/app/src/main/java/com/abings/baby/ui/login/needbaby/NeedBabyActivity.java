package com.abings.baby.ui.login.needbaby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.login.LoginActivity;
import com.abings.baby.ui.login.create.CreateBabyActivity;
import com.abings.baby.ui.scan.ScanActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.utils.DESUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/11/15.
 * description : 需要宝宝的界面，创建宝宝和关联宝宝界面
 */

public class NeedBabyActivity extends BaseTitleActivity implements NeedBabyMvpView {

    @Inject
    NeedBabyPresenter presenter;
    public static final int kCreateBabySuccess = 100;
    @BindView(R.id.needBaby_btn_create)
    Button needBabyBtnCreate;
    @BindView(R.id.needBaby_btn_focus)
    Button needBabyBtnFocus;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_needbaby;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setTitleBackground(R.color.transparent);
        setBtnLeftDrawableRes(R.drawable.title_left_arrow);

        List<BabyModel> list = ZSApp.getInstance().getListBaby();
        if (list != null && list.size() <= 0) {
            showToast("对不起，您还没有宝宝");
        }
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightListener();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        rightListener();
    }

    private void rightListener(){
        if (getIntent().getBooleanExtra("isFromSplash", false)) {
            Intent intent = new Intent(bContext, LoginActivity.class);
            intent.putExtra("isErr", true);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void showData(Object o) {
    }

    @OnClick({R.id.needBaby_btn_create, R.id.needBaby_btn_focus})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.needBaby_btn_create:
                intent.setClass(bContext, CreateBabyActivity.class);
                startActivityForResult(intent, kCreateBabySuccess);
                break;
            case R.id.needBaby_btn_focus:
//                PopupWindowMenu.Item[] items =
//                        {new PopupWindowMenu.Item(R.drawable.ppw_add_qrcode, "二维码", ScanActivity.class),
////                                new PopupWindowMenu.Item(R.drawable.ppw_add_telephone, "电话号码", PublishPictureActivity.class),
//                        };
//                PopupWindowMenu menu = new PopupWindowMenu(bContext, items, true, findViewById(R.id.needBaby_main));
//                menu.setItemOnClick(new IPopupWindowMenuOnClick() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        if (position == 0) {
//                            //扫描二维码
//                            Intent intent = new Intent(bContext, ScanActivity.class);
//                            startActivityForResult(intent, ScanActivity.kSCAN_RESULT_CODE);
//                        }
//                    }
//                });
//                menu.showPpw(findViewById(R.id.needBaby_main));

                //扫描二维码
                Intent intent2 = new Intent(bContext, ScanActivity.class);
                startActivityForResult(intent2, ScanActivity.kSCAN_RESULT_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == kCreateBabySuccess && resultCode == kCreateBabySuccess) {
            setResult(ScanActivity.kSCAN_RESULT_CODE);
            finish();
            if (getIntent().getBooleanExtra("isFromSplash", false)){
                startActivityDefault(new Intent(NeedBabyActivity.this,LoginActivity.class));
            }
        }
        if (resultCode == ScanActivity.kSCAN_RESULT_CODE && requestCode == ScanActivity.kSCAN_RESULT_CODE) {
            //扫描结果
            String result = data.getStringExtra(ScanActivity.kSCAN_RESULT);
            String decode = DESUtils.decodeUrl(result);
//            if (result.length() < 9 && Pattern.compile("[0-9]*").matcher(result).matches()) {
            if (decode != null) {
                JSONObject jsonObject = JSON.parseObject(decode);
//                String name = jsonObject.getString("name");
//                String type = jsonObject.getString("type");
                String babyId = jsonObject.getString("babyId");
                if (jsonObject.containsKey("babyId")) {
//                 调用 13.1 接口功能：推送一条宝宝关注信息（userbaby/sendCareNoteToUser）
//                    presenter.insertCareBaby(babyId);
                    presenter.notificationBabyParent(babyId, this);
                } else {
                    showToast("请扫描哈喽宝贝的二维码");
                }
            } else {
                showToast("请扫描哈喽宝贝的二维码");
            }
        }
    }


    @Override
    public void careBabySuccess(String babyId) {
        //13.1 接口功能：完成后
        presenter.insertInitAlert(babyId);
        setResult(ScanActivity.kSCAN_RESULT_CODE);
    }
}
