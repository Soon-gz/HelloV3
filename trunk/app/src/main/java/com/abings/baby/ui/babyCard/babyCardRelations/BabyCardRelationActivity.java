package com.abings.baby.ui.babyCard.babyCardRelations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.data.injection.DaggerUtils;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyRelationModel;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;
import com.hellobaby.library.widget.crop.FileUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BabyCardRelationActivity extends BaseTitleActivity<String> implements BabyCardRelationMvpView<String> {

    @BindView(R.id.babycard_relation_head_img)
    CircleImageView circleImageView;
    @BindView(R.id.babycard_relation_name)
    EditText editText_name;
    @BindView(R.id.babycard_relation_ship)
    TextView editText_realtionship;
    @BindView(R.id.babycard_relation_phone)
    EditText editText_phone;
    @BindView(R.id.babycard_relation_surebtn)
    Button relation_surebtn;

    private String qrcode;
    private String filePath;
    private int pickBabyCardId = 0;
    private BabyRelationModel babyRelationModel;


    @Inject
    BabyCardRelationPresenter presenter;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkAllInput();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_baby_card_relation;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(bActivityComponent,this).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        if (getIntent().getSerializableExtra(Const.BABY_CARD_RELATION) != null){
            setBtnRightDrawableRes(R.drawable.del_icon);

            relation_surebtn.setVisibility(View.VISIBLE);
            relation_surebtn.setText("保存");

            babyRelationModel = (BabyRelationModel) getIntent().getSerializableExtra(Const.BABY_CARD_RELATION);
            initViews(babyRelationModel);
        }else {
            qrcode = getIntent().getStringExtra(Const.BABY_CARD_QRCODE);
            relation_surebtn.setText("确定");
            editText_name.addTextChangedListener(textWatcher);
            editText_phone.addTextChangedListener(textWatcher);
        }
    }

    private void initViews(BabyRelationModel relationModel) {
        Log.i("tag00","详情头像："+Const.URL_pickHead+ relationModel.getHeadImageurl());
        ImageLoader.loadHeadTarget(this,Const.URL_pickHead+ relationModel.getHeadImageurl(),circleImageView);
        editText_name.setText(relationModel.getUserName());
        pickBabyCardId = relationModel.getPickUpId();
        editText_realtionship.setText(relationModel.getRelation());
        editText_phone.setText(relationModel.getPhoneNum());
        editText_name.addTextChangedListener(textWatcher);
        editText_phone.addTextChangedListener(textWatcher);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == -1) {
            String headImgPath = FileUtils.getFilePathFromUri(bContext, data.getData());
            filePath = headImgPath;
            circleImageView.setImageURI(data.getData());
            if (babyRelationModel != null){
                presenter.uploadHead(pickBabyCardId,headImgPath);
            }
        }
    }


    @Override
    public void setBtnLeftClickFinish() {
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialogUtils.getBottomExitEditDialog(bContext);
            }
        });

    }

    @Override
    protected void btnRightOnClick(View v) {
        super.btnRightOnClick(v);
        BottomDialogUtils.getBottomDynamicDelDialog(bContext, new BottomDialogUtils.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                if (position == 0){
                    presenter.deleteBabyCard(pickBabyCardId);
                }
            }
        });
    }

    public void checkAllInput(){
        if (StringUtils.isEmpty(editText_name.getText().toString())){
            relation_surebtn.setVisibility(View.GONE);
            return;
        }
//        if (StringUtils.isEmpty(editText_realtionship.getText().toString())){
//            relation_surebtn.setVisibility(View.GONE);
//            return;
//        }
        if (StringUtils.isEmpty(editText_phone.getText().toString())){
            relation_surebtn.setVisibility(View.GONE);
            return;
        }
        relation_surebtn.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.babycard_relation_surebtn,R.id.babycard_relation_head_img,R.id.babycard_relation_ship})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.babycard_relation_surebtn:
                if (babyRelationModel != null){
                    String userNameUpdate = editText_name.getText().toString();
                    String phoneUpdate = editText_phone.getText().toString();
                    if(!StringUtils.isPhoneNum(phoneUpdate)){
                        ToastUtils.showNormalToast(bContext,"请输入正确的手机号！");
                        return;
                    }
                    String relationUpdate = editText_realtionship.getText().toString();
                    presenter.updateBabyCard(pickBabyCardId,userNameUpdate,phoneUpdate,relationUpdate);
                }else {
                    String userName = editText_name.getText().toString();
                    String phone = editText_phone.getText().toString();
                    if(!StringUtils.isPhoneNum(phone)){
                        ToastUtils.showNormalToast(bContext,"请输入正确的手机号！");
                        return;
                    }
                    String relation = editText_realtionship.getText().toString();
                    presenter.insertTPCard(qrcode,userName,phone,relation,filePath);
                }
                break;
            case R.id.babycard_relation_head_img:
                Intent intent = new Intent(bContext,SinglePhotoActivity.class);
                if (babyRelationModel != null){
                    intent.putExtra("bitmap", Const.URL_pickHead + babyRelationModel.getHeadImageurl());//这里展示的是大图
                }
                intent.putExtra("isCreate",true);
                startActivityForResult(intent,200);
                break;
            case R.id.babycard_relation_ship:
                BottomDialogUtils.getBottomRelDialog(this, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        editText_realtionship.setText(item);
                    }
                });
                break;
        }
    }

    @Override
    public void showData(String s) {

    }

    @Override
    public void uploadWithOutHeadImg() {
        ToastUtils.showNormalToast(bContext,"添加接送卡成功。");
        finish();
    }

    @Override
    public void uploadSuccess() {
        ToastUtils.showNormalToast(bContext,"添加接送卡成功。");
        finish();
    }


    @Override
    public void updateSuccess() {
        ToastUtils.showNormalToast(bContext,"修改接送卡成功。");
        finish();
    }

    @Override
    public void updateHead() {
        ToastUtils.showNormalToast(bContext,"头像修改成功。");
    }

    @Override
    public void deleteSuccess() {
        ToastUtils.showNormalToast(bContext,"删除成功。");
        finish();
    }
}
