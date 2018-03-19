package com.abings.baby.ui.attendance;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.abings.baby.R;
import com.hellobaby.library.Const;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class RecyclerViewAdapterAttendanceIndexList extends BaseAdapter<TimeCardModel> {

    public RecyclerViewAdapterAttendanceIndexList(Context context, List<TimeCardModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final TimeCardModel data) {
        TextView title=(TextView)holder.getView(R.id.attendance_item_title);
        TextView name=(TextView)holder.getView(R.id.attendance_item_name);
        TextView rel=(TextView)holder.getView(R.id.attendance_item_rel);
        ImageView head=(ImageView)holder.getView(R.id.attendance_item_head);
        title.setText(data.getTimeStr()!=null?data.getTimeStr().substring(0,data.getTimeStr().lastIndexOf(":"))+" "+data.getEventType():"");
        if(data.getPersonType()==TimeCardModel.TC_TPYE_USER){
            name.setText(data.getName()!=null?data.getName():"");
            rel.setText(data.getRelation()!=null?data.getRelation():"");
            ImageLoader.loadHeadTarget(mContext, Const.URL_userHead+data.getHeadImageUrl(),head);
        }else if(data.getPersonType()==TimeCardModel.TC_TPYE_AGENT){
            name.setText(data.getName()!=null?data.getName():"");
            rel.setText(data.getPhoneNum()!=null?data.getPhoneNum():"");
            head.setImageResource(R.drawable.head_dai);
//            ImageLoader.load(mContext,R.drawable.head_dai,head);
        }else if(data.getPersonType()==TimeCardModel.TC_TPYE_CARD){
            name.setText(data.getName()!=null?data.getName():"");
            rel.setText(data.getRelation()!=null?data.getRelation():"");
            ImageLoader.loadHeadTarget(mContext,Const.URL_pickHead+data.getHeadImageUrl(),head);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_attendanceindex;
    }
}
