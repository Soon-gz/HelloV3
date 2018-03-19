package com.abings.baby.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class SelectdateActivity extends BaseTitleActivity {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_selectpaydate;
    }

    @Override
    protected void initDaggerInject() {
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setTitleText("开通公开课");
        setBtnLeftClickFinish();
    }
    @OnClick({R.id.selectpaydate_month,R.id.selectpaydate_term,R.id.selectpaydate_year})
    public void OnClick(View view){
        Intent intent=new Intent(this,PayActivity.class);
        switch (view.getId()){
            case R.id.selectpaydate_month:
                intent.putExtra("type",0);
                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.selectpaydate_term:
                intent.putExtra("type",1);
                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.selectpaydate_year:
                intent.putExtra("type",2);
                startActivityForResult(intent, Const.NORMAL_ACTIVITY_RESULT);
                break;
        }
    }
    @Override
    public void showData(Object o) {

    }
}
