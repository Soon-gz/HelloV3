package com.abings.baby.ui.measuredata.teachingplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abings.baby.ui.event.EventDetailActivity;
import com.hellobaby.library.R;
import com.hellobaby.library.data.model.EventModel;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.ui.slide.SlideActivity;
import com.hellobaby.library.ui.slide.SlideBean;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hellobaby.library.Const.NORMAL_ACTIVITY_RESULT;

public class RecyclerViewAdapterTeachPlanningList extends BaseAdapter<TeachingPlanModel> {

    public RecyclerViewAdapterTeachPlanningList(Context context, List<TeachingPlanModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }
    @Override
    protected void convert(ViewHolder holder, final TeachingPlanModel data) {
        final RelativeLayout rl=(RelativeLayout) holder.getView(R.id.tplist_rl_root);
        TextView textView=(TextView)holder.getView(R.id.tp_tv_createTime);
        ImageView imageView=(ImageView) holder.getView(R.id.tp_im);
        ImageLoader.load(mContext,data.getImageurlAbs(),imageView);
        textView.setText("来自"+data.getTeacherName()+" "+data.getPlanningTime());
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_teachingplist;
    }
}
