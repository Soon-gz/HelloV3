package com.abings.baby.teacher.ui.event;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.abings.baby.teacher.R;
import com.abings.baby.teacher.data.injection.DaggerUtils;
import com.abings.baby.teacher.ui.base.BaseTitleActivity;
import com.hellobaby.library.data.model.EventJoinModel;
import com.hellobaby.library.data.model.EventModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class EventJoinDetailActivity extends BaseTitleActivity implements EventDetailMvpView{
    @Inject
    EventDetailPresenter presenter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapterEventJoinList mAdapter;
    TextView countperson;
    private List<EventJoinModel> bListData=new ArrayList<>();
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_event_join_detail;
    }

    @Override
    protected void initDaggerInject() {
        DaggerUtils.getActivityComponent(getActivityComponent(),this).inject(this);
    }

    @Override
    protected void initViewsAndEvents(@Nullable Bundle savedInstanceState) {
        presenter.attachView(this);
        setBtnLeftClickFinish();
        RecyclerView mDataRv = (RecyclerView) findViewById(R.id.joindetal_rv);
        countperson=(TextView)findViewById(R.id.tv_totalcount);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(mLayoutManager);
        bListData=new ArrayList<>();
        initAdapter();
        mDataRv.setAdapter(mAdapter);
        countperson.setText("总人数("+getIntent().getStringExtra("totalperson")+")");
        presenter.selectEventNumList(getIntent().getStringExtra("eventId"));
    }

    private void initAdapter() {
        mAdapter=new RecyclerViewAdapterEventJoinList(bContext,bListData,false);
    }

    @Override
    public void showData(Object o) {

    }

    @Override
    public void showEvent(EventModel eventModel) {

    }

    @Override
    public void showJoinDetail(List<EventJoinModel> list) {
        mAdapter.setNewData(list);
    }
}
