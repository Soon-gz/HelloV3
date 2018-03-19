package com.abings.baby.ui.measuredata.teachingplan;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

public class RecyclerViewAdapterTeachPlanningList extends BaseAdapter<TeachingPlanModel> {

    public RecyclerViewAdapterTeachPlanningList(Context context, List<TeachingPlanModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, final TeachingPlanModel data) {
        final RelativeLayout rl = holder.getView(R.id.tplist_rl_root);
        TextView textView = holder.getView(R.id.tp_tv_createTime);
        ImageView imageView = holder.getView(R.id.tp_im);
        if(data.getImageurl()==null||"21ad4eb243e140abbc36e65204c83f59.jpeg".equals(data.getImageurl())){
//            imageView.setVisibility(View.INVISIBLE);
            String typeStr = "暂无教学计划";
            if(data.isTypeLast()){
                typeStr = "上周暂无教学计划";
            }else if(data.isTypeThis()){
                typeStr = "本周暂无教学计划";
            }else if(data.isTypeNext()){
                typeStr = "下周暂无教学计划";
            }
            textView.setText(typeStr);
            ImageLoader.load(mContext,data.getImageurlAbs(), imageView);
        }else{
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.load(mContext, data.getImageurlAbs(), imageView);
            textView.setText("来自" + data.getTeacherName() + " " + DateUtil.timestamp2NYRFormat(data.getPlanningTime()) + "-" + DateUtil.timestamp2NYRFormat(data.getPlanningEndtime()));
        }


    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.librecyler_item_teachingplist;
    }
}
