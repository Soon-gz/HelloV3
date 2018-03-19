package com.abings.baby.teacher.ui.PrizeDraw.history.fm;


import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.PrizeDraw.history.PrizeHistoryPresenter;
import com.hellobaby.library.data.model.PrizeHistoryBean;
import com.hellobaby.library.ui.base.BaseLibActivity;
import com.hellobaby.library.ui.base.BaseLibFragment;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends BaseLibFragment<List<PrizeHistoryBean>> {

    @BindView(R.id.prize_history_recycler)
    RecyclerView recyclerView;

    @Inject
    PrizeHistoryPresenter presenter;

    private BaseAdapter<PrizeHistoryBean> baseAdapter;
    private List<PrizeHistoryBean> prizeHistoryBeanList;

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(((BaseLibActivity)getActivity()).getActivityComponent(),getActivity()).inject(this);
        presenter.attachView(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_score;
    }

    @Override
    protected void initViewsAndEvents() {

        prizeHistoryBeanList = new ArrayList<>();

        baseAdapter = new BaseAdapter<PrizeHistoryBean>(getActivity(),prizeHistoryBeanList,false) {
            @Override
            protected void convert(ViewHolder holder, PrizeHistoryBean data) {
                TextView textViewName = holder.getView(R.id.prize_history_name);
                textViewName.setText(getTypeName(data.getType()));
                TextView textTime = holder.getView(R.id.prize_history_time);
                textTime.setText(getTime(data.getCreateTime()));
                TextView textScore = holder.getView(R.id.prize_history_score);
                textScore.setText(getScoreNumber(data,textScore));
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_prize_history;
            }
        };

        recyclerView.setAdapter(baseAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.getRecordLine();
    }

    private String getScoreNumber(PrizeHistoryBean data,TextView textScore) {
        String typeScore = "";
        switch (data.getType()){
            case 1:
                typeScore = "+"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                typeScore = "+"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                typeScore = "+"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.black));
                break;
            case 4:
                typeScore = "-"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case 5:
                typeScore = "-"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.text_red));
                break;
            case 6:
                typeScore = "+"+data.getLinePoints();
                textScore.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        return typeScore;
    }

    private String getTime(long createTime) {
        String time =  DateUtil.getFormatTimeFromTimestamp(createTime,"yyyy年MM月dd日 HH:mm");
        return time;
    }

    private String getTypeName(int type) {
        String typeName = "";
        switch (type){
            case 1:
                typeName = "完成宝宝评语";
                break;
            case 2:
                typeName = "发布校园动态";
                break;
            case 3:
                typeName = "分享app";
                break;
            case 4:
                typeName = "抽奖一次";
                break;
            case 5:
                typeName = "兑换奖品";
                break;
            case 6:
                typeName = "领取积分";
                break;
        }
        return typeName;
    }

    @Override
    public void showData(List<PrizeHistoryBean> prizeHistoryBeen) {
        if (prizeHistoryBeen != null){
            prizeHistoryBeanList.addAll(prizeHistoryBeen);
            baseAdapter.notifyDataSetChanged();
        }
    }
}
