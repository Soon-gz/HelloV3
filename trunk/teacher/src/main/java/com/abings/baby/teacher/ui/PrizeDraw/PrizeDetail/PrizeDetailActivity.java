package com.abings.baby.teacher.ui.PrizeDraw.PrizeDetail;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.ui.PrizeDraw.Adress.PrizeAdressActivity;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.ExchangeModel;
import com.hellobaby.library.utils.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PrizeDetailActivity extends BaseTitleActivity<ExchangeModel> {

    @BindView(R.id.full_list_view)
    FullListView fullListView;
    @BindView(R.id.prize_detail_image)
    ImageView imageView;
    @BindView(R.id.detail_prize_name)
    TextView detail_prize_name;
    @BindView(R.id.detail_rest_number)
    TextView detail_rest_number;
    @BindView(R.id.detail_prize_price)
    TextView detail_prize_price;

    @Inject
    PrizeDetailPresenter presenter;



    private String[] explains;
    private FullListViewAdapter fullListViewAdapter;
    String exchangeId = "";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_prize_detail;
    }

    @Override
    protected void initDaggerInject() {
        getActivityComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        setBtnLeftClickFinish();
        String name = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String rest = getIntent().getStringExtra("rest");
        String price = getIntent().getStringExtra("price");
        String explain = getIntent().getStringExtra("explain");
        exchangeId = getIntent().getStringExtra("exchangeId");
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(rest) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(explain)){
            explains = explain.split("¨");
            detail_prize_name.setText(name);
            detail_rest_number.setText(rest);
            detail_prize_price.setText(price);
            ImageLoader.load(this, Const.URL_prizeDrawImg + imageUrl,imageView);
        }

        setTitleText("商品详情");

        fullListViewAdapter = new FullListViewAdapter(explains,this);
        fullListView.setAdapter(fullListViewAdapter);
    }

    @OnClick(R.id.prize_detail_duihuan)
    public void click(View view){
        presenter.exchangePool(exchangeId);
    }

    @Override
    public void showData(ExchangeModel exchangeModel) {
        Intent intent = new Intent(this, PrizeAdressActivity.class);
        intent.putExtra("name",exchangeModel.getName());
        intent.putExtra("orderNum",exchangeModel.getOrderNum());
        intent.putExtra("price",exchangeModel.getPoints()+"");
        intent.putExtra("drawId",exchangeModel.getDrawId()+"");
        startActivity(intent);
        finish();
    }
}
