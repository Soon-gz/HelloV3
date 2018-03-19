package com.abings.baby.teacher.ui.calendar;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.ScheduleIdModel;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapterCalendar extends BaseAdapter<ScheduleIdModel> {
    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private SimpleDateFormat sdf2 = new SimpleDateFormat("hh", Locale.getDefault());
    public RecyclerViewAdapterCalendar(Context context, List<ScheduleIdModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final ScheduleIdModel data) {
        //判断是不是全天
        if(data.getIsAllDay()==0){
            holder.getView(R.id.cal_tv_time).setVisibility(View.GONE);
            ((TextView)holder.getView(R.id.cal_tv_title)).setText("全天");
        }else {
            ((TextView)holder.getView(R.id.cal_tv_title)).setText(sdf.format(data.getScheduleStartDate()));
            ((TextView)holder.getView(R.id.cal_tv_time)).setText(Integer.parseInt(sdf2.format(data.getScheduleStartDate()))>12?"下午":"上午");
        }
        //内容
        ((TextView)holder.getView(R.id.cal_tv_content)).setText(data.getContent());
        //备注
        if(data.getRemark().equals("")){
            ((TextView)holder.getView(R.id.cal_tv_remark)).setVisibility(View.GONE);
        }else {
            ((TextView)holder.getView(R.id.cal_tv_remark)).setText(data.getRemark());
        }
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_calendar;
    }
}
