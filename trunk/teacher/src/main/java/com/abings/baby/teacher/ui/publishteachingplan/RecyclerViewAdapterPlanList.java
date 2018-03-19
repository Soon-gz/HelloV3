package com.abings.baby.teacher.ui.publishteachingplan;

import android.content.Context;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.TeachingPlanModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.utils.ImageLoader;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterPlanList extends BaseAdapter<TeachingPlanModel> {

    public RecyclerViewAdapterPlanList(Context context, List<TeachingPlanModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, TeachingPlanModel data) {
        CircleImageView icon = holder.getView(R.id.libMsgList_civ_icon);
        TextView name = holder.getView(R.id.libMsgList_tv_person);//来自于谁或者发给谁
        TextView time = holder.getView(R.id.libMsgList_tv_time);//创建时间
        TextView title = holder.getView(R.id.libMsgList_tv_subject);//创建标题
        TextView content = holder.getView(R.id.libMsgList_tv_content);//消息内容
        if (null != data.getTeacherName() && !data.getTeacherName().equals(""))
            name.setText(data.getTeacherName());
        else
            name.setText("班主任");
        title.setText(data.getClassName());
        time.setText(DateUtil.getDescriptionTimeFromTimestamp(data.getCreateTime()));
        content.setText(DateUtil.timestamp2NYRFormat(data.getPlanningTime()) + "-" + DateUtil.timestamp2NYRFormat(data.getPlanningEndtime()));
        icon.setImageResource(R.drawable.head_holder);
        ImageLoader.loadHeadTarget(mContext, data.getHeadImageurlAbs(), icon);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.librecyler_item_msglist;
    }
}
