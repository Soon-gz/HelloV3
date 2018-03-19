package com.abings.baby.ui.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abings.baby.ui.event.EventDetailActivity;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.TimeCardHisModel;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.OnItemClickListeners;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.Date;
import java.util.List;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

public class RecyclerViewAdapterAttendanceList extends BaseAdapter<TimeCardHisModel> {
    protected RecyclerViewAdapterAttendanceHisItemList adapter;
    RecyclerView rv;
    ImageView im;
    public RecyclerViewAdapterAttendanceList(Context context, List<TimeCardHisModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final TimeCardHisModel data) {
        rv = (RecyclerView)holder.getView(R.id.his_rv);
        im = (ImageView)holder.getView(R.id.hisdetail_jia);
        iniAdapter(data.getTimeCardModels());
        ((TextView)holder.getView(R.id.timecard_time)).setText(DateUtil.getAttendTime(data.getDataTime()));
        if(data.getDataType()==TimeCardHisModel.TCH_TPYE_VOCATION){
            im.setVisibility(View.VISIBLE);
        }else {
            im.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_attendancehis;
    }

    private void iniAdapter(List<TimeCardModel> list) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            rv.setLayoutManager(layoutManager);
            adapter = new RecyclerViewAdapterAttendanceHisItemList(mContext, list, false);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListeners<TimeCardModel>() {
                @Override
                public void onItemClick(ViewHolder viewHolder, TimeCardModel data, int position) {
                    Intent intent=new Intent(mContext,AttendanceDetailActivity.class);
                    intent.putExtra("TimeCardModel",data);
                    mContext.startActivity(intent);
                }
            });
    }
}
