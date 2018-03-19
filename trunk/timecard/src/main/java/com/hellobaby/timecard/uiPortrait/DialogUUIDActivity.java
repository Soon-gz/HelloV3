package com.hellobaby.timecard.uiPortrait;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.utils.SharedPreferencesUtils;
import com.hellobaby.timecard.KeyConst;
import com.hellobaby.timecard.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogUUIDActivity extends BaseLibActivity {

    @BindView(R.id.dialog_input)
    EditText editText;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_dialog_uuid;
    }

    @Override
    protected void initDaggerInject() {

    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setFinishOnTouchOutside(false);
        String uuid = Settings.System.getString(getContentResolver(),KeyConst.kUUID);
//        String uuid = (String) SharedPreferencesUtils.getParam(bContext,KeyConst.kUUID,"");
        editText.setText(uuid);
    }

    @OnClick(R.id.dialog_sure)
    public void click(View view){
        String uuid = editText.getText().toString().trim();
        if (!uuid.equals("")){
            Settings.System.putString(getContentResolver(),KeyConst.kUUID,uuid);
//            SharedPreferencesUtils.setParam(bContext,KeyConst.kUUID,uuid);
            Intent intent = getIntent();
            setResult(KeyConst.kUUIDResult,intent);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            finish();
        }else {
            showError("请输入正确的机器码！");
        }
    }

    @Override
    public void showData(Object o) {

    }
}
