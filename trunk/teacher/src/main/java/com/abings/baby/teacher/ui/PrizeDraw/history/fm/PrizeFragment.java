package com.abings.baby.teacher.ui.PrizeDraw.history.fm;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.PrizeDraw.Adress.PrizeAdressActivity;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryActivity;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryPresenter;
import com.abings.baby.teacher.ui.PrizeDraw.history.prizeDetail.PrizeHistoryDetail;
import com.hellobaby.library.data.model.PrizeHistoryBean;
import com.hellobaby.library.data.model.PrizeOrderDetailModel;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrizeFragment extends BaseLibFragment<List<PrizeOrderDetailModel>> {

    @BindView(R.id.prize_recycler)
    RecyclerView recyclerView;


    @Inject
    PrizeHistoryPresenter presenter;

    private BaseAdapter<PrizeOrderDetailModel> baseAdapter;
    private List<PrizeOrderDetailModel> prizeHistoryBeanList;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_prize;
    }

    @Override
    protected void initViewsAndEvents() {
        prizeHistoryBeanList = new ArrayList<>();

        baseAdapter = new BaseAdapter<PrizeOrderDetailModel>(getActivity(),prizeHistoryBeanList,false) {
            @Override
            protected void convert(ViewHolder holder, PrizeOrderDetailModel data) {

                TextView prize_order_name = holder.getView(R.id.prize_order_name);
                prize_order_name.setText(data.getPrizeName());

                TextView prize_order_time = holder.getView(R.id.prize_order_time);
                prize_order_time.setText(getTime(data.getCreateTime()));

                TextView prize_order_state = holder.getView(R.id.prize_order_state);
                getTypeName(data.getState(),prize_order_state);

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_order_prize;
            }
        };

        recyclerView.setAdapter(baseAdapter);
        baseAdapter.setOnItemClickListener(new OnItemClickListeners<PrizeOrderDetailModel>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, PrizeOrderDetailModel data, int position) {
                if(data.getState() == 0){
                    Intent intent = new Intent(getActivity(), PrizeAdressActivity.class);
                    intent.putExtra("name",data.getPrizeName());
                    intent.putExtra("orderNum",data.getOrderNum());
                    intent.putExtra("price",data.getPoints()+"");
                    intent.putExtra("drawId",data.getId()+"");
                    startActivity(intent);
                }else{
                    startActivity(new Intent(getActivity(), PrizeHistoryDetail.class).putExtra("itemdata",data));
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.getOrderList();
    }

    private String getTime(long createTime) {
        String time =  DateUtil.getFormatTimeFromTimestamp(createTime,"yyyy年MM月dd日 HH:mm");
        return time;
    }

    private void getTypeName(int type,TextView textView) {
        switch (type){
            case 0:
                textView.setText("未完成");
                textView.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case 1:
                textView.setText("进行中");
                textView.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case 2:
                textView.setText("进行中");
                textView.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case 3:
                textView.setText("已发货");
                textView.setTextColor(getResources().getColor(R.color.video_green));
                break;
            case 4:
                textView.setText("已完成");
                textView.setTextColor(getResources().getColor(R.color.gray6c));
                break;
        }
    }

    @Override
    public void showData(List<PrizeOrderDetailModel> prizeHistoryBeen) {
        if (prizeHistoryBeen != null){
            prizeHistoryBeanList.addAll(prizeHistoryBeen);
            baseAdapter.notifyDataSetChanged();
        }
    }
}
