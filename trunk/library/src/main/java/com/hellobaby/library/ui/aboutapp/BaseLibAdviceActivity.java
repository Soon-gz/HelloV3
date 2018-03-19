package com.hellobaby.library.ui.aboutapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.LoginUtils;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;


public abstract class BaseLibAdviceActivity<T> extends BaseLibTitleActivity<T> {

    public EditText lib_about_advice_edt;
    public EditText lib_about_advice_email_edt;
    public EditText lib_about_advice_phone_edt;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_lib_advice;
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnRightDrawableRes(R.drawable.title_update);
        bIvRight.setVisibility(View.GONE);
        setBtnLeftClickFinish();
        lib_about_advice_edt = (EditText) findViewById(R.id.lib_about_advice_edt);
        lib_about_advice_email_edt = (EditText) findViewById(R.id.lib_about_advice_email_edt);
        lib_about_advice_phone_edt = (EditText) findViewById(R.id.lib_about_advice_phone_edt);
        lib_about_advice_edt.addTextChangedListener(textWatcher);
        lib_about_advice_email_edt.addTextChangedListener(textWatcher);
        lib_about_advice_phone_edt.addTextChangedListener(textWatcher);
    }

    protected boolean isEmailOrPhone(){
        if (!StringUtils.isEmail(lib_about_advice_email_edt.getText().toString().trim())){
            ToastUtils.showNormalToast(bContext,"请输入正确的邮箱地址！");
            return false;
        }
        if (!StringUtils.isPhoneNum(lib_about_advice_phone_edt.getText().toString().trim())){
            ToastUtils.showNormalToast(bContext,"请输入正确的手机号！");
            return false;
        }
        return true;
    }

    public boolean checkAll(){
        if (StringUtils.isEmpty(lib_about_advice_edt.getText().toString())){
            return false;
        }
        if (StringUtils.isEmpty(lib_about_advice_phone_edt.getText().toString())){
            return false;
        }
        if (StringUtils.isEmpty(lib_about_advice_email_edt.getText().toString())){
            return false;
        }
        return true;
    }



    public boolean check(){
        if (!StringUtils.isEmpty(lib_about_advice_edt.getText().toString())){
            return true;
        }
        if (!StringUtils.isEmpty(lib_about_advice_phone_edt.getText().toString())){
            return true;
        }
        if (!StringUtils.isEmpty(lib_about_advice_email_edt.getText().toString())){
            return true;
        }
        return false;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (checkAll()){
                bIvRight.setVisibility(View.VISIBLE);
            }else {
                bIvRight.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void setBtnLeftClickFinish() {
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()){
                    BottomDialogUtils.getBottomExitEditDialog(bContext);
                }else {
                    finish();
                }
            }
        });
    }

    protected String getValueOfView(EditText editText){
        String value = null;
        if(StringUtils.isEmpty(editText.getText().toString())){
            ToastUtils.showNormalToast(bContext,"请完善相关信息，谢谢。");
            return value;
        }
        value = editText.getText().toString().trim();
        return value;
    }

}
