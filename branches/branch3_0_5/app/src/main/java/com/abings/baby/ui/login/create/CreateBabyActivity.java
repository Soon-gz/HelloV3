package com.abings.baby.ui.login.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.login.needbaby.NeedBabyActivity;
import com.abings.baby.util.SharedPreferencesUtils;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.BabyModel;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.crop.SinglePhotoActivity;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;
import com.hellobaby.library.widget.crop.FileUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.tencent.open.utils.Global.getContext;

/**
 * Created by zwj on 2016/11/15.
 * description : 创建一个宝宝
 */

public class CreateBabyActivity extends BaseTitleActivity implements CreateBabyMvpView {

    @Inject
    CreateBabyPresenter presenter;

    @BindView(R.id.createBaby_iv_head)
    ImageView ivHead;
    @BindView(R.id.createBaby_et_name)
    EditText etName;
    @BindView(R.id.createBaby_et_gender)
    EditText etGender;
    @BindView(R.id.createBaby_et_birthday)
    EditText etBirthday;
    @BindView(R.id.createBaby_et_native)
    EditText etNative;
    @BindView(R.id.createBaby_et_school)
    EditText etSchool;
    @BindView(R.id.createBaby_btn_complete)
    Button btnComplete;
    String birthday = null;
    private String mHeadImgPath;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_createbaby;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        presenter.attachView(this);
    }


    @Override
    public void showData(Object o) {

    }


    //姓名
    @OnTextChanged(value = R.id.createBaby_et_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void otcName(CharSequence charSequence) {
        LoginUtils.setBtnVisibility(charSequence, btnComplete, etGender, etBirthday, etNative);
    }

    //性别
    @OnTextChanged(value = R.id.createBaby_et_gender, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void otcGender(CharSequence charSequence) {
        LoginUtils.setBtnVisibility(charSequence, btnComplete, etName, etBirthday, etNative);
    }

    //生日
    @OnTextChanged(value = R.id.createBaby_et_birthday, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void otcBirthday(CharSequence charSequence) {
        LoginUtils.setBtnVisibility(charSequence, btnComplete, etName, etGender, etNative);
    }

    //出生地
    @OnTextChanged(value = R.id.createBaby_et_native, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void otcNative(CharSequence charSequence) {
        LoginUtils.setBtnVisibility(charSequence, btnComplete, etName, etGender, etBirthday);
    }


    @OnClick({R.id.createBaby_iv_head, R.id.createBaby_et_gender, R.id.createBaby_et_birthday, R.id.createBaby_btn_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createBaby_iv_head:
                //已经选中的用户
                //切换头像
                Intent intent = new Intent(bContext, SinglePhotoActivity.class);
                intent.putExtra("bitmap", ivHead.getDrawingCache());
                startActivityForResult(intent, 200);
                break;
            case R.id.createBaby_et_gender:
                //性别
                BottomDialogUtils.getBottomGenderDialog(bContext, new BottomDialogUtils.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, String item, long id) {
                        etGender.setText(item);
                    }
                });
                break;
            case R.id.createBaby_et_birthday:
                //生日
//                String birthday = null;
                String[] bs = etBirthday.getText().toString().split(" ");
                if (bs.length >= 2) {
                    birthday = etBirthday.getText().toString().split(" ")[1];
                } else
                    birthday = null;
                BottomDialogUtils.getBottomDatePickerDialog(bContext, birthday, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showDate) {
                        etBirthday.setText("出生 " + showDate);
                    }
                });
                break;
            case R.id.createBaby_btn_complete:
                BabyModel baby = new BabyModel();
                baby.setBabyName(etName.getText().toString());
                baby.setBabyGender(etGender.getText().toString().contains("男孩") ? "1" : "0");
                SimpleDateFormat sdf = new SimpleDateFormat("出生 yyyy年MM月dd日");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse(etBirthday.getText().toString());
                    baby.setBirthday(sdf2.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                baby.setAddress(etNative.getText().toString());
                baby.setKindergarten(etSchool.getText().toString());
//                logI(JSONObject.toJSONString(baby));
                String phoneNum = (String) SharedPreferencesUtils.getParam(bContext, Const.keyPhoneNum, "");
                Log.i("ZLog", "phoneNum - >" + phoneNum);
                presenter.createBaby(baby, mHeadImgPath, phoneNum);
                break;
        }
    }

    @Override
    public void createBabyFinish() {
        showToast("创建宝宝完成");
        setResult(NeedBabyActivity.kCreateBabySuccess);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == -1) {
            //用户的登录头像更换
            mHeadImgPath = FileUtils.getFilePathFromUri(getContext(), data.getData());
            ivHead.setImageURI(data.getData());
//            meMvpView.userUploadHeadImgClick(headImgPath);
        }
    }
}
