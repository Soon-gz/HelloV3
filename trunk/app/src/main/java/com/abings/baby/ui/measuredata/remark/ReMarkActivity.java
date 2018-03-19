package com.abings.baby.ui.measuredata.remark;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.abings.baby.R;
import com.abings.baby.data.injection.DaggerUtils;
import com.hellobaby.library.data.model.ReviewModel;
import com.hellobaby.library.ui.remarkalter.BaseRemarkAlertActivity;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.BottomPickerDateDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zwj on 2016/12/6.
 * description : 宝宝评语界面
 */

public class ReMarkActivity extends BaseRemarkAlertActivity implements ReMarkMvpView {

    @Inject
    ReMarkPresenter presenter;



    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        presenter.attachView(this);
        presenter.selectTevaluationAllBybabyid("");
        setBtnRightDrawableRes(R.drawable.select_date_black);
        bEtRemark.setTextColor(Color.BLACK);
        bEtRemark.setEnabled(false);
        bCivHead.setVisibility(View.INVISIBLE);
        bTvName.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        bIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogUtils.getBottomDatePickerDialog(bContext, null, true, new BottomPickerDateDialog.BottomOnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth, String showData) {
                        String yMd = year+"-"+monthOfYear+"-"+dayOfMonth;
                        presenter.selectTevaluationAllBybabyid(yMd);
                    }
                });
            }
        });
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void setReMark(ReviewModel reMark) {
        if(null==reMark.getRemark()||"".equals(reMark.getRemark())){
            bEtRemark.setHint("");
        }
        bEtRemark.setText(reMark.getRemark());

        List<Item> list = new ArrayList<>();
        list.add(new Item("吃饭", reMark.getEating()));
        list.add(new Item("喝水", reMark.getDrinking()));
        list.add(new Item("午休", reMark.getNoonbreak()));
        list.add(new Item("上厕所", reMark.getToilet()));
        list.add(new Item("活动", reMark.getActivity()));
        bAdapter.setNewData(list);
        bTvScore.setText(reMark.getScoreTotal());
        if(reMark.getInputDate()!=null){
            bTvCreateTime.setVisibility(View.VISIBLE);//设置显示时间
            bTvCreateTime.setText("来自于"+reMark.getTeacherName()+" "+DateUtil.timestamp2NYRFormat(Long.valueOf(reMark.getInputDate())));
        }else{
            bTvCreateTime.setVisibility(View.INVISIBLE);
        }
        linearLayout.setVisibility(View.VISIBLE);
    }

}
