package com.abings.baby.ui.measuredata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ZSApp;
import com.abings.baby.ui.base.BaseTitleActivity;
import com.abings.baby.ui.measuredata.remark.ReMarkActivity;
import com.abings.baby.ui.measuredata.teachingplan.TeachingPlanActivity;
import com.alibaba.fastjson.JSONObject;
import com.hellobaby.library.data.model.MeasureModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

/**
 * 数据界面
 */
public class MeasureActivity extends BaseTitleActivity implements LineCharMvpView{
    @Inject
    LineCharPresenter presenter;
    @BindView(R.id.measure_tv_weight)
    TextView tv_weight;
    @BindView(R.id.measure_tv_height)
    TextView tv_height;
    boolean isNewHeight=false;
    boolean isNewWeight=false;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_measure;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        presenter.selectLatestHeight(ZSApp.getInstance().getBabyId());
        setBtnLeftClickFinish();
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

    @OnClick({R.id.measure_weight, R.id.measure_height, R.id.measure_remark,R.id.measure_teachingplan})
    public void ItemClick(View view) {
        Intent intent = new Intent(this, LineCharActivity.class);
        switch (view.getId()) {
            case R.id.measure_weight:
                intent.putExtra("isHeight", "0");
                intent.putExtra("isNew",isNewWeight?"1":"0");
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_height:
                intent.putExtra("isHeight", "1");
                intent.putExtra("isNew",isNewHeight?"1":"0");
                startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
                break;
            case R.id.measure_remark:
                Intent intentRemark = new Intent(bContext, ReMarkActivity.class);
                startActivity(intentRemark);
                break;
            case R.id.measure_teachingplan:
                Intent intentplan = new Intent(bContext, TeachingPlanActivity.class);
                startActivity(intentplan);
                break;
        }
    }

    @Override
    public void setLastData(JSONObject jsonObject) {
        String weight = jsonObject.getString("weight");
        if(!weight.equals("0.0")) {
            tv_weight.setText(weight + "kg");
            tv_weight.setVisibility(View.VISIBLE);
        }else {
            isNewWeight = true;
            tv_weight.setVisibility(View.GONE);
        }
        String height = jsonObject.getString("height");
        if(!height.equals("0.0")) {
            tv_height.setText(height + "cm");
            tv_height.setVisibility(View.VISIBLE);
        }else {
            isNewHeight=true;
            tv_height.setVisibility(View.GONE);
        }
    }

    @Override
    public void selectHisHeight(List<MeasureModel> models) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        presenter.selectLatestHeight(ZSApp.getInstance().getBabyId());
    }
}
