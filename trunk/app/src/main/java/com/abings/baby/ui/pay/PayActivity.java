package com.abings.baby.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.R;
import com.abings.baby.ui.base.BaseTitleActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PayActivity extends BaseTitleActivity {
    TextView textView1;
    TextView textView2;
    TextView paytext3;
    LinearLayout  pay_success;
    Button pay;
    LinearLayout pay_type;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initDaggerInject() {
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setTitleText("在线支付");
        textView1=(TextView) findViewById(R.id.paytext1);
        textView2=(TextView) findViewById(R.id.paytext2);
        pay=(Button)findViewById(R.id.bt_pay);
        paytext3=(TextView)findViewById(R.id.paytext3);
        pay_type=(LinearLayout)findViewById(R.id.pay_type);
        pay_success=(LinearLayout)findViewById(R.id.pay_success);
        setBtnLeftClickFinish();
        int type=getIntent().getIntExtra("type",4);
        switch (type){
            case 0:
                textView1.setText("哈喽宝贝公开课开通订单 5元/月");
                textView2.setText("￥5.00");
                pay.setText("确认支付 ￥5.00");
                break;
            case 1:
                textView1.setText("哈喽宝贝公开课开通订单 20元/学期");
                textView2.setText("￥20.00");
                pay.setText("确认支付 ￥20.00");
                break;
            case 2:
                textView1.setText("哈喽宝贝公开课开通订单 50元/年");
                textView2.setText("￥50.00");
                pay.setText("确认支付 ￥50.00");
                break;
            case 3:
                break;
        }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay_success.setVisibility(View.VISIBLE);
                pay_type.setVisibility(View.GONE);
                paytext3.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public void showData(Object o) {

    }
}
