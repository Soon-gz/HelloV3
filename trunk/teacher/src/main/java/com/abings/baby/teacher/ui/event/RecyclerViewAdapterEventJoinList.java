package com.abings.baby.teacher.ui.event;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.EventJoinModel;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.Date;
import java.util.List;

public class RecyclerViewAdapterEventJoinList extends BaseAdapter<EventJoinModel> {

    public RecyclerViewAdapterEventJoinList(Context context, List<EventJoinModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, EventJoinModel data) {
        RelativeLayout ll=(RelativeLayout)holder.getView(R.id.eventlist_rl_root);
        TextView join_name=(TextView)holder.getView(R.id.join_name);
        TextView join_baby=(TextView)holder.getView(R.id.join_baby);
        TextView join_parents=(TextView)holder.getView(R.id.join_parents);
        TextView join_sum=(TextView)holder.getView(R.id.join_sum);
        if(holder.getLayoutPosition()%2==1){
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.activity_event_doublecolor));
        }else {
            ll.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        join_name.setText(data.getName()+"");
        join_baby.setText(data.getBabyNumber()+"");
        join_parents.setText(data.getParentsNumber()+"");
        join_sum.setText(data.getBabyNumber()+data.getParentsNumber()+"");
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_joineventlist;
    }
}
