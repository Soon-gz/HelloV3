package com.hellobaby.library.ui.alert;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellobaby.library.R;
import com.hellobaby.library.data.model.MessageItem;
import com.hellobaby.library.data.model.SystemMessageModel;
import com.hellobaby.library.utils.DateUtil;
import com.hellobaby.library.widget.baseadapter.BaseAdapter;
import com.hellobaby.library.widget.baseadapter.ViewHolder;

import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterAlertList extends BaseAdapter<SystemMessageModel> {

    public RecyclerViewAdapterAlertList(Context context, List<SystemMessageModel> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, SystemMessageModel data) {
        CircleImageView icon=(CircleImageView) holder.getView(R.id.libMsgList_civ_icon);
        TextView name = holder.getView(R.id.libMsgList_tv_person);//来自于谁或者发给谁
        TextView time = holder.getView(R.id.libMsgList_tv_time);//创建时间
        TextView title = holder.getView(R.id.libMsgList_tv_subject);//创建标题
        TextView content = holder.getView(R.id.libMsgList_tv_content);//消息内容
        if (null!=data.getSenderName()&&!data.getSenderName().equals(""))
            name.setText(data.getSenderName());
        else
            name.setText("哈喽宝贝");
        title.setText(data.getTitle());
        content.setText(data.getContent());
        try {
            time.setText(DateUtil.getDescriptionTimeFromTimestamp(DateUtil.longDay2time(data.getSendTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (data.getIsRead() == 1) {
            icon.setImageResource(R.drawable.msgbox_icon_readed);
        } else {
            icon.setImageResource(R.drawable.msgbox_icon_unread);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return  R.layout.librecyler_item_msglist;
    }
}
