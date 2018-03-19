package com.abings.baby.ui.attendance;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellobaby.library.Const;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TimeCardHisModel;
import com.hellobaby.library.data.model.TimeCardModel;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class RecyclerViewAdapterAttendanceHisItemList extends BaseAdapter<TimeCardModel> {

    public RecyclerViewAdapterAttendanceHisItemList(Context context, List<TimeCardModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final TimeCardModel data) {
        ImageView head=(ImageView)holder.getView(R.id.hisdetail_head);
        TextView type=(TextView)holder.getView(R.id.hisdetail_type);
        TextView time=(TextView)holder.getView(R.id.hisdetail_time);
        TextView rel=(TextView)holder.getView(R.id.hisdetail_rel);
        if(data.getTimeStr()!=null){
            time.setText(data.getTimeStr().substring(data.getTimeStr().lastIndexOf(" "),data.getTimeStr().lastIndexOf(":")));
            type.setText(data.getEventType());
        }
        if(data.getPersonType()==TimeCardModel.TC_TPYE_USER){
            ImageLoader.loadHeadTarget(mContext, Const.URL_userHead+data.getHeadImageUrl(),head);
            rel.setText(data.getRelation());
        }else if(data.getPersonType()==TimeCardModel.TC_TPYE_AGENT){
           head.setImageResource(R.drawable.head_dai);
            rel.setText(data.getName());
        }else if(data.getPersonType()==TimeCardModel.TC_TPYE_CARD){
            ImageLoader.loadHeadTarget(mContext,Const.URL_pickHead+data.getHeadImageUrl(),head);
            rel.setText(data.getRelation());
        }
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_attendancehis_detail;
    }
}
