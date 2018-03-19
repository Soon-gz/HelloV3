package com.abings.baby.teacher.ui.PrizeDraw.history.prizeDetail;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryPresenter;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.PrizeOrderDetailModel;
import com.hellobaby.library.utils.StringUtils;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PrizeHistoryDetail extends BaseTitleActivity {

    @BindView(R.id.prize_history_name_detail)
    TextView prize_history_name;
    @BindView(R.id.prize_history_price)
    TextView prize_history_price;
    @BindView(R.id.prize_history_dingdan_number)
    TextView prize_history_dingdan_number;
    @BindView(R.id.prize_history_username)
    TextView prize_history_username;
    @BindView(R.id.prize_history_phonenumber)
    TextView prize_history_phonenumber;
    @BindView(R.id.prize_history_string)
    TextView prize_history_string;
    @BindView(R.id.prize_history_fullhistory)
    TextView prize_history_fullhistory;
    @BindView(R.id.prize_history_state_text)
    TextView prize_history_state_text;
    @BindView(R.id.prize_history_order_number)
    TextView prize_history_order_number;
    @BindView(R.id.prize_history_sure_btn)
    Button button;

    @Inject
    PrizeHistoryPresenter presenter;


    PrizeOrderDetailModel prizeOrderDetailModel;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_prize_history_detail;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {

        setBtnLeftClickFinish();
        setTitleText("订单详情");
        prizeOrderDetailModel = (PrizeOrderDetailModel) getIntent().getSerializableExtra("itemdata");

        if(prizeOrderDetailModel != null){
            prize_history_name.setText(prizeOrderDetailModel.getPrizeName());
            prize_history_price.setText(prizeOrderDetailModel.getPoints()+"");
            prize_history_dingdan_number.setText(prizeOrderDetailModel.getOrderNum());
            prize_history_username.setText(prizeOrderDetailModel.getName());
            prize_history_phonenumber.setText(prizeOrderDetailModel.getPhoneNum());
            prize_history_string.setText(prizeOrderDetailModel.getLocationArea());
            prize_history_fullhistory.setText(prizeOrderDetailModel.getFullAddress());
            prize_history_state_text.setText(getState(prizeOrderDetailModel.getState()));
            prize_history_order_number.setText(prizeOrderDetailModel.getCourierNumber());
            if(prizeOrderDetailModel.getState() == 2 || prizeOrderDetailModel.getState() == 3){
                button.setVisibility(View.VISIBLE);
            }else{
                button.setVisibility(View.GONE);
            }

        }
    }

    private String getState(int type) {
        String state = "";
        switch (type){
            case 0:
                state = "未完成";
                break;
            case 1:
                state = "进行中";
                break;
            case 2:
                state = "进行中";
                break;
            case 3:
                state = "已发货";
                break;
            case 4:
                state = "已完成";
                break;
            default:
                state = "进行中";
                break;
        }
        return state;
    }

    @OnClick(R.id.prize_history_sure_btn)
    public void onClickBtn(View view){
        BottomDialogUtils.getBottomPrizeDrawDialog(this, "是否已确认收货？", "是", "否", new BottomDialogUtils.onSureAndCancelClick() {
            @Override
            public void onItemClick() {
               if (!StringUtils.isEmpty(prizeOrderDetailModel.getId()+"")){
                   presenter.sureGet(prizeOrderDetailModel.getId()+"");
                   finish();
               }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void showData(Object o) {
        ToastUtils.showNormalToast(this,"确认收货成功");
    }
}
