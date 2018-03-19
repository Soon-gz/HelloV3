package com.hellobaby.library.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.hellobaby.library.R;
import com.hellobaby.library.ui.base.BaseLibTitleActivity;
import com.hellobaby.library.widget.BottomDialogUtils;

/**
 * Created by zwj on 2016/10/26.
 * description :通用输入commcomm
 */
public class CommAlterActivity extends BaseLibTitleActivity {
    EditText et;
    private CommAlterBean mCommAlterBean;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_comm_alter;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        et=(EditText)findViewById(R.id.commAlter_et);
        setBtnLeftDrawableRes(R.drawable.title_x_black);
        setBtnRightDrawableRes(R.drawable.title_tick_black);
        mCommAlterBean = (CommAlterBean) getIntent().getSerializableExtra(CommAlterBean.kName);
        et.setText(mCommAlterBean.getOldValue());
        et.setSelection(mCommAlterBean.getOldValue().length());
        if (mCommAlterBean.getInputType() >= 0) {
            //设置输入的类型
            et.setInputType(mCommAlterBean.getInputType());
        }
        bIvRight.setVisibility(View.GONE);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newValue = et.getText().toString().trim();
                if (newValue.isEmpty()||mCommAlterBean.getOldValue().equals(newValue)) {
                    bIvRight.setVisibility(View.GONE);
                }else{
                    bIvRight.setVisibility(View.VISIBLE);
                }
            }
        });
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClick();
            }
        });
    }

    private void backClick() {
        final String newValue = et.getText().toString().trim();
        if (mCommAlterBean.getOldValue().equals(newValue)) {
            //没变化
            finish();
        } else {
            BottomDialogUtils.getBottomExitEditDialog(bContext);
        }
    }

    @Override
    public void showMsg(String msg) {
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showError(String err) {

    }


    @Override
    protected void btnRightOnClick(View v) {
        String newValue = et.getText().toString().trim();
        if (!mCommAlterBean.getOldValue().equals(newValue)) {
            //变化
            mCommAlterBean.setNewValue(newValue);
            Intent intent = new Intent();
            intent.putExtra(CommAlterBean.kName, mCommAlterBean);
            setResult(mCommAlterBean.getViewId(), intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backClick();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
