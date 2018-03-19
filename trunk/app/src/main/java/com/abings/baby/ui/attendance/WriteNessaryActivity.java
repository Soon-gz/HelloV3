package com.abings.baby.ui.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.BuildConfig;
import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.UserModel;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.crop.FileUtils;

import javax.inject.Inject;

import butterknife.OnTextChanged;

/**
 * Created by Administrator on 2017/3/15.
 */

public class WriteNessaryActivity extends BaseTitleActivity implements WriteNessaryMvpView {
    TextView rel;
    EditText name;
    TextView phone;
    ImageView head;
    UserModel userModel;
    Button btn;
    @Inject
    WriteNessaryPresenter presenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_write_nessary;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
//        setBtnLeftClickFinish();
        presenter.attachView(this);
        name = (EditText) findViewById(R.id.wn_name);
        rel = (TextView) findViewById(R.id.wn_rel);
        head = (ImageView) findViewById(R.id.wn_head_img);
        btn=(Button)findViewById(R.id.btn_succeed);
        ImageLoader.loadHeadTarget(bContext, ZSApp.getInstance().getLoginUser().getHeadImageurlAbs(), head);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bContext, SinglePhotoActivity.class);
                intent.putExtra(SinglePhotoActivity.kAPP_ID, BuildConfig.APPLICATION_ID);
                intent.putExtra("bitmap", ZSApp.getInstance().getLoginUser().getHeadImageurlAbs());//这里展示的是大图
                intent.putExtra("isCreate", true);
                startActivityForResult(intent, 200);
            }
        });
        userModel = ZSApp.getInstance().getLoginUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userModel.setUserName(name.getText().toString());
                userModel.setRelation(rel.getText().toString());
                presenter.userUpdateInfo(userModel);
            }
        });
        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogUtils.getBottomRelDialog(bContext, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        rel.setText(item);
                    }
                });
            }
        });
        phone = (TextView) findViewById(R.id.wn_phone);
        name.setText(ZSApp.getInstance().getLoginUser().getUserName());
        rel.setText(ZSApp.getInstance().getBabyModel().getRelation());
        phone.setText(ZSApp.getInstance().getLoginUser().getPhoneNum());
        name.setSelection(name.length());
    }

    @Override
    public void showData(Object o) {

    }

    @OnTextChanged({R.id.wn_name, R.id.wn_rel, R.id.wn_phone})
    public void textchange() {
        if (rel.getText().length() > 0 && name.getText().length() > 0 && phone.getText().length() > 0) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        setBtnLeftClickFinish();
    }

    @Override
    public void setBtnLeftClickFinish() {
        setResult(Const.NORMAL_ACTIVITY_RESULT);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == -1) {
            String headImgPath = FileUtils.getFilePathFromUri(bContext, data.getData());
            presenter.userUploadHeadImg(headImgPath);
            head.setImageURI(data.getData());
        }
    }

    @Override
    public void showMsg(String msg) {
        super.showMsg(msg);
        for (int j = 0; j < ZSApp.getInstance().getListBaby().size(); j++) {
            //改变当前用户的baby的关系
            if(ZSApp.getInstance().getListBaby().get(j).getBabyId()==ZSApp.getInstance().getBabyModel().getBabyId()){
                ZSApp.getInstance().getListBaby().get(j).setRelation(rel.getText().toString());
            }
        }
        ZSApp.getInstance().setLoginUser(userModel);
        finish();
    }
}
