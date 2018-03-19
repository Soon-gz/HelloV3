package com.abings.baby.teacher.ui.PrizeDraw.Adress;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.sharePrize.SharePrizeActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.PrizeAddressModel;
import com.hellobaby.library.data.model.PrizeContactInfoModel;
import com.hellobaby.library.utils.LogZS;
import com.hellobaby.library.widget.BottomDialogUtils;
import com.hellobaby.library.widget.CityPicker;
import com.hellobaby.library.widget.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PrizeAdressActivity extends BaseTitleActivity implements PrizeAddressMvpView{

    @Inject
    PrizeAddressPresenter presenter;

    @BindView(R.id.prize_address_string)
    TextView textViewAddress;

    @BindView(R.id.prize_address_username)
    EditText prize_address_username;
    @BindView(R.id.prize_address_phonenumber)
    EditText prize_address_phonenumber;
    @BindView(R.id.prize_address_fulladdress)
    EditText prize_address_fulladdress;

    @BindView(R.id.prize_address_name)
    TextView prize_address_name;
    @BindView(R.id.prize_address_price)
    TextView prize_address_price;
    @BindView(R.id.prize_address_dingdan_number)
    TextView prize_address_dingdan_number;

    String orderName = "";
    String drawId = "";
    String id = "";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_prize_adress;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        bIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackListen();
            }
        });


        String name = getIntent().getStringExtra("name");
        String price = getIntent().getStringExtra("price");
        orderName = getIntent().getStringExtra("orderNum");
        drawId = getIntent().getStringExtra("drawId");
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(orderName)){
            prize_address_name.setText(name);
            prize_address_price.setText(price);
            prize_address_dingdan_number.setText(orderName);
        }

        setTitleText("领取奖品");

        presenter.getContactInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setBackListen();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setBackListen(){
        BottomDialogUtils.getBottomPrizeDrawDialog(this, "是否放弃领取奖品？", "是", "否", new BottomDialogUtils.onSureAndCancelClick() {
            @Override
            public void onItemClick() {
                finish();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void showData(Object o) {

    }

    @OnClick({R.id.prize_address_choose,R.id.prize_address_next_btn})
    public void click(View view){
        switch (view.getId()){
            case R.id.prize_address_choose:
                String address = textViewAddress.getText().toString();
                String[]addresses = address.split(" ");
                CityPicker cityPicker = new CityPicker.Builder(PrizeAdressActivity.this).textSize(18)
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .province(addresses[0])
                        .city(addresses[1])
                        .district(addresses[2])
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();

                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        textViewAddress.setText(citySelected[0]+" " +citySelected[1]+" "+citySelected[2]);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                break;
            case R.id.prize_address_next_btn:
                String username = prize_address_username.getText().toString();
                String phonenumber = prize_address_phonenumber.getText().toString();
                String locationArea = textViewAddress.getText().toString();
                String fullAddress = prize_address_fulladdress.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(phonenumber)&& !TextUtils.isEmpty(locationArea)&& !TextUtils.isEmpty(fullAddress)){
                    presenter.insertContactInfo(username,phonenumber,fullAddress,locationArea,drawId,orderName,id);
                }else{
                    ToastUtils.showNormalToast(PrizeAdressActivity.this,"请完善收货联系人信息！");
                }
                break;
        }

    }


    @Override
    public void getContactInfo(PrizeContactInfoModel infoModel) {
        if(infoModel != null){
            id = infoModel.getId()+"";
            prize_address_username.setText(infoModel.getName());
            prize_address_phonenumber.setText(infoModel.getPhoneNum());
            textViewAddress.setText(infoModel.getLocationArea());
            prize_address_fulladdress.setText(infoModel.getFullAddress());
        }
    }

    @Override
    public void inserContactInfo(PrizeAddressModel prizeAddressModel) {
//        LogZS.i(prizeAddressModel.toString());
        if(prizeAddressModel != null){
            Intent intent = new Intent(this, SharePrizeActivity.class);
            intent.putExtra("imageUrl",prizeAddressModel.getShareImgeString());
            intent.putExtra("goodsName",prizeAddressModel.getGoodsName());
            intent.putExtra("headImage",prizeAddressModel.getHeadImageurl());
            startActivity(intent);
            finish();
        }
    }
}
