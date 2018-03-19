package com.abings.baby.teacher.ui.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.Date;
import java.util.List;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

public class RecyclerViewAdapterEventList extends BaseAdapter<EventModel> {

    public RecyclerViewAdapterEventList(Context context, List<EventModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final EventModel data) {
        LinearLayout ll=(LinearLayout)holder.getView(R.id.eventlist_ll);
        TextView time=(TextView)holder.getView(R.id.libEventList_tv_time);
        TextView time1=(TextView)holder.getView(R.id.libEventList_tv_time1);
        TextView address=(TextView)holder.getView(R.id.libEventList_tv_address);
        TextView title=(TextView)holder.getView(R.id.libEventList_tv_title);
        Date date1=new Date();
        date1.setTime(data.getCreateTime());
        time1.setText(DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));
        address.setText(data.getEventAddress());
        title.setText(data.getEventTitle());
        time.setText(DateUtil.getFormatTimeFromTimestamp(data.getEventStartTime(),"yyyy年MM月dd日"));
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("EventModel",data);
                Intent intent =new Intent(mContext,EventDetailActivity.class);
                intent.putExtras(bundle);
                ((Activity)mContext).startActivityForResult(intent,NORMAL_ACTIVITY_RESULT);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_eventlist;
    }
}
